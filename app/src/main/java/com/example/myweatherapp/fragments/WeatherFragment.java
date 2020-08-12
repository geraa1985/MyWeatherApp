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

import java.util.Objects;

public class WeatherFragment extends Fragment {

    private ImageView settingsImage;
    private ImageView detailsCity;
    private ImageView infoCity;

    private TextView temperatureTextView;
    private TextView cityName;

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
    }

    private void findViews() {
        temperatureTextView = Objects.requireNonNull(getActivity()).findViewById(R.id.temperatureReading);
        settingsImage = Objects.requireNonNull(getActivity()).findViewById(R.id.settingsImage);
        cityName = Objects.requireNonNull(getActivity()).findViewById(R.id.cityName);
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
            setAllSettingsFromSettings();
        }
    }

    private void setCityFromOptions() {
        cityName.setText(options.getString(WeatherActivity.cityKey));
    }

    private void setAllSettingsFromSettings() {
        temperatureTextView.setText(settings.getString(WeatherActivity.temperatureKey));
    }

    private void getInfoFromWeatherFragment() {
        options.putString(WeatherActivity.temperatureKey, temperatureTextView.getText().toString());
        options.putString(WeatherActivity.cityKey, cityName.getText().toString());
        settings.putString(WeatherActivity.temperatureKey, temperatureTextView.getText().toString());
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

}
