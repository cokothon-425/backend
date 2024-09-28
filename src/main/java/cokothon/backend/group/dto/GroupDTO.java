package cokothon.backend.group.dto;

import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.record.dto.RecordDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GroupDTO {
    private String name;
    private String description;
    private BookDTO book;
    private String leaderName;
    private Integer maxCount;
    private Integer currentCount;
    private List<RecordDTO> records;
}
