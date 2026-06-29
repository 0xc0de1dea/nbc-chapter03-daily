package com.example.chapter03daily.domain.comment.controller;

import com.example.chapter03daily.common.dto.ApiResponse;
import com.example.chapter03daily.domain.comment.dto.DailyDto;
import com.example.chapter03daily.domain.comment.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily")
public class DailyController {

    private DailyService dailyService;

    @PostMapping
    public ResponseEntity<ApiResponse<DailyDto.Response>> create(
            @RequestBody DailyDto.Request request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(dailyService.create(request)));
    }
}
