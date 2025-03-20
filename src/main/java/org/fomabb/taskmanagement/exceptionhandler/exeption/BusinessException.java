package org.fomabb.taskmanagement.exceptionhandler.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Исключение, представляющее ошибки бизнес-логики.
 * <p>
 * Это исключение может быть выброшено, когда возникает ошибка, связанная с бизнес-правилами
 * или логикой приложения. Упаковка сообщений об ошибках в это исключение позволяет централизованно
 * обрабатывать бизнес-ошибки в приложении.
 *
 * @author Ваше Имя
 */
@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private String message;
}
