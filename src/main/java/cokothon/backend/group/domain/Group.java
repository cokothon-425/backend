package cokothon.backend.group.domain;

import cokothon.backend.book.domain.Book;
import cokothon.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "group")
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Member leader;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
