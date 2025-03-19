package org.fomabb.taskmanagement.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.security.dto.request.SignInRequest;
import org.fomabb.taskmanagement.security.dto.request.SignUpRequest;
import org.fomabb.taskmanagement.security.dto.response.JwtAuthenticationResponse;
import org.fomabb.taskmanagement.security.facade.AuthFacade;
import org.fomabb.taskmanagement.security.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для аутентификации пользователей.
 * Обрабатывает запросы на регистрацию и авторизацию пользователей.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Аутентификация", description = "Интерфейс для логики аутентификации")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final AuthFacade authFacade;

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект {@link SignUpRequest}, содержащий данные для регистрации
     * @return ответ с информацией о JWT аутентификации
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        log.info("Получен запрос на регистрацию нового пользователя {} с email: {}",
                request.getFirstName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.signUpFacade(request));
    }

    /**
     * Авторизация пользователя.
     *
     * @param request объект {@link SignInRequest}, содержащий данные для авторизации
     * @return ответ с информацией о JWT аутентификации
     */
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Получен запрос на авторизацию пользователя");
        return authenticationService.signIn(request);
    }
}
