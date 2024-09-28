package cokothon.backend.group.domain;

import cokothon.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
