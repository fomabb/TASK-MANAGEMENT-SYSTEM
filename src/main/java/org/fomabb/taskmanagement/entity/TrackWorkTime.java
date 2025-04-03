package org.fomabb.taskmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "track_works_times")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "time_track")
    private Integer timeTrack;

    @Column(name = "date_time_track")
    private LocalDateTime dateTimeTrack;

    @OneToOne(fetch = FetchType.LAZY)
    private Task task;
}
