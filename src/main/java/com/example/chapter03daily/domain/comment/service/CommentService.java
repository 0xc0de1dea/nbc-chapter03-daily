package com.example.chapter03daily.domain.comment.service;

import com.example.chapter03daily.common.exception.ErrorCode;
import com.example.chapter03daily.common.exception.ServiceException;
import com.example.chapter03daily.domain.comment.dto.CommentDto;
import com.example.chapter03daily.domain.comment.entity.Comment;
import com.example.chapter03daily.domain.comment.repository.CommentRepository;
import com.example.chapter03daily.domain.daily.entity.Daily;
import com.example.chapter03daily.domain.daily.repository.DailyRepository;
import com.example.chapter03daily.domain.user.entity.User;
import com.example.chapter03daily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public CommentDto.Response create(org.springframework.security.core.userdetails.User user, long id, CommentDto.Request request) {
        String email = user.getUsername();

        Daily daily = dailyRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
                );

        long count = commentRepository.countByDailyId(id);

        if (count >= 10) {
            throw new ServiceException(ErrorCode.EXCEEDED_COMMENT);
        }

        Comment comment = commentRepository.saveAndFlush(
                new Comment(
                        request.getContent(),
                        email,
                        passwordEncoder.encode(request.getPassword()),
                        daily
                )
        );

        return CommentDto.Response.build(
                daily.getId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getCreatedAt(),
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
    public CommentDto.Response update(org.springframework.security.core.userdetails.User user, long id, CommentDto.Request request) {
        String email = user.getUsername();

        User savedUser = userRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
                );

        Comment savedComment = commentRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND)
                );

        if (!savedUser.getEmail().equals(savedComment.getAuthor())) {
            throw new ServiceException(ErrorCode.USER_NOT_MATCHED);
        }

        if (!passwordEncoder.matches(request.getPassword(), savedComment.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        savedComment.update(request.getContent());

        return CommentDto.Response.build(
                savedComment.getDaily().getId(),
                savedComment.getContent(),
                savedComment.getAuthor(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional
    public void delete(org.springframework.security.core.userdetails.User user, long id, CommentDto.Request request) {
        String email = user.getUsername();

        User savedUser = userRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
                );

        Comment savedComment = commentRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND)
                );

        if (!savedUser.getEmail().equals(savedComment.getAuthor())) {
            throw new ServiceException(ErrorCode.USER_NOT_MATCHED);
        }

        if (!passwordEncoder.matches(request.getPassword(), savedComment.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        commentRepository.deleteById(id);
    }
}
