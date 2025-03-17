package com.iase24.test.security.controller;

import com.iase24.test.security.dto.request.SignInRequest;
import com.iase24.test.security.dto.request.SignUpRequest;
import com.iase24.test.security.dto.response.JwtAuthenticationResponse;
import com.iase24.test.security.facade.AuthFacade;
import com.iase24.test.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Аутентификация", description = "Интерфейс для логики аутентификации")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final AuthFacade authFacade;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        log.info("Получен запрос на регистрацию нового пользователя {} с email: {}",
                request.getFirstName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.signUpFacade(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Получен запрос на авторизацию пользователя");
        return authenticationService.signIn(request);
    }
}
