package com.example.myweatherapp.activities;

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

import com.example.myweatherapp.R;

import java.util.Objects;

public class WeatherActivity extends AppCompatActivity {
    private ImageView optionImage;
    private ImageView settingsImage;
    private ImageView detailsCityImage;
    private ImageView infoCityImage;
    private ImageView listCitiesImage;

    private TextView cityName;
    private TextView temperature;
    private ImageView weatherImage;
    private TextView valueOfHumidity;
    private TextView valueOfUVIndex;
    private TextView valueOfChanceOfRain;
    private TextView valueOfPressure;
    private TextView valueOfWindSpeed;
    private TextView valueOfWindDirection;
    private TextView valueOfSunrise;
    private TextView valueOfSunset;

    private Bundle options = new Bundle();
    private Bundle settings = new Bundle();

    private TableRow rowHumidity;
    private TableRow rowUVIndex;
    private TableRow rowChanceOfRain;
    private TableRow rowPressure;
    private TableRow rowWindSpeed;
    private TableRow rowWindDirect;
    private TableRow rowSunrise;
    private TableRow rowSunset;

    public static final String cityKey = "cityKey";
    public static final String temperatureKey = "temperatureKey";
    public static final String weatherImageKey = "weatherImageKey";
    public static final String humidityKey = "humidityKey";
    public static final String uvIndexKey = "uvIndexKey";
    public static final String chanceOfRainKey = "chanceOfRainKey";
    public static final String pressureKey = "pressureKey";
    public static final String windSpeedKey = "windSpeedKey";
    public static final String windDirectKey = "windDirectKey";
    public static final String sunriseKey = "sunriseKey";
    public static final String sunsetKey = "sunsetKey";

    public static final String optionsDataKey = "optionsDataKey";
    public static final String settingsDataKey = "settingsDataKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_weather);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onRestoreInstanceState(savedInstanceState);
            options = savedInstanceState.getBundle(optionsDataKey);
            settings = savedInstanceState.getBundle(settingsDataKey);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(optionsDataKey, options);
            intent.putExtra(settingsDataKey, settings);
            startActivity(intent);
            finish();
        }

        findViews();
        setOnClickToListIcon();
        setOnClickToOptions();
        setOnClickToSettings();
        setOnClickToWikiIcon();
        setOnClickToYandexIcon();
        setCityFromOptions();
        getInfo();
        getAllOptionsFromDisplay();
        getSettingsFromMainDisplay();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        getAllOptionsFromDisplay();
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(optionsDataKey, options);
        intent.putExtra(settingsDataKey, settings);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        cityName = findViewById(R.id.cityName);
        temperature = findViewById(R.id.temperatureReading);
        weatherImage = findViewById(R.id.weatherImage);
        valueOfHumidity = findViewById(R.id.valueOfHumidity);
        valueOfUVIndex = findViewById(R.id.valueOfUVIndex);
        valueOfChanceOfRain = findViewById(R.id.valueOfChanceOfRain);
        valueOfPressure = findViewById(R.id.valueOfPressure);
        valueOfWindSpeed = findViewById(R.id.valueOfWindSpeed);
        valueOfWindDirection = findViewById(R.id.valueOfWindDirection);
        valueOfSunrise = findViewById(R.id.valueOfSunrise);
        valueOfSunset = findViewById(R.id.valueOfSunset);

        optionImage = findViewById(R.id.optionImage);
        settingsImage = findViewById(R.id.settingsImage);
        detailsCityImage = findViewById(R.id.detailsImage);
        infoCityImage = findViewById(R.id.infoImage);
        listCitiesImage = findViewById(R.id.listImage);

        rowHumidity = findViewById(R.id.rowHumidity);
        rowUVIndex = findViewById(R.id.rowUVIndex);
        rowChanceOfRain = findViewById(R.id.rowChanceOfRain);
        rowPressure = findViewById(R.id.rowPressure);
        rowWindSpeed = findViewById(R.id.rowWindSpeed);
        rowWindDirect = findViewById(R.id.rowWindDirection);
        rowSunrise = findViewById(R.id.rowSunrise);
        rowSunset = findViewById(R.id.rowSunset);

        options = getIntent().getBundleExtra(optionsDataKey);
    }

    private void getInfo() {
        if (getIntent().getBundleExtra(optionsDataKey) != null) {
            options = getIntent().getBundleExtra(optionsDataKey);
            setAllOptionsFromOptions();
        }
        if (getIntent().getBundleExtra(settingsDataKey) != null) {
            settings = getIntent().getBundleExtra(settingsDataKey);
            setAllSettings();
        }
    }

    private void setOnClickToListIcon() {
        listCitiesImage.setOnClickListener((v) -> {
            getSettingsFromMainDisplay();
            getAllOptionsFromDisplay();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(optionsDataKey, options);
            intent.putExtra(settingsDataKey, settings);
            startActivity(intent);
            finish();
        });
    }

    private void setOnClickToYandexIcon() {
        detailsCityImage.setOnClickListener((v) -> {
            String info = "https://yandex.ru/pogoda/" + options.getString(cityKey);
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void setOnClickToWikiIcon() {
        infoCityImage.setOnClickListener((v) -> {
            String info = "https://ru.wikipedia.org/wiki/" + options.getString(cityKey);
            Uri uri = Uri.parse(info);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void setOnClickToOptions() {
        optionImage.setOnClickListener((v) -> {
            getSettingsFromMainDisplay();
            getAllOptionsFromDisplay();
            Intent intent = new Intent(this, OptionsActivity.class);
            intent.putExtra(optionsDataKey, options);
            intent.putExtra(settingsDataKey, settings);
            startActivity(intent);
            finish();
        });
    }

    private void setOnClickToSettings() {
        settingsImage.setOnClickListener((v) -> {
            getSettingsFromMainDisplay();
            getAllOptionsFromDisplay();
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(settingsDataKey, settings);
            intent.putExtra(optionsDataKey, options);
            startActivity(intent);
            finish();
        });
    }

    private void setAllOptionsFromOptions() {
        setVisibilityFromOptions(rowHumidity);
        setVisibilityFromOptions(rowUVIndex);
        setVisibilityFromOptions(rowChanceOfRain);
        setVisibilityFromOptions(rowPressure);
        setVisibilityFromOptions(rowWindSpeed);
        setVisibilityFromOptions(rowWindDirect);
        setVisibilityFromOptions(rowSunrise);
        setVisibilityFromOptions(rowSunset);
    }

    private void setCityFromOptions() {
        cityName.setText(options.getString(cityKey));
        temperature.setText(options.getString(temperatureKey));
        weatherImage.setImageResource(options.getInt(weatherImageKey));
        valueOfHumidity.setText(options.getString(humidityKey));
        valueOfUVIndex.setText(options.getString(uvIndexKey));
        valueOfChanceOfRain.setText(options.getString(chanceOfRainKey));
        valueOfPressure.setText(options.getString(pressureKey));
        valueOfWindSpeed.setText(options.getString(windSpeedKey));
        valueOfWindDirection.setText(options.getString(windDirectKey));
        valueOfSunrise.setText(options.getString(sunriseKey));
        valueOfSunset.setText(options.getString(sunsetKey));
    }

    private void setVisibilityFromOptions(TableRow row) {
        TextView option = (TextView) row.getChildAt(0);
        String optionKey = option.getText().toString();
        if (!options.getBoolean(optionKey) && options.containsKey(optionKey)) {
            row.setVisibility(View.GONE);
        } else {
            row.setVisibility(View.VISIBLE);
        }
    }

    private void getAllOptionsFromDisplay() {
        getCityFromMainDisplay();
        getVisibilityFromMainDisplay(rowHumidity);
        getVisibilityFromMainDisplay(rowUVIndex);
        getVisibilityFromMainDisplay(rowChanceOfRain);
        getVisibilityFromMainDisplay(rowPressure);
        getVisibilityFromMainDisplay(rowWindSpeed);
        getVisibilityFromMainDisplay(rowWindDirect);
        getVisibilityFromMainDisplay(rowSunrise);
        getVisibilityFromMainDisplay(rowSunset);
    }

    private void getCityFromMainDisplay() {
        options.putString(temperatureKey, temperature.getText().toString());
        options.putString(pressureKey, valueOfPressure.getText().toString());
        options.putString(windSpeedKey, valueOfWindSpeed.getText().toString());
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

    private void getSettingsFromMainDisplay() {
        settings.putBoolean(SettingsActivity.tempValueCKey, temperature.getText().toString().contains(getString(R.string.degreesC)));
        settings.putBoolean(SettingsActivity.tempValueFKey, temperature.getText().toString().contains(getString(R.string.degreesF)));
        settings.putBoolean(SettingsActivity.pressureValMmKey, valueOfPressure.getText().toString().contains(getString(R.string.mm)));
        settings.putBoolean(SettingsActivity.pressureValGPaKey, valueOfPressure.getText().toString().contains(getString(R.string.gpa)));
        settings.putBoolean(SettingsActivity.windSpeedValMSKey, valueOfWindSpeed.getText().toString().contains(getString(R.string.ms)));
        settings.putBoolean(SettingsActivity.windSpeedValKmHKey, valueOfWindSpeed.getText().toString().contains(getString(R.string.kmh)));
    }

    private void setAllSettings() {
        setSettingValue(temperature,SettingsActivity.tempValueCKey, getString(R.string.degreesC));
        setSettingValue(temperature,SettingsActivity.tempValueFKey, getString(R.string.degreesF));
        setSettingValue(valueOfPressure,SettingsActivity.pressureValMmKey, getString(R.string.mm));
        setSettingValue(valueOfPressure,SettingsActivity.pressureValGPaKey, getString(R.string.gpa));
        setSettingValue(valueOfWindSpeed,SettingsActivity.windSpeedValMSKey, getString(R.string.ms));
        setSettingValue(valueOfWindSpeed,SettingsActivity.windSpeedValKmHKey, getString(R.string.kmh));
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
            } else if (key.equals(SettingsActivity.pressureValMmKey)) {
                newDigitValue = gPaToMm(digitalValue(value));
            } else if (key.equals(SettingsActivity.pressureValGPaKey)) {
                newDigitValue = mmToGPa(digitalValue(value));
            } else if (key.equals(SettingsActivity.windSpeedValMSKey)) {
                newDigitValue = kmhToMs(digitalValue(value));
            } else if (key.equals(SettingsActivity.windSpeedValKmHKey)) {
                newDigitValue = msToKmh(digitalValue(value));
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

    private int mmToGPa(int valMm) {
        return (int) Math.round(valMm * 1.333);
    }

    private int gPaToMm(int valGPa) {
        return (int) Math.round(valGPa * 0.75);
    }

    private int msToKmh(int valMs) {
        return (int) Math.round(valMs * 3.6);
    }

    private int kmhToMs(int valKmh) {
        return (int) Math.round(valKmh / 3.6);
    }


    private void checkTheme() {
        if (getIntent().getBundleExtra(settingsDataKey) != null) {
            if (Objects.requireNonNull(getIntent().getBundleExtra(settingsDataKey)).getBoolean(SettingsActivity.nightThemeKey)) {
                setTheme(R.style.AppThemeDark);
            } else {
                setTheme(R.style.AppTheme);
            }
        }
    }
}