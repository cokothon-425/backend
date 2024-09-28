package cokothon.backend.record.dto;

import cokothon.backend.record.domain.Record;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RecordDTO {
    private Long id;
    private String content;
    private Integer startPage;
    private Integer endPage;
    private Long memberId;
    private ReadingStatus status;
    private LocalDateTime createdAt;

    public static RecordDTO create(Record record) {
        return RecordDTO.builder()
                .id(record.getId())
                .content(record.getContent())
                .startPage(record.getStartPage())
                .endPage(record.getEndPage())
                .memberId(record.getGroupMember().getMember().getId())
                .status(record.getReadingStatus())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
