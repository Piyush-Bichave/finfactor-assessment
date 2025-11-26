package com.apod.controller;

import com.apod.common.ApiResponse;
import com.apod.dto.ApodResponse;
import com.apod.service.ApodService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api")
@Validated
public class ApodController {

    private final ApodService apodService;

    public ApodController(ApodService apodService) {
        this.apodService = apodService;
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        ApiResponse<String> resp = ApiResponse.success(200, "OK", "Service is up");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/apod/today")
    public ResponseEntity<ApiResponse<ApodResponse>> getToday() {
        ApodResponse resp = apodService.getTodayApod();
        ApiResponse<ApodResponse> wrapper = ApiResponse.success(200, "Today's APOD fetched successfully", resp);
        return ResponseEntity.ok(wrapper);
    }

    @GetMapping("/apod")
    public ResponseEntity<ApiResponse<ApodResponse>> getByDate(
            @RequestParam(name = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ApodResponse resp = apodService.getApodByDate(date);
        ApiResponse<ApodResponse> wrapper = ApiResponse.success(200, "APOD fetched for date " + date, resp);
        return ResponseEntity.ok(wrapper);
    }

    @GetMapping("/apod/recent")
    public ResponseEntity<ApiResponse<List<ApodResponse>>> getRecent(
            @RequestParam(name = "count", required = false, defaultValue = "10") @Min(1) @Max(60) int count
    ) {
        List<ApodResponse> list = apodService.getRecentApods(count);
        ApiResponse<List<ApodResponse>> wrapper = ApiResponse.success(200, "Recent APODs fetched", list);
        return ResponseEntity.ok(wrapper);
    }
}
