package org.fomabb.taskmanagement.util.testobjectgenerator.user;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;

import java.util.List;

import static java.time.LocalDateTime.now;

@UtilityClass
public class UserDataDTOGenerator {

    public static UserDataDto generateUserDataDto() {
        return UserDataDto.builder()
                .userId(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .createdAt(now())
                .userRole(Role.ROLE_USER)
                .build();
    }

    public static User generateUserEntity() {
        return User.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .createdAt(now())
                .role(Role.ROLE_USER)
                .build();
    }

    public static User generateUserEntityIdTwo() {
        return User.builder()
                .id(2L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .createdAt(now())
                .role(Role.ROLE_USER)
                .build();
    }

    public static List<User> generateListUsers() {
        User user1 = User.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .createdAt(now())
                .role(Role.ROLE_USER)
                .build();


        User user2 = User.builder()
                .id(2L)
                .firstName("Petr}")
                .lastName("Petrov")
                .createdAt(now())
                .role(Role.ROLE_USER)
                .build();


        return List.of(user1, user2);
    }

    public static List<UserDataDto> generateListUserDto() {

        UserDataDto user1 = UserDataDto.builder()
                .userId(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .createdAt(now())
                .userRole(Role.ROLE_USER)
                .build();

        UserDataDto user2 = UserDataDto.builder()
                .userId(2L)
                .firstName("Petr}")
                .lastName("Petrov")
                .createdAt(now())
                .userRole(Role.ROLE_USER)
                .build();

        return List.of(user1, user2);
    }
}
