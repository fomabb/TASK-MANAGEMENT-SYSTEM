package com.iase24.test.repository;

import com.iase24.test.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
            select t.* from tasks t join comments c on t.id = c.task_id
            """, nativeQuery = true)
    List<Task> findAllWithComment();
}
