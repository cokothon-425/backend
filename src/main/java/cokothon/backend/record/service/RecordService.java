package cokothon.backend.record.service;

import cokothon.backend.global.exception.CustomException;
import cokothon.backend.global.exception.GlobalErrorCode;
import cokothon.backend.group.domain.Group;
import cokothon.backend.group.domain.GroupMember;
import cokothon.backend.group.repository.GroupMemberRepository;
import cokothon.backend.group.service.GroupService;
import cokothon.backend.record.dto.CreateRecordRequestDTO;
import cokothon.backend.record.domain.Record;
import cokothon.backend.record.dto.UpdateRecordRequestDTO;
import cokothon.backend.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;
    private final GroupMemberRepository groupMemberRepository;

    public List<Record> getRecordsByGroup(Group group) {
        return recordRepository.findAllByGroupId(group.getId());
    }

    // 레코드 생성
    @Transactional
    public void createRecord(CreateRecordRequestDTO createRequestDTO, Long memberId) {
        GroupMember groupMember = groupMemberRepository.findByGroupIdAndMemberId(createRequestDTO.getGroupId(), memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.GROUP_MEMBER_NOT_FOUND));

        Record record = Record.builder()
                .content(createRequestDTO.getContent())
                .startPage(createRequestDTO.getStartPage())
                .endPage(createRequestDTO.getEndPage())
                .readingStatus(createRequestDTO.getStatus())
                .groupMember(groupMember)
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
                updateRequestDTO.getStatus()
        );
    }

    // 레코드 삭제
    @Transactional
    public void deleteRecord(Long recordId) {
        if (!recordRepository.existsById(recordId)) {
            throw new CustomException(GlobalErrorCode.RECORD_NOT_FOUND);
        }
        recordRepository.deleteById(recordId);
    }
}