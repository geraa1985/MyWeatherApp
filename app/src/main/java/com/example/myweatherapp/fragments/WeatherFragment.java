package com.example.myweatherapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myweatherapp.R;
import com.example.myweatherapp.activities.SettingsActivity;
import com.example.myweatherapp.activities.WeatherActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class WeatherFragment extends Fragment {

    private ImageView settingsImage;
    private ImageView detailsCity;
    private ImageView infoCity;

    private TextView cityName;
    private TextView temperature;
    private ImageView weatherImage;

    private Bundle options = new Bundle();
    private Bundle settings = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        findViews();
        setInfoFromBundle();
        setOnClickBehaviourToSettings();
        setOnClickInfoImage();
        setOnClickDetailsImage();
        getInfoFromWeatherFragment();
        setAllSettings();
    }

    private void findViews() {
        cityName = Objects.requireNonNull(getActivity()).findViewById(R.id.cityName);
        temperature = Objects.requireNonNull(getActivity()).findViewById(R.id.temperatureReading);
        weatherImage = Objects.requireNonNull(getActivity()).findViewById(R.id.weatherImage);
        settingsImage = Objects.requireNonNull(getActivity()).findViewById(R.id.settingsImage);
        detailsCity = Objects.requireNonNull(getActivity()).findViewById(R.id.detailsImage);
        infoCity = Objects.requireNonNull(getActivity()).findViewById(R.id.infoImage);
    }

    private void setInfoFromBundle() {
        if (Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.optionsDataKey) != null) {
            options = Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.optionsDataKey);
            setCityFromOptions();
        }
        if (Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.settingsDataKey) != null) {
            settings = Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.settingsDataKey);
            setAllSettings();
        }
    }

    private void setCityFromOptions() {
        cityName.setText(options.getString(WeatherActivity.cityKey));
        temperature.setText(options.getString(WeatherActivity.temperatureKey));
        Picasso.get().load(options.getString(WeatherActivity.weatherImageKey)).resizeDimen(R.dimen.width_weatherImage, R.dimen.height_weatherImage).into(weatherImage);
    }

    private void getInfoFromWeatherFragment() {
        options.putString(WeatherActivity.temperatureKey, temperature.getText().toString());
        options.putString(WeatherActivity.cityKey, cityName.getText().toString());
        getSettingsFromMainDisplay();
    }

    private void setOnClickBehaviourToSettings() {
        settingsImage.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            getInfoFromWeatherFragment();
            intent.putExtra(WeatherActivity.settingsDataKey, settings);
            intent.putExtra(WeatherActivity.optionsDataKey, options);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        });
    }

    private void setOnClickInfoImage() {
        infoCity.setOnClickListener((v) -> {
            String info = "https://ru.wikipedia.org/wiki/" + cityName.getText().toString();
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void setOnClickDetailsImage() {
        detailsCity.setOnClickListener((v) -> {
            String info = "https://yandex.ru/pogoda/" + cityName.getText().toString();
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
    private void getSettingsFromMainDisplay() {
        settings.putBoolean(SettingsActivity.tempValueCKey, temperature.getText().toString().contains(getString(R.string.degreesC)));
        settings.putBoolean(SettingsActivity.tempValueFKey, temperature.getText().toString().contains(getString(R.string.degreesF)));
    }

    private void setAllSettings() {
        setSettingValue(temperature, SettingsActivity.tempValueCKey, getString(R.string.degreesC));
        setSettingValue(temperature,SettingsActivity.tempValueFKey, getString(R.string.degreesF));
    }

    private void setSettingValue(TextView textView, String key, String units) {
        String value = textView.getText().toString();
        boolean isUnits = value.contains(units);

        if (settings.getBoolean(key) && !isUnits) {
            int newDigitValue;
            if (key.equals(SettingsActivity.tempValueCKey)) {
                newDigitValue = fToC(digitalValue(value));
            } else if (key.equals(SettingsActivity.tempValueFKey)) {
                newDigitValue = cToF(digitalValue(value));
            } else {
                newDigitValue = 0;
            }
            textView.setText(newValue(value, newDigitValue, units));
        }
    }

    private int digitalValue(String value) {
        StringBuilder sb = new StringBuilder(value);
        StringBuilder digitalValue = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isDigit(sb.charAt(i))) {
                digitalValue.append(sb.charAt(i));
            }
        }
        return Integer.parseInt(digitalValue.toString());
    }

    private String newValue(String value, int newDigitValue, String units) {
        StringBuilder sb = new StringBuilder(value);
        StringBuilder newValue = new StringBuilder();

        if (!Character.isDigit(sb.charAt(0))) {
            newValue.append(sb.charAt(0)).append(newDigitValue).append(units);
        } else {
            newValue.append(newDigitValue).append(" ").append(units);
        }

        return newValue.toString();
    }

    private int cToF(int valC) {
        return (int) Math.round(valC * 1.8 + 32);
    }

    private int fToC(int valF) {
        return (int) Math.round((valF - 32) / 1.8);
    }
}
