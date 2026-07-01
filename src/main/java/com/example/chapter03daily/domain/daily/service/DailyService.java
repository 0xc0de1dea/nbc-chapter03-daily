package com.example.chapter03daily.domain.daily.service;

import com.example.chapter03daily.common.exception.ErrorCode;
import com.example.chapter03daily.common.exception.ServiceException;
import com.example.chapter03daily.domain.comment.dto.CommentDto;
import com.example.chapter03daily.domain.comment.entity.Comment;
import com.example.chapter03daily.domain.daily.dto.DailyDetailResponse;
import com.example.chapter03daily.domain.daily.dto.DailyDto;
import com.example.chapter03daily.domain.daily.entity.Daily;
import com.example.chapter03daily.domain.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;

    @Transactional
    public DailyDto.Response create(DailyDto.Request request) {
        Daily saved = dailyRepository.saveAndFlush(
                new Daily(
                        request.getTitle(),
                        request.getContent(),
                        request.getAuthor(),
                        request.getPassword()
                )
        );

        return DailyDto.Response.build(
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthor(),
                saved.getCreatedAt(),
                null
        );
    }

    @Transactional(readOnly = true)
    public Page<DailyDto.Response> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("modifiedAt").descending()
        );

        Page<Daily> pageDaily = dailyRepository.findAll(pageable);

        return pageDaily.map(daily ->
                DailyDto.Response.build(
                        daily.getTitle(),
                        daily.getContent(),
                        daily.getAuthor(),
                        daily.getCreatedAt(),
                        daily.getModifiedAt()
                ));
    }

    @Transactional(readOnly = true)
    public DailyDetailResponse findOne(long id) {
        Daily daily = dailyRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
                );

        List<Comment> comments = daily.getComments();

        List<CommentDto.Response> commentDtos = daily.getComments()
                .stream()
                .map(comment -> CommentDto.Response.build(
                        comment.getDaily().getId(),
                        comment.getContent(),
                        comment.getAuthor(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ))
                .toList();

        return DailyDetailResponse.build(
                daily.getTitle(),
                daily.getContent(),
                daily.getAuthor(),
                daily.getCreatedAt(),
                daily.getModifiedAt(),
                commentDtos
        );
    }

    @Transactional
    public DailyDto.Response update(long id, DailyDto.Request request) {
        Daily saved = dailyRepository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
        );

        if (!saved.getPassword().equals(request.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        String title = request.getTitle();
        String content = request.getContent();
        String author = request.getAuthor();

        saved.update(
                title == null ? saved.getTitle() : title,
                content == null ? saved.getContent() : content,
                author == null ? saved.getAuthor() : author
        );

        return DailyDto.Response.build(
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthor(),
                null,
                saved.getModifiedAt()
        );
    }

    @Transactional
    public void delete(long id, String password) {
        Daily saved = dailyRepository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorCode.DAILY_NOT_FOUND)
        );

        if (!saved.getPassword().equals(password)) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        dailyRepository.deleteById(id);
    }
}
