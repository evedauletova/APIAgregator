package com.yeva.dauletova.controllers;

import com.yeva.dauletova.models.WeatherObject;
import com.yeva.dauletova.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

@Controller

public class WeatherController {
    private WeatherService weatherService;
@Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeatherPage(Model model){
        WeatherObject weather = weatherService.getWeather("London");
        model.addAttribute("weather", weather);

        return "index";
    }
    @PostMapping("/weather")
    public String getWeather(@RequestParam String city, Model model){
        System.out.println(city);
        WeatherObject weather = weatherService.getWeather(city);
        model.addAttribute("weather", weather);
        return "index";
    }
}
