package cokothon.backend.member.dto;

import cokothon.backend.member.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private Long id;
    private String name;

    public static MemberDTO create(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }
}
