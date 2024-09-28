package cokothon.backend.group.repository;

import cokothon.backend.group.domain.Group;
import cokothon.backend.group.dto.BookCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    @Query("SELECT new cokothon.backend.group.dto.BookCountDTO(b.id, COUNT(g)) " +
            "FROM Group g JOIN g.book b " +
            "GROUP BY b.id " +
            "ORDER BY COUNT(g) DESC")
    List<BookCountDTO> findBookCounts();
}
