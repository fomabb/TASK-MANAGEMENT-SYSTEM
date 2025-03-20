package org.fomabb.taskmanagement.repository;

import org.fomabb.taskmanagement.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {

    Page<Comment> findCommentsByTaskId(Long taskId, Pageable pageable);

    Page<Comment> findAllByAuthorId(Long authorId, Pageable pageable);
}
