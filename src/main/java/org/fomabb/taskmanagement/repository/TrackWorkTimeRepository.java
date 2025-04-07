package org.fomabb.taskmanagement.repository;

import org.fomabb.taskmanagement.entity.TrackWorkTime;
import org.fomabb.taskmanagement.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackWorkTimeRepository extends JpaRepository<TrackWorkTime, Long> {
    List<TrackWorkTime> findByDateTimeTrackBetweenAndTaskAssignee(LocalDateTime startDateTime, LocalDateTime endDateTime, User taskAssignee);
}
