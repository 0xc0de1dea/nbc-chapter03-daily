package com.example.chapter03daily.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class Request {

        private String name;

        private String email;

        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"name", "email", "createdAt", "modifiedAt"})
    public static class Response {

        protected String name;

        protected String email;

        protected LocalDateTime createdAt;

        protected LocalDateTime modifiedAt;

        public static Response build(
                String name,
                String email,
                LocalDateTime createdAt,
                LocalDateTime modifiedAt
        ) {
            return Response.builder()
                    .name(name)
                    .email(email)
                    .createdAt(createdAt)
                    .modifiedAt(modifiedAt)
                    .build();
        }
    }
}
