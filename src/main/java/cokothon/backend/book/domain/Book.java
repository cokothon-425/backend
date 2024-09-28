package cokothon.backend.book.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
