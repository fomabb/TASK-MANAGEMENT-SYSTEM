package com.iase24.test.security.service;

import com.iase24.test.exceptionhandler.exeption.BusinessException;
import com.iase24.test.security.dto.request.SignInRequest;
import com.iase24.test.security.dto.request.SignUpRequest;
import com.iase24.test.security.dto.response.JwtAuthenticationResponse;
import com.iase24.test.security.entity.User;
import com.iase24.test.security.entity.enumeration.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceSecurity userService;
    private final JwtServiceSecurity jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.info("Начато выполнение сборки и сохранения пользователя в базу данных");
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .createdAt(now())
                .updatedAt(null)
                .build();
        log.info("Пользователь сохранен в базу данных");
        userService.create(user);
        log.info("Начало генерации токена для пользователя");
        var jwt = jwtService.generateToken(user);
        if (jwt.isEmpty()) {
            log.warn("Не удалось сгенерировать токен для пользователя");
            throw new BusinessException("Токен для пользователя не сгенерирован");
        } else {
            log.info("Токен для пользователя сгенерирован");
            return new JwtAuthenticationResponse(jwt);
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.info("Попытка авторизации пользователя");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());
        var jwt = jwtService.generateToken(user);
        log.info("Пользователь успешно авторизован");
        return new JwtAuthenticationResponse(jwt);
    }
}
