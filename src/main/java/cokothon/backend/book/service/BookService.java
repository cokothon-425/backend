package cokothon.backend.book.service;

import cokothon.backend.book.domain.Book;
import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.book.repository.BookRepository;
import cokothon.backend.global.exception.CustomException;
import cokothon.backend.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

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
