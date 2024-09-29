package cokothon.backend.book.service;

import cokothon.backend.book.domain.Book;
import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.book.repository.BookRepository;
import cokothon.backend.global.exception.CustomException;
import cokothon.backend.global.exception.GlobalErrorCode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoApiKey;

    public List<BookDTO> searchByTitle(String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://dapi.kakao.com/v3/search/book?query=" + title,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getBody() == null || response.getBody().isEmpty()) {
            throw new CustomException(GlobalErrorCode.BOOK_NOT_FOUND);
        }

        JsonObject bodyObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
        JsonArray documents = bodyObject.getAsJsonArray("documents");
        int size = documents.size();
        List<BookDTO> books = new ArrayList<>();

        if (documents.size() < 0) {
            throw new CustomException(GlobalErrorCode.BOOK_NOT_FOUND);
        }

        for (int i = 0; i < size; i++) {
            JsonObject bookObject = documents.get(i).getAsJsonObject();

            StringBuilder authorBuilder = new StringBuilder();
            JsonArray authorsArray = bookObject.getAsJsonArray("authors");
            int authorSize = authorsArray.size();

            for (int j = 0; j < authorSize; j++) {
                String author = authorsArray.get(j).getAsString();

                if (i > 0) {
                    authorBuilder.append(", ");
                }

                authorBuilder.append(author);
            }

            BookDTO book = BookDTO.builder()
                    .title(bookObject.get("title").getAsString())
                    .isbn(formatIsbn(bookObject.get("isbn").getAsString()))
                    .author(authorBuilder.toString())
                    .cover(bookObject.get("thumbnail").getAsString())
                    .publisher(bookObject.get("publisher").getAsString())
                    .build();

            books.add(book);
        }

        return books;
    }

    public Long formatIsbn(String isbnText) {
        if (isbnText.isEmpty()) {
            throw new CustomException(GlobalErrorCode.BOOK_NOT_FOUND);
        }

        return Long.valueOf(isbnText.split(" ")[1]);
    }

    public boolean existsByIsbn(Long isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.BOOK_NOT_FOUND));
    }

    public Book findByIsbn(Long isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.BOOK_NOT_FOUND));
    }

    @Transactional
    public Book addBook(BookDTO bookDTO) {
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .cover(bookDTO.getCover())
                .publisher(bookDTO.getPublisher())
                .isbn(bookDTO.getIsbn())
                .build();

        return bookRepository.save(book);
    }
}
