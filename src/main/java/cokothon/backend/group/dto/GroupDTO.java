package cokothon.backend.group.dto;

import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.member.dto.MemberDTO;
import cokothon.backend.record.dto.RecordDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private BookDTO book;
    private String leaderName;
    private Integer maxCount;
    private Integer currentCount;
    private List<MemberDTO> members;
    private List<RecordDTO> records;
}
