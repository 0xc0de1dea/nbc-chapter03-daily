package com.example.chapter03daily.domain.comment.service;

import com.example.chapter03daily.common.exception.ErrorCode;
import com.example.chapter03daily.common.exception.ServiceException;
import com.example.chapter03daily.domain.comment.dto.CommentDto;
import com.example.chapter03daily.domain.comment.entity.Comment;
import com.example.chapter03daily.domain.comment.repository.CommentRepository;
import com.example.chapter03daily.domain.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DailyRepository dailyRepository;

    @Transactional
    public CommentDto.Response create(CommentDto.Request request) {
        dailyRepository.findById(request.getDailyId())
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
                );

        long count = commentRepository.countByDailyId(request.getDailyId());

        if (count >= 10) {
            throw new ServiceException(ErrorCode.EXCEEDED_COMMENT);
        }

        Comment saved = commentRepository.saveAndFlush(
                new Comment(
                        request.getDailyId(),
                        request.getContent(),
                        request.getAuthor(),
                        request.getPassword()
                )
        );

        return CommentDto.Response.build(
                saved.getDailyId(),
                saved.getContent(),
                saved.getAuthor(),
                saved.getCreatedAt(),
                null
        );
    }
}
