package org.fomabb.taskmanagement.exceptionhandler.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException(String message) {
        super(message);
    }
}
