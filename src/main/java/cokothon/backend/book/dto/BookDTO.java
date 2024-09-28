package cokothon.backend.book.dto;

import cokothon.backend.book.domain.Book;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private String cover;
    private String publisher;
    private Long isbn;

    public static BookDTO create(Book book) {
        return BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .cover(book.getCover())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .build();
    }
}
