package org.fomabb.taskmanagement.util.testobjectgenerator.task;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class TrackTaskResponseGenerator {

    public static TrackTimeDatDto generateTrackTimeDataDtoFromTaskEntity(Task task) {
        return TrackTimeDatDto.builder()
                .taskId(task.getId())
                .description("Description-1")
                .timeTrack(2)
                .dateTimeTrack(LocalDateTime.now().toString())
                .build();
    }

    public static TrackTimeResponse generateTrackResponse(Task task) {
        return TrackTimeResponse.builder()
                .taskId(task.getId())
                .dateTimeTrack(LocalDateTime.now())
                .timeTrack(2)
                .description("Description-1")
                .build();
    }

    public static Map<String, List<TrackTimeResponse>> generateTrackTimeResponseMap(
            TrackTimeResponse trackTimeResponse,
            LocalDate date
    ) {
        Map<String, List<TrackTimeResponse>> responseMap = new LinkedHashMap<>();
        TrackTimeResponse response = TrackTimeResponse.builder()
                .taskId(trackTimeResponse.getTaskId())
                .dateTimeTrack(LocalDateTime.now())
                .timeTrack(2)
                .description("Description-1")
                .build();

        responseMap.put("MONDAY " + date.plusDays(0).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("TUESDAY " + date.plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("WEDNESDAY " + date.plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("THURSDAY " + date.plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("FRIDAY " + date.plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("SATURDAY " + date.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("SUNDAY " + date.plusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), List.of(response));

        return responseMap;
    }
}