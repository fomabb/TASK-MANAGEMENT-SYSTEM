package org.fomabb.taskmanagement.exceptionhandler.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается при попытке создать элемент с дублирующимся заголовком.
 * <p>
 * Это исключение используется для сигнализации о конфликте, когда заголовок,
 * который пытаются использовать, уже существует в системе. При выбрасывании
 * этого исключения будет возвращен статус 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException(String message) {
        super(message);
    }
}
