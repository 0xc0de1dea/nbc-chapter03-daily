package com.example.chapter03daily.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Request {

        private Long dailyId;

        private String content;

        private String author;

        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"dailyId", "content", "author", "createdAt", "modifiedAt"})
    public static class Response {

        private Long dailyId;

        private String content;

        private String author;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        public static Response build(
                Long dailyId, String content, String author,
                LocalDateTime createdAt, LocalDateTime modifiedAt
        ) {
            return Response.builder()
                    .dailyId(dailyId)
                    .content(content)
                    .author(author)
                    .createdAt(createdAt)
                    .modifiedAt(modifiedAt)
                    .build();
        }
    }
}
