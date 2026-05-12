package com.weather.weatherapp.service;

import com.weather.weatherapp.entity.WeatherResponse;
import com.weather.weatherapp.repository.WeatherResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherResponseService {
    private final WeatherResponseRepository weatherRepository;

    public WeatherResponse getWeather(String city) {
        return weatherRepository.findByCity(city);
    }
}