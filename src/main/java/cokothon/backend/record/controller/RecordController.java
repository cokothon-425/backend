package cokothon.backend.record.controller;

import cokothon.backend.record.domain.Record;
import cokothon.backend.record.dto.CreateRecordRequestDTO;
import cokothon.backend.record.dto.UpdateRecordRequestDTO;
import cokothon.backend.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // 레코드 생성
    @PostMapping
    public ResponseEntity<?> createRecord(@RequestBody CreateRecordRequestDTO createRequestDTO) {
        recordService.createRecord(createRequestDTO);
        return ResponseEntity.ok().build();
    }

    // 레코드 수정
    @PatchMapping("/{recordId}")
    public ResponseEntity<?> updateRecord(@PathVariable Long recordId, @RequestBody UpdateRecordRequestDTO updateRequestDTO) {
        recordService.updateRecord(recordId, updateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
