package org.fomabb.taskmanagement.exceptionhandler.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private String message;
}
