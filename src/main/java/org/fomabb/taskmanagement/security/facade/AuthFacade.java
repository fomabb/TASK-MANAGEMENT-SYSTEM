package org.fomabb.taskmanagement.security.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.security.dto.request.SignUpRequest;
import org.fomabb.taskmanagement.security.dto.response.JwtAuthenticationResponse;
import org.fomabb.taskmanagement.security.service.AuthenticationService;
import org.springframework.stereotype.Component;

/**
 * Фасад для аутентификации пользователей.
 * Обрабатывает логику регистрации пользователя, включая проверку пароля.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthenticationService authenticationService;

    /**
     * Обрабатывает запрос на регистрацию пользователя.
     * Проверяет, совпадает ли пароль с его подтверждением.
     *
     * @param request объект {@link SignUpRequest}, содержащий данные для регистрации
     * @return объект {@link JwtAuthenticationResponse}, содержащий информацию о JWT аутентификации
     * @throws BusinessException если пароли не совпадают
     */
    public JwtAuthenticationResponse signUpFacade(SignUpRequest request) {
        log.info("Начало проверки подтверждения пароля пользователя");
        if (request.getPassword().equals(request.getConfirmPassword())) {
            log.info("Пароль пользователя подтвержден успешно");
            return authenticationService.signUp(request);
        } else {
            log.warn("Пароль пользователя не совпадает с введенным изначально");
            throw new BusinessException("Пароль не совпадает с заданным Вами паролем");
        }
    }
}
