package org.fomabb.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Запрос клиента на трек времени по задаче")
public class TrackTimeDatDto {

    @Schema(description = "Идентификатор задачи", example = "1")
    private Long taskId;

    @Schema(description = "Отчёт о проделанной работе", example = "Выполнял задачу")
    private String description;

    @Schema(description = "Время затраченное на выполнение задачи на день", example = "2")
    private Integer timeTrack;

    @Schema(description = "Дата отчёта", example = "12.12.2025")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dateTimeTrack;
}
