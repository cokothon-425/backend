package cokothon.backend.page.dto;

import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.group.dto.GroupDisplayResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomeResponseDTO {
    private String userName;
    private List<BookDTO> hotBooks;
    private List<GroupDisplayResponseDTO> groups;
}
