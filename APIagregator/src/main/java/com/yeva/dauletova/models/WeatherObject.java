package com.yeva.dauletova.models;

import lombok.Data;

import java.util.List;

@Data
public class WeatherObject {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String name;
    private Coord coord;
    @Data
    public static class Main{
        public double temp;

        private double pressure;

    }
    @Data
    public static class Weather{
        private String main;
        private String description;
        private String icon;
    }
    @Data
    public static class Wind{
        private double speed;
    }
    @Data
    public static class Coord{
        private double lon;
        private double lat;

    }
}
