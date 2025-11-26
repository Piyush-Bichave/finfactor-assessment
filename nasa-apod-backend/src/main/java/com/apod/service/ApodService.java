package com.apod.service;

import com.apod.client.NasaClient;
import com.apod.dto.ApodResponse;
import com.apod.exception.ApodNotFoundException;
import com.apod.exception.InvalidDateException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApodService {

    private final NasaClient nasaClient;
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final LocalDate NASA_MIN_DATE = LocalDate.of(1995, 6, 16);

    public ApodService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
    }

    private void validateDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (date.isBefore(NASA_MIN_DATE) || date.isAfter(today)) {
            throw new InvalidDateException("APOD is only available between 1995-06-16 and today.");
        }
    }

    @Cacheable(value = "apodByDate", key = "#date")
    public ApodResponse getApodByDate(LocalDate date) {
        validateDate(date);
        ApodResponse resp = nasaClient.getApodByDate(date);
        if (resp == null || ObjectUtils.isEmpty(resp.getDate())) {
            throw new ApodNotFoundException("APOD not found for date: " + date.format(fmt));
        }
        return resp;
    }

    @Cacheable(value = "apodByDate", key = "'today'")
    public ApodResponse getTodayApod() {
        LocalDate today = LocalDate.now();
        ApodResponse resp = nasaClient.getTodayApod();
        if (resp == null || ObjectUtils.isEmpty(resp.getDate())) {
            throw new ApodNotFoundException("Today's APOD not found.");
        }
        return resp;
    }

    @Cacheable(value = "recentApods", key = "#count")
    public List<ApodResponse> getRecentApods(int count) {
        if (count <= 0) throw new InvalidDateException("count must be >= 1");
        if (count > 60) throw new InvalidDateException("count must be <= 60");
        List<ApodResponse> list = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < count; i++) {
            LocalDate d = today.minusDays(i);

            if (d.isBefore(NASA_MIN_DATE)) break;
            ApodResponse r = nasaClient.getApodByDate(d);
            if (r != null) {
                list.add(r);
            }
        }
        if (list.isEmpty()) {
            throw new ApodNotFoundException("No APODs found for the requested recent range.");
        }
        return list;
    }
}
