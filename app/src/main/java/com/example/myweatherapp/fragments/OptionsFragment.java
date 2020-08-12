package com.example.myweatherapp.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myweatherapp.R;
import com.example.myweatherapp.activities.WeatherActivity;

import java.util.Objects;

public class OptionsFragment extends Fragment {
    private ImageView saveImage;

    private EditText enterCity;

    private CheckBox humidity;
    private CheckBox uvIndex;
    private CheckBox chanceOfRain;
    private CheckBox pressure;
    private CheckBox windSpeed;
    private CheckBox windDirect;
    private CheckBox sunrise;
    private CheckBox sunset;

    private Bundle options = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        findViews();
        setOnClickBehaviourToSave();
    }

    private void findViews() {
        saveImage = Objects.requireNonNull(getActivity()).findViewById(R.id.homeImage);
        enterCity = Objects.requireNonNull(getActivity()).findViewById(R.id.enterCity);
        humidity = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxHumidity);
        uvIndex = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxUVIndex);
        chanceOfRain = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxChanceOfRain);
        pressure = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxPressure);
        windSpeed = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxWindSpeed);
        windDirect = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxWindDirection);
        sunrise = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxSunrise);
        sunset = Objects.requireNonNull(getActivity()).findViewById(R.id.checkBoxSunset);
    }

    private void setOnClickBehaviourToSave() {
        saveImage.setOnClickListener((v) -> {
            enteredCity();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                checkAllOfOptions();
                Intent intent = new Intent(Objects.requireNonNull(getActivity()), WeatherActivity.class);
                intent.putExtra(WeatherActivity.optionsDataKey, options);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                getActivity().finish();
            } else {
                TextView city = Objects.requireNonNull(getActivity()).findViewById(R.id.cityName);
                city.setText(options.getString(WeatherActivity.cityKey));
            }
        });
    }

    private void checkAllOfOptions() {
        checkToCheckboxClicked(humidity);
        checkToCheckboxClicked(uvIndex);
        checkToCheckboxClicked(chanceOfRain);
        checkToCheckboxClicked(pressure);
        checkToCheckboxClicked(windSpeed);
        checkToCheckboxClicked(windDirect);
        checkToCheckboxClicked(sunrise);
        checkToCheckboxClicked(sunset);
    }

    private void enteredCity() {
        if (!enterCity.getText().toString().equals("")) {
            options.putString(WeatherActivity.cityKey, enterCity.getText().toString());
        }
    }

    private void checkToCheckboxClicked(CheckBox box) {
        if (box.isChecked()) {
            options.putBoolean(box.getText().toString(), true);
        } else {
            options.putBoolean(box.getText().toString(), false);
        }
    }
}