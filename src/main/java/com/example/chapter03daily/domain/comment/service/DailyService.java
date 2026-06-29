package com.example.chapter03daily.domain.comment.service;

import com.example.chapter03daily.domain.comment.dto.DailyDto;
import com.example.chapter03daily.domain.comment.entity.Daily;
import com.example.chapter03daily.domain.comment.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
