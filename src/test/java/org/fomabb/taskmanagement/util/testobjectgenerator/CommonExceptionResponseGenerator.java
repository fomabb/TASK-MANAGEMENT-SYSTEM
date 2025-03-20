package org.fomabb.taskmanagement.util.testobjectgenerator;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.exception.CommonExceptionResponse;

import java.time.LocalDateTime;

@UtilityClass
public class CommonExceptionResponseGenerator {

    public static CommonExceptionResponse generateExceptionResponse() {
        return CommonExceptionResponse.builder()
                .exceptionClass("EntityNotFoundException")
                .message("User with ID 0 not found")
                .timestamp(LocalDateTime.parse("2025-03-20T18:17:53.3362947"))
                .build();
    }
}
