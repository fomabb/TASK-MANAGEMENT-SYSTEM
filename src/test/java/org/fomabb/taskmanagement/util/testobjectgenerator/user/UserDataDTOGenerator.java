package org.fomabb.taskmanagement.util.testobjectgenerator.user;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;

import java.time.LocalDateTime;

@UtilityClass
public class UserDataDTOGenerator {

    public static UserDataDto generateUserDataDto() {
        return UserDataDto.builder()
                .userId(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .createdAt(LocalDateTime.parse("20-03-2025 14:22"))
                .userRole(Role.ROLE_USER)
                .build();
    }
}
