package cokothon.backend.page.service;

import cokothon.backend.book.dto.BookDTO;
import cokothon.backend.global.exception.CustomException;
import cokothon.backend.global.exception.GlobalErrorCode;
import cokothon.backend.group.domain.Group;
import cokothon.backend.group.dto.GroupDisplayResponseDTO;
import cokothon.backend.group.service.GroupService;
import cokothon.backend.member.domain.Member;
import cokothon.backend.member.repository.MemberRepository;
import cokothon.backend.page.dto.HomeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {
    private final GroupService groupService;
    private final MemberRepository memberRepository;

    public HomeResponseDTO getHomePageData(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.MEMBER_NOT_FOUND));

        List<GroupDisplayResponseDTO> groups = groupService.findAllGroupsByMember(member)
                .stream()
                .map(groupService::getGroupDisplay)
                .toList();

        List<BookDTO> books = groupService.getTopRankingBooks()
                .stream()
                .map(BookDTO::create)
                .toList();

        return HomeResponseDTO.builder()
                .userName(member.getName())
                .groups(groups)
                .hotBooks(books)
                .build();
    }
}
