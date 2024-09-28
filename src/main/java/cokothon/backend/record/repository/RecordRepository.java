package cokothon.backend.record.repository;

import cokothon.backend.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(
            "SELECT r FROM Record r " +
                    "WHERE r.groupMember.group.id = :groupId"
    )
    List<Record> findAllByGroupId(@Param("groupId") Long groupId);
}
