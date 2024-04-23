package com.yeva.dauletova.services;

import com.yeva.dauletova.models.WeatherObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class WeatherService {
    @Value("${api.key.weather}")
    private String APIkey;
    public WeatherObject getWeather(String city){
        WebClient webClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=" +
                APIkey
                );
        WeatherObject weather = webClient.get().retrieve().bodyToMono(WeatherObject.class).block();
        if(weather!=null){
            double temp = 1.8*(weather.getMain().getTemp()-273)+32;
            temp = Math.round(temp*10.0)/10.0;
            weather.getMain().setTemp(temp);
        }
        return weather;
    }
}
