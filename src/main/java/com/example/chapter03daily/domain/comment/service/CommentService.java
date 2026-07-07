package com.example.chapter03daily.domain.comment.service;

import com.example.chapter03daily.common.config.PasswordEncoder;
import com.example.chapter03daily.common.exception.ErrorCode;
import com.example.chapter03daily.common.exception.ServiceException;
import com.example.chapter03daily.domain.comment.dto.CommentDto;
import com.example.chapter03daily.domain.comment.entity.Comment;
import com.example.chapter03daily.domain.comment.repository.CommentRepository;
import com.example.chapter03daily.domain.daily.entity.Daily;
import com.example.chapter03daily.domain.daily.repository.DailyRepository;
import com.example.chapter03daily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DailyRepository dailyRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public CommentDto.Response create(CommentDto.Request request) {
        Daily daily = dailyRepository.findById(request.getDailyId())
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
                );

        long count = commentRepository.countByDailyId(request.getDailyId());

        if (count >= 10) {
            throw new ServiceException(ErrorCode.EXCEEDED_COMMENT);
        }

        Comment saved = commentRepository.saveAndFlush(
                new Comment(
                        request.getContent(),
                        request.getAuthor(),
                        passwordEncoder.encode(request.getPassword()),
                        daily
                )
        );

        return CommentDto.Response.build(
                daily.getId(),
                saved.getContent(),
                saved.getAuthor(),
                saved.getCreatedAt(),
                null
        );
    }

    @Transactional(readOnly = true)
    public List<CommentDto.Response> findAll(long id) {
        Daily daily = dailyRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
                );

        List<Comment> comments = daily.getComments();

        return comments
                .stream()
                .map(comment -> CommentDto.Response.build(
                        comment.getDaily().getId(),
                        comment.getContent(),
                        comment.getAuthor(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ))
                .toList();
    }

    @Transactional
    public CommentDto.Response update(long id, CommentDto.Request request, String password) {
        Comment saved = commentRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND)
                );

        if (!passwordEncoder.matches(password, saved.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        saved.update(request.getContent());

        return CommentDto.Response.build(
                saved.getDaily().getId(),
                saved.getContent(),
                saved.getAuthor(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    @Transactional
    public void delete(long id, String password) {
        Comment saved = commentRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND)
                );

        if (!passwordEncoder.matches(password, saved.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        userRepository.deleteById(id);
    }
}
