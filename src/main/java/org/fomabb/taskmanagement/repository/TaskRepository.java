package org.fomabb.taskmanagement.repository;

import org.fomabb.taskmanagement.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    boolean existsByTitle(String title);

    Page<Task> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<Task> findAllByAssigneeId(Long assigneeId, Pageable pageable);

    @Query("select t from Task t where t.createdAt between :startDate and :endDate")
    List<Task> findTasksByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
