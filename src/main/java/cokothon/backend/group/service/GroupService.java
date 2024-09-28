package cokothon.backend.group.service;

import cokothon.backend.book.domain.Book;
import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.book.service.BookService;
import cokothon.backend.global.exception.CustomException;
import cokothon.backend.global.exception.GlobalErrorCode;
import cokothon.backend.group.domain.Group;
import cokothon.backend.group.domain.GroupMember;
import cokothon.backend.group.dto.*;
import cokothon.backend.group.repository.GroupMemberRepository;
import cokothon.backend.group.repository.GroupRepository;
import cokothon.backend.group.repository.GroupSpecifications;
import cokothon.backend.member.domain.Member;
import cokothon.backend.member.repository.MemberRepository;
import cokothon.backend.record.dto.RecordDTO;
import cokothon.backend.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final BookService bookService;
    private final GroupMemberRepository groupMemberRepository;
    private final RecordService recordService;

    public List<Book> getTopRankingBooks() {
        List<BookCountDTO> bookCounts = groupRepository.findBookCounts();

        return bookCounts.stream()
                .map((bookCountDTO) -> bookService.findById(bookCountDTO.getBookId()))
                .toList();
    }

    public List<Group> findAllGroupsByMember(Member member) {
        return groupMemberRepository.findAllByMemberId(member.getId())
                .stream()
                .map(GroupMember::getGroup)
                .toList();
    }

    public GroupMember findGroupMember(Long groupId, Long memberId) {
        return groupMemberRepository.findByGroupIdAndMemberId(groupId, memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.GROUP_MEMBER_NOT_FOUND));
    }

    @Transactional
    public void createGroup(CreateGroupRequestDTO requestDTO, Long leaderId) {
        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Long isbn = requestDTO.getBook().getIsbn();

        Book book = (bookService.existsByIsbn(isbn)) ?
                bookService.findByIsbn(isbn) : bookService.addBook(requestDTO.getBook());

        Group group = Group.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .maxCount(requestDTO.getMaxCount())
                .leader(leader)
                .book(book)
                .build();

        groupRepository.save(group);
    }

    public GroupDTO getGroupDetailById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.GROUP_NOT_FOUND));
        long currentCount = groupMemberRepository.countByGroupId(groupId);
        List<RecordDTO> records = recordService.getRecordsByGroup(group)
                .stream()
                .map(RecordDTO::create)
                .toList();

        return GroupDTO.builder()
                .name(group.getName())
                .description(group.getDescription())
                .maxCount(group.getMaxCount())
                .currentCount((int) currentCount)
                .leaderName(group.getLeader().getName())
                .book(BookDTO.create(group.getBook()))
                .records(records)
                .build();
    }

    @Transactional
    public void joinGroup(JoinGroupRequestDTO requestDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Long groupId = requestDTO.getGroupId();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.GROUP_NOT_FOUND));

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .member(member)
                .build();

        groupMemberRepository.save(groupMember);
    }

    public List<GroupDisplayResponseDTO> searchByBookTitle(String keyword) {
        List<Group> groups = groupRepository.findAll(GroupSpecifications.searchByBookTitle(keyword.trim()));

        return groups.stream()
                .map(this::getGroupDisplay)
                .toList();
    }

    public GroupDisplayResponseDTO getGroupDisplay(Group group) {
        long currentCount = groupMemberRepository.countByGroupId(group.getId());
        return GroupDisplayResponseDTO.create(group, (int) currentCount);
    }
}
