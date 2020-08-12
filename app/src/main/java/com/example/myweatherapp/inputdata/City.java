package com.example.myweatherapp.inputdata;

import android.content.Context;

import com.example.myweatherapp.R;

import java.util.Random;

public class City {
    private String name;
    private String temperature;
    private int weatherImage;
    private String humidity;
    private String uvIndex;
    private String chanceOfRain;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String sunrise;
    private String sunset;

    Context context;

    Random rand = new Random();

    public City(String name, Context context) {
        this.name = name;
        this.context = context;

        this.temperature = context.getResources().getStringArray(R.array.temperature)[rand.nextInt(context.getResources().getStringArray(R.array.temperature).length)];
        this.weatherImage = context.getResources().getIntArray(R.array.weatherImage)[rand.nextInt(context.getResources().getStringArray(R.array.weatherImage).length)];
        this.humidity = context.getResources().getStringArray(R.array.humidity)[rand.nextInt(context.getResources().getStringArray(R.array.humidity).length)];
        this.uvIndex = context.getResources().getStringArray(R.array.uvIndex)[rand.nextInt(context.getResources().getStringArray(R.array.uvIndex).length)];
        this.chanceOfRain = context.getResources().getStringArray(R.array.chanceOfRain)[rand.nextInt(context.getResources().getStringArray(R.array.chanceOfRain).length)];
        this.pressure = context.getResources().getStringArray(R.array.pressure)[rand.nextInt(context.getResources().getStringArray(R.array.pressure).length)];
        this.windSpeed = context.getResources().getStringArray(R.array.windSpeed)[rand.nextInt(context.getResources().getStringArray(R.array.windSpeed).length)];
        this.windDirection = context.getResources().getStringArray(R.array.windDirection)[rand.nextInt(context.getResources().getStringArray(R.array.windDirection).length)];
        this.sunrise = context.getResources().getStringArray(R.array.sunrise)[rand.nextInt(context.getResources().getStringArray(R.array.sunrise).length)];
        this.sunset = context.getResources().getStringArray(R.array.sunset)[rand.nextInt(context.getResources().getStringArray(R.array.sunset).length)];
    }

    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getWeatherImage() {
        return weatherImage;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public String getChanceOfRain() {
        return chanceOfRain;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
