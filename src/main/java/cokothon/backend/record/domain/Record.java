package cokothon.backend.record.domain;


import cokothon.backend.group.domain.GroupMember;
import cokothon.backend.record.dto.ReadingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "record")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private Integer startPage;

    private Integer endPage;

    @ManyToOne
    @JoinColumn(name = "group_member_id")
    private GroupMember groupMember;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReadingStatus readingStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public void updateRecord(String content, Integer startPage, Integer endPage, ReadingStatus status) {
        this.content = content;
        this.startPage = startPage;
        this.endPage = endPage;
        this.readingStatus = status;
    }



}
