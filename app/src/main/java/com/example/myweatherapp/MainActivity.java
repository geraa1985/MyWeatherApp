package com.example.myweatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
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

    static final String temperatureKey = "temperatureKey";
    static final String pressureKey = "pressureKey";
    static final String windSpeedKey = "windSpeedKey";
    static final String cityKey = "cityKey";
    static final String optionsDataKey = "optionsDataKey";
    static final String settingsDataKey = "settingsDataKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setOnClickBehaviourToTemperature();
        setOnClickBehaviourToOptions();
        setOnClickBehaviourToSettings();
        setOnClickInfoImage();
        setOnClickDetailsImage();

        getAllOptionsFromMainDisplay();
        getSettingsFromMainDisplay();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
        if (getIntent().getBundleExtra(optionsDataKey)!=null) {
            options = getIntent().getBundleExtra(optionsDataKey);
            setAllOptionsFromOptions();
        }
        if (getIntent().getBundleExtra(settingsDataKey)!=null) {
            settings = getIntent().getBundleExtra(settingsDataKey);
            setAllSettingsFromSettings();
            getAllOptionsFromMainDisplay();
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

    private void setOnClickBehaviourToTemperature() {
        temperatureTextView.setOnClickListener((view) -> {
            StringBuilder sb = new StringBuilder(temperatureTextView.getText().toString());
            StringBuilder digitValue = new StringBuilder();
            for (int i = 0; i < sb.length(); i++) {
                if (Character.isDigit(sb.charAt(i))) {
                    digitValue.append(sb.charAt(i));
                }
            }
            int newDigitValue = Integer.parseInt(digitValue.toString()) + 3;

            StringBuilder newValue = new StringBuilder();
            newValue.append(sb.charAt(0));
            newValue.append(newDigitValue);
            for (int i = 1; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i))) {
                    newValue.append(sb.charAt(i));
                }
            }

            temperatureTextView.setText(newValue.toString());
            options.putString(temperatureKey, newValue.toString());
            settings.putString(temperatureKey, newValue.toString());
        });
    }

    private void setOnClickBehaviourToOptions() {
        optionImage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, OptionsActivity.class);
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
        temperatureTextView.setText(options.getString(temperatureKey));
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

    private void getAllOptionsFromMainDisplay() {
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
        infoCity.setOnClickListener((v)->{
            String info = "https://ru.wikipedia.org/wiki/" + options.getString(cityKey);
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void setOnClickDetailsImage() {
        detailsCity.setOnClickListener((v)->{
            String info = "https://yandex.ru/pogoda/" + options.getString(cityKey);
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}