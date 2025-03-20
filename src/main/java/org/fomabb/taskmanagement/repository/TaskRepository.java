package org.fomabb.taskmanagement.repository;

import org.fomabb.taskmanagement.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    boolean existsByTitle(String title);

    Page<Task> findAllByAuthorId(Long authorId, Pageable pageable);
}
