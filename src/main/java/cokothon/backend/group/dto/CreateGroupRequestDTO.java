package cokothon.backend.group.dto;

import cokothon.backend.book.dto.BookDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateGroupRequestDTO {
    private String name;
    private String description;
    private BookDTO book;
    private Integer maxCount;
}
