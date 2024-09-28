package cokothon.backend.group.dto;

import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.group.domain.Group;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GroupDisplayResponseDTO {
    private String name;
    private String description;
    private BookDTO book;
    private String leaderName;
    private Integer maxCount;
    private Integer currentCount;

    public static GroupDisplayResponseDTO create(Group group, int currentCount) {
        return GroupDisplayResponseDTO
                .builder()
                .name(group.getName())
                .description(group.getDescription())
                .book(BookDTO.create(group.getBook()))
                .leaderName(group.getLeader().getName())
                .maxCount(group.getMaxCount())
                .currentCount(currentCount)
                .build();
    }
}
