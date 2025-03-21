package org.fomabb.taskmanagement.util.testobjectgenerator.user;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;

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
}
