package cokothon.backend.group.repository;

import cokothon.backend.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
