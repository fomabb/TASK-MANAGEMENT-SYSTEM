package org.fomabb.taskmanagement.security.repository;

import org.fomabb.taskmanagement.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс для сервиса управления пользователями.
 * Содержит методы для получения информации о пользователях.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
