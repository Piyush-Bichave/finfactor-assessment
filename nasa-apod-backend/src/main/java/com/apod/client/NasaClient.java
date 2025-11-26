package com.apod.client;

import com.apod.dto.ApodResponse;
import com.apod.exception.ApodNotFoundException;
import com.apod.exception.InvalidDateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;


@Component
public class NasaClient {

    private final RestTemplate restTemplate;

    @Value("${nasa.api.key}")
    private String apiKey;

    public NasaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApodResponse getApodByDate(LocalDate date) {

        if (date.isBefore(LocalDate.of(1995, 6, 16))) {
            throw new InvalidDateException("APOD data is only available after 1995-06-16.");
        }

        URI uri = UriComponentsBuilder
                .fromUri(URI.create("https://api.nasa.gov/planetary/apod"))
                .queryParam("api_key", apiKey)
                .queryParam("date", date.toString())
                .build()
                .toUri();


        try {
            return restTemplate.getForObject(uri, ApodResponse.class);
        } catch (Exception e) {
            throw new ApodNotFoundException("No APOD found for date: " + date);
        }
    }

    public ApodResponse getTodayApod() {

        URI uri = UriComponentsBuilder
                .fromUri(URI.create("https://api.nasa.gov/planetary/apod"))
                .queryParam("api_key", apiKey)
                .build()
                .toUri();

        try {
            return restTemplate.getForObject(uri, ApodResponse.class);
        } catch (Exception e) {
            throw new ApodNotFoundException("Unable to fetch today's APOD");
        }
    }
}
