package com.iase24.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentsDataDto {

    private Long id;
    private String text;
    private UserAuthorDataDto author;
}
