package com.example.chapter03daily.domain.daily.controller;

import com.example.chapter03daily.common.dto.ApiResponse;
import com.example.chapter03daily.domain.daily.dto.DailyDto;
import com.example.chapter03daily.domain.daily.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dailies")
public class DailyController {

    private final DailyService dailyService;

    @PostMapping
    public ResponseEntity<ApiResponse<DailyDto.Response>> create(
            @RequestBody DailyDto.Request request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(dailyService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<DailyDto.Response>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(dailyService.findAll(page, size)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DailyDto.Response>> update(
            @PathVariable long id,
            @RequestBody DailyDto.Request request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(dailyService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable long id,
            @RequestParam String password
    ) {
        dailyService.delete(id, password);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.noContent());
    }
}
