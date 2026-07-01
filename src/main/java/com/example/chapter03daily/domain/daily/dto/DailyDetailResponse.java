package com.example.chapter03daily.domain.daily.dto;

import com.example.chapter03daily.domain.comment.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "content", "author", "createdAt", "modifiedAt", "comments"})
public class DailyDetailResponse extends DailyDto.Response {

    private List<CommentDto.Response> comments;

    @Builder(access = AccessLevel.PRIVATE)
    private DailyDetailResponse(
            String title,
            String content,
            String author,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<CommentDto.Response> comments
    ) {
        super(title, content, author, createdAt, modifiedAt);
        this.comments = comments;
    }

    public static DailyDetailResponse build(
            String title,
            String content,
            String author,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<CommentDto.Response> comments
    ) {
        return DailyDetailResponse.builder()
                .title(title)
                .content(content)
                .author(author)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .comments(comments)
                .build();
    }
}
