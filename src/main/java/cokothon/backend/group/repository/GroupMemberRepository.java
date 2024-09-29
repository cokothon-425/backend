package cokothon.backend.group.repository;

import cokothon.backend.group.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    long countByGroupId(Long groupId);
    Optional<GroupMember> findByGroupIdAndMemberId(Long groupId, Long memberId);
    List<GroupMember> findAllByMemberId(Long memberId);
    List<GroupMember> findAllByGroupId(Long groupId);
    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);
}
