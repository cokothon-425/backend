package cokothon.backend.group.repository;

import cokothon.backend.book.domain.Book;
import cokothon.backend.group.domain.Group;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class GroupSpecifications {
    public static Specification<Group> searchByBookTitle(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Join<Group, Book> bookJoin = root.join("book", JoinType.LEFT);

            return criteriaBuilder.like(criteriaBuilder.lower(bookJoin.get("title")), "%" + keyword + "%");
        };
    }
}
