package cokothon.backend.group.controller;

import cokothon.backend.global.oauth2.dto.CustomOAuth2User;
import cokothon.backend.group.dto.CreateGroupRequestDTO;
import cokothon.backend.group.dto.GroupDTO;
import cokothon.backend.group.dto.JoinGroupRequestDTO;
import cokothon.backend.group.dto.GroupDisplayResponseDTO;
import cokothon.backend.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(summary = "독서모임 생성")
    @PostMapping
    public ResponseEntity<?> createGroup(
            @RequestBody CreateGroupRequestDTO requestDTO,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) {
        groupService.createGroup(requestDTO, customOAuth2User.getMemberId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "독서모임 상세 조회")
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getGroupDetailById(groupId));
    }

    @Operation(summary = "사용자가 독서모임에 참여할 때 사용")
    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody JoinGroupRequestDTO requestDTO,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) {
        groupService.joinGroup(requestDTO, customOAuth2User.getMemberId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "책 이름으로 독서모임을 검색")
    @GetMapping("/search")
    public ResponseEntity<List<GroupDisplayResponseDTO>> searchByBookTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(groupService.searchByBookTitle(keyword));
    }
}
