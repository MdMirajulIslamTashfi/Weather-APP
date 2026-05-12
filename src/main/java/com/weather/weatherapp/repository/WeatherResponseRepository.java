package com.weather.weatherapp.repository;


import com.weather.weatherapp.dto.OpenWeatherApiResponse;
import com.weather.weatherapp.entity.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Repository
public class WeatherResponseRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api_key}")
    private String apiKey;
    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherResponse findByCity(String city) {
        URI url = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("appid", apiKey)
                .queryParam("q", city)
                .queryParam("units", "metric")
                .build()
                .encode()
                .toUri();

        OpenWeatherApiResponse apiResp = restTemplate.getForObject(url, OpenWeatherApiResponse.class);
        if (apiResp == null) {
            throw new RuntimeException("No response received from weather API");
        }

        int offsetSeconds = apiResp.getTimezone();
        Instant instant = Instant.ofEpochSecond(apiResp.getDt());
        OffsetDateTime cityTime = instant.atOffset(ZoneOffset.ofTotalSeconds(offsetSeconds));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = cityTime.format(timeFormatter);

        return WeatherResponse.builder()
                .location(apiResp.getName() + ", " + apiResp.getSys().getCountry())
                .temperature(apiResp.getMain().getTemp())
                .time(formattedTime)
                .build();
    }
}