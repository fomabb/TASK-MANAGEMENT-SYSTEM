package com.iase24.test.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonExceptionResponse {

    private LocalDateTime timestamp;

    private String exceptionClass;

    private String message;
}
