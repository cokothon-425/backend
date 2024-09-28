package cokothon.backend.record.service;

import cokothon.backend.record.dto.CreateRecordRequestDTO;
import cokothon.backend.record.domain.Record;
import cokothon.backend.record.dto.UpdateRecordRequestDTO;
import cokothon.backend.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;

    // 레코드 생성
    @Transactional
    public void createRecord(CreateRecordRequestDTO createRequestDTO) {
        Record record = Record.builder()
                .content(createRequestDTO.getContent())
                .startPage(createRequestDTO.getStartPage())
                .endPage(createRequestDTO.getEndPage())
                .readingStatus(createRequestDTO.getStatus())
                .build();
        recordRepository.save(record);
    }

    // 레코드 수정
    @Transactional
    public void updateRecord(Long recordId, UpdateRecordRequestDTO updateRequestDTO) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.updateRecord(
                updateRequestDTO.getContent(),
                updateRequestDTO.getStartPage(),
                updateRequestDTO.getEndPage(),
                updateRequestDTO.getStatus());
    }

    // 레코드 삭제
    @Transactional
    public void deleteRecord(Long recordId) {
        if (!recordRepository.existsById(recordId)) {
            throw new RuntimeException("Record not found");
        }
        recordRepository.deleteById(recordId);
    }
}