package com.example.chapter03daily.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * Validation
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청값 검증에 실패했습니다."),

    /**
     * Comment
     */
    EXCEEDED_COMMENT(HttpStatus.BAD_REQUEST, "하나의 일정에는 댓글을 10개까지만 작성할 수 있습니다."),

    /**
     * Daily
     */
    DAILY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 일정을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
