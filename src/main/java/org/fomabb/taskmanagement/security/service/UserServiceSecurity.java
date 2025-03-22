package org.fomabb.taskmanagement.security.service;

import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceSecurity {

    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (user.getRole().equals(Role.ROLE_USER) && repository.existsByUsername(user.getUsername())) {
            throw new BusinessException("The user with this email already exists");
        }

        if (user.getRole().equals(Role.ROLE_ADMIN) && repository.existsByUsername(user.getUsername())) {
            throw new BusinessException("An employee with this username already exists");
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with not found"));
    }

    /**
     * Извлечение ID пользователя из security context
     *
     * @return userId
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return ((User) userDetails).getId();
        }
        throw new BusinessException("Authentication principal is not of type UserDetails");
    }

    public Role getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return ((User) userDetails).getRole();
        }
        throw new BusinessException("Authentication principal is not of type UserDetails");
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
