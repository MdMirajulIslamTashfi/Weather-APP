package com.weather.weatherapp.controller;

import com.weather.weatherapp.entity.WeatherResponse;
import com.weather.weatherapp.service.WeatherResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherResponseController {
    private final WeatherResponseService weatherService;

    // GET /api/weather?city=Dhaka
    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam (defaultValue = "Dhaka") String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));
    }
}
