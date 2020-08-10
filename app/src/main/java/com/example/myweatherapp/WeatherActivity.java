package com.example.myweatherapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {
    private ImageView optionImage;
    private ImageView settingsImage;
    private ImageView detailsCity;
    private ImageView infoCity;

    private TextView temperatureTextView;
    private TextView cityName;
    private TextView valueOfPressure;
    private TextView valueOfWindSpeed;

    private Bundle options = new Bundle();
    private Bundle settings = new Bundle();

    private TableRow humidity;
    private TableRow uvIndex;
    private TableRow chanceOfRain;
    private TableRow pressure;
    private TableRow windSpeed;
    private TableRow windDirect;
    private TableRow sunrise;
    private TableRow sunset;

    public static final String temperatureKey = "temperatureKey";
    public static final String pressureKey = "pressureKey";
    public static final String windSpeedKey = "windSpeedKey";
    public static final String cityKey = "cityKey";
    public static final String optionsDataKey = "optionsDataKey";
    public static final String settingsDataKey = "settingsDataKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            super.onRestoreInstanceState(savedInstanceState);
            options = savedInstanceState.getBundle(optionsDataKey);
            settings = savedInstanceState.getBundle(settingsDataKey);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(optionsDataKey, options);
            intent.putExtra(settingsDataKey, settings);
            startActivity(intent);
            finish();
            return;
        }

        findViews();
        setOnClickBehaviourToOptions();
        setOnClickBehaviourToSettings();
        setOnClickInfoImage();
        setOnClickDetailsImage();
        getInfo();
        getAllOptionsFromDisplay();
        getSettingsFromMainDisplay();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBundle(optionsDataKey, options);
        outState.putBundle(settingsDataKey, settings);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        options = savedInstanceState.getBundle(optionsDataKey);
        settings = savedInstanceState.getBundle(settingsDataKey);
        setAllOptionsFromOptions();
        setAllSettingsFromSettings();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getInfo();
    }

    private void getInfo() {
        if (getIntent().getBundleExtra(optionsDataKey) != null) {
            options = getIntent().getBundleExtra(optionsDataKey);
            setAllOptionsFromOptions();
        }
        if (getIntent().getBundleExtra(settingsDataKey) != null) {
            settings = getIntent().getBundleExtra(settingsDataKey);
            setAllSettingsFromSettings();
            getAllOptionsFromDisplay();
        }
    }

    private void findViews() {
        temperatureTextView = findViewById(R.id.temperatureReading);
        optionImage = findViewById(R.id.optionImage);
        settingsImage = findViewById(R.id.settingsImage);
        cityName = findViewById(R.id.cityName);
        detailsCity = findViewById(R.id.detailsImage);
        infoCity = findViewById(R.id.infoImage);

        humidity = findViewById(R.id.rowHumidity);
        uvIndex = findViewById(R.id.rowUVIndex);
        chanceOfRain = findViewById(R.id.rowChanceOfRain);
        pressure = findViewById(R.id.rowPressure);
        windSpeed = findViewById(R.id.rowWindSpeed);
        windDirect = findViewById(R.id.rowWindDirection);
        sunrise = findViewById(R.id.rowSunrise);
        sunset = findViewById(R.id.rowSunset);

        valueOfPressure = findViewById(R.id.valueOfPressure);
        valueOfWindSpeed = findViewById(R.id.valueOfWindSpeed);
    }

    private void setOnClickBehaviourToOptions() {
        optionImage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(optionsDataKey, options);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void setOnClickBehaviourToSettings() {
        settingsImage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(settingsDataKey, settings);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void setAllOptionsFromOptions() {
        setCityFromOptions();
        setVisibilityFromOptions(humidity);
        setVisibilityFromOptions(uvIndex);
        setVisibilityFromOptions(chanceOfRain);
        setVisibilityFromOptions(pressure);
        setVisibilityFromOptions(windSpeed);
        setVisibilityFromOptions(windDirect);
        setVisibilityFromOptions(sunrise);
        setVisibilityFromOptions(sunset);
    }

    private void setCityFromOptions() {
        cityName.setText(options.getString(cityKey));
    }

    private void setVisibilityFromOptions(TableRow row) {
        TextView option = (TextView) row.getChildAt(0);
        String optionKey = option.getText().toString();
        if (!options.getBoolean(optionKey)) {
            row.setVisibility(View.GONE);
        } else {
            row.setVisibility(View.VISIBLE);
        }
    }

    private void getAllOptionsFromDisplay() {
        getCityFromMainDisplay();
        getVisibilityFromMainDisplay(humidity);
        getVisibilityFromMainDisplay(uvIndex);
        getVisibilityFromMainDisplay(chanceOfRain);
        getVisibilityFromMainDisplay(pressure);
        getVisibilityFromMainDisplay(windSpeed);
        getVisibilityFromMainDisplay(windDirect);
        getVisibilityFromMainDisplay(sunrise);
        getVisibilityFromMainDisplay(sunset);
    }

    private void getCityFromMainDisplay() {
        options.putString(temperatureKey, temperatureTextView.getText().toString());
        options.putString(cityKey, cityName.getText().toString());
    }

    private void getVisibilityFromMainDisplay(TableRow row) {
        TextView option = (TextView) row.getChildAt(0);
        String optionKey = option.getText().toString();
        int visibility = row.getVisibility();
        if (visibility == View.VISIBLE) {
            options.putBoolean(optionKey, true);
        } else {
            options.putBoolean(optionKey, false);
        }
    }

    private void setAllSettingsFromSettings() {
        temperatureTextView.setText(settings.getString(temperatureKey));
        valueOfPressure.setText(settings.getString(pressureKey));
        valueOfWindSpeed.setText(settings.getString(windSpeedKey));
    }

    private void getSettingsFromMainDisplay() {
        settings.putString(temperatureKey, temperatureTextView.getText().toString());
        settings.putString(pressureKey, valueOfPressure.getText().toString());
        settings.putString(windSpeedKey, valueOfWindSpeed.getText().toString());
    }

    private void setOnClickInfoImage() {
        infoCity.setOnClickListener((v) -> {
            String info = "https://ru.wikipedia.org/wiki/" + options.getString(cityKey);
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void setOnClickDetailsImage() {
        detailsCity.setOnClickListener((v) -> {
            String info = "https://yandex.ru/pogoda/" + options.getString(cityKey);
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}