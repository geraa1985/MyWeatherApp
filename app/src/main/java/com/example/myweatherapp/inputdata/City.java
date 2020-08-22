package com.example.myweatherapp.inputdata;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.myweatherapp.BuildConfig;
import com.example.myweatherapp.R;
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
import java.util.Locale;

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
    private String lang = Locale.getDefault().getISO3Language().substring(0, 2);

    private Context context;

    private static LinkedList<String> citiesList = new LinkedList<>(Arrays.asList(OptionsFragment.getCities()));

    public City(String name, Context context) {
        this.name = name;
        this.context = context;
        this.getWeather();
    }

    public static LinkedList<String> getCitiesList() {
        return citiesList;
    }

    public void getWeather() {
        try {
            final URL uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + name + "&lang=" + lang + "&units=metric&appid=" + BuildConfig.WEATHER_API_KEY);
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
        String currentTemp = Math.round(weatherRequest.getMain().getTemp()) + context.getResources().getString(R.string.degreesC);
        String currentFeelsLikeTemp = Math.round(weatherRequest.getMain().getFeels_like()) + context.getString(R.string.degreesC);


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
        this.humidity = weatherRequest.getMain().getHumidity() + context.getString(R.string.percent);
        this.visibility = weatherRequest.getVisibility() + " " + context.getString(R.string.m);
        this.pressure = weatherRequest.getMain().getPressure() + " " + context.getString(R.string.gpa);
        this.windSpeed = Math.round(weatherRequest.getWind().getSpeed()) + " " + context.getString(R.string.ms);
        this.windDirection = weatherRequest.getWind().getDeg() + context.getString(R.string.degrees);


        this.sunrise = getTimeFromUnix(weatherRequest.getSys().getSunrise()) + " " + context.getString(R.string.am);
        this.sunset = getTimeFromUnix(weatherRequest.getSys().getSunset()) + " " + context.getString(R.string.pm);
    }

    private String getTimeFromUnix(long unixTime) {
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis((unixTime) * 1000);
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
