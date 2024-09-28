package cokothon.backend.record.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRecordRequestDTO {
    private String content;
    private Integer startPage;
    private Integer endPage;
    private ReadingStatus status;
}
