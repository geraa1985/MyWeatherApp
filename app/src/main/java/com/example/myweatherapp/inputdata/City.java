package com.example.myweatherapp.inputdata;

import android.annotation.SuppressLint;

import com.example.myweatherapp.BuildConfig;
import com.example.myweatherapp.fragments.OptionsFragment;
import com.example.myweatherapp.inputdata.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class City {
    private String name;
    private String temperature;
    private String weatherImage;
    private String humidity;
    private String feelsLikeTemp;
    private String visibility;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String sunrise;
    private String sunset;

    private static LinkedList<String> citiesList = new LinkedList<>(Arrays.asList(OptionsFragment.getCities()));

    public City(String name) {
        this.name = name;
        this.getWeather();
    }

    public static LinkedList<String> getCitiesList() {
        return citiesList;
    }

    public void getWeather() {
        try {
            final URL uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + name + "&lang=ru&units=metric&appid=" + BuildConfig.WEATHER_API_KEY);
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(in);
                Gson gson = new Gson();
                WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                displayWeather(weatherRequest);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void displayWeather(WeatherRequest weatherRequest) {
        String currentTemp = Math.round(weatherRequest.getMain().getTemp()) + "°C";
        String currentFeelsLikeTemp = Math.round(weatherRequest.getMain().getFeels_like()) + "°C";


        if (Character.isDigit(currentTemp.charAt(0)) && currentTemp.charAt(0) != '0') {
            this.temperature = "+" + currentTemp;
        } else {
            this.temperature = currentTemp;
        }

        if (Character.isDigit(currentFeelsLikeTemp.charAt(0)) && currentFeelsLikeTemp.charAt(0) != '0') {
            this.feelsLikeTemp = "+" + currentFeelsLikeTemp;
        } else {
            this.feelsLikeTemp = currentFeelsLikeTemp;
        }

        this.weatherImage = "http://openweathermap.org/img/wn/" + weatherRequest.getWeather()[0].getIcon() + "@2x.png";
        this.humidity = weatherRequest.getMain().getHumidity() + "%";
        this.visibility = weatherRequest.getVisibility() + " m";
        this.pressure = weatherRequest.getMain().getPressure() + " gPa";
        this.windSpeed = Math.round(weatherRequest.getWind().getSpeed()) + " m/s";
        this.windDirection = weatherRequest.getWind().getDeg() + "°";


        this.sunrise = getTimeFromUnix(weatherRequest.getSys().getSunrise(), weatherRequest.getTimezone()) + " AM";
        this.sunset = getTimeFromUnix(weatherRequest.getSys().getSunset(), weatherRequest.getTimezone()) + " PM";
    }

    private String getTimeFromUnix(long unixTime, int timezone) {
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis((unixTime + timezone) * 1000);
        return myDate.get(Calendar.HOUR) + ":" + myDate.get(Calendar.MINUTE) + ":" + myDate.get(Calendar.SECOND);
    }

    private String getLines(BufferedReader reader) {
        StringBuilder rawData = new StringBuilder(1024);

        while (true) {
            try {
                String tempVariable = reader.readLine();
                if (tempVariable == null) break;
                rawData.append(tempVariable).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawData.toString();
    }

    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeatherImage() {
        return weatherImage;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public String getVisibility() {
        return visibility;
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
