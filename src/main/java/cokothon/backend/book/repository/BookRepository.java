package cokothon.backend.book.repository;

import cokothon.backend.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(Long isbn);

    Optional<Book> findByIsbn(Long isbn);
}
