package cokothon.backend.record.controller;

import cokothon.backend.global.oauth2.dto.CustomOAuth2User;
import cokothon.backend.record.dto.CreateRecordRequestDTO;
import cokothon.backend.record.dto.UpdateRecordRequestDTO;
import cokothon.backend.record.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // 레코드 생성
    @Operation(summary = "독서모임 책 기록 생성")
    @PostMapping
    public ResponseEntity<?> createRecord(
            @RequestBody CreateRecordRequestDTO createRequestDTO,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) {
        recordService.createRecord(createRequestDTO, customOAuth2User.getMemberId());
        return ResponseEntity.ok().build();
    }

    // 레코드 수정
    @Operation(summary = "독서모임 책 기록 수정")
    @PatchMapping("/{recordId}")
    public ResponseEntity<?> updateRecord(@PathVariable Long recordId, @RequestBody UpdateRecordRequestDTO updateRequestDTO) {
        recordService.updateRecord(recordId, updateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "독서모임 책 기록 제거")
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
