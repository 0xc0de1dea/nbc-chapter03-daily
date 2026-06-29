package com.example.chapter03daily.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class DailyDto {

    @Getter
    @AllArgsConstructor
    public static class Request {

        private String title;

        private String content;

        private String author;

        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private String title;

        private String content;

        private String author;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        public static Response build(
                String title, String content, String author,
                LocalDateTime createdAt, LocalDateTime modifiedAt
        ) {
            return Response.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .createdAt(createdAt)
                    .modifiedAt(modifiedAt)
                    .build();
        }
    }
}
