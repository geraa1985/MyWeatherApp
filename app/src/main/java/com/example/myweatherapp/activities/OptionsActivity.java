package com.example.myweatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myweatherapp.R;
import com.example.myweatherapp.inputdata.City;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

public class OptionsActivity extends AppCompatActivity {

    private ImageView saveImage;

    private CheckBox humidity;
    private CheckBox uvIndex;
    private CheckBox chanceOfRain;
    private CheckBox pressure;
    private CheckBox windSpeed;
    private CheckBox windDirect;
    private CheckBox sunrise;
    private CheckBox sunset;

    private TextInputEditText enterCity;

    private boolean isValid;

    private Pattern newCityRules = Pattern.compile("^[A-Z][a-z]{2,}$");
    private String newCityName;
    private City newCity;

    private Bundle options = new Bundle();
    private Bundle settings = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_options);

        findViews();
        setOnClickBehaviourToSave();
        checkCityField();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkAllOfOptions();
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(WeatherActivity.optionsDataKey, options);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        saveImage = findViewById(R.id.homeImage);
        humidity = findViewById(R.id.checkBoxHumidity);
        uvIndex = findViewById(R.id.checkBoxUVIndex);
        chanceOfRain = findViewById(R.id.checkBoxChanceOfRain);
        pressure = findViewById(R.id.checkBoxPressure);
        windSpeed = findViewById(R.id.checkBoxWindSpeed);
        windDirect = findViewById(R.id.checkBoxWindDirection);
        sunrise = findViewById(R.id.checkBoxSunrise);
        sunset = findViewById(R.id.checkBoxSunset);
        options = getIntent().getBundleExtra(WeatherActivity.optionsDataKey);
        settings = getIntent().getBundleExtra(WeatherActivity.settingsDataKey);
        enterCity = findViewById(R.id.enter_city);
    }

    private void setOnClickBehaviourToSave() {
        saveImage.setOnClickListener((v) -> {
            enterCity.clearFocus();
            if(isValid) {
                if (newCityName != null) {
                    newCity = new City(newCityName, this);
                    City.getCitiesList().add(newCity);
                    setChangeFromNewCity(newCity);
                }
                checkAllOfOptions();
                Intent intent = new Intent(this, WeatherActivity.class);
                intent.putExtra(WeatherActivity.optionsDataKey, options);
                intent.putExtra(WeatherActivity.settingsDataKey, settings);
                startActivity(intent);
                finish();
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

    private void checkToCheckboxClicked(CheckBox box) {
        if (box.isChecked()) {
            options.putBoolean(box.getText().toString(), true);
        } else {
            options.putBoolean(box.getText().toString(), false);
        }
    }


    private void checkTheme() {
        if (getIntent().getBundleExtra(WeatherActivity.settingsDataKey) != null) {
            if (Objects.requireNonNull(getIntent().getBundleExtra(WeatherActivity.settingsDataKey)).getBoolean(SettingsActivity.nightThemeKey)) {
                setTheme(R.style.AppThemeDark);
            } else {
                setTheme(R.style.AppTheme);
            }
        }
    }


    private void checkCityField() {
        enterCity.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                TextView inputText = (TextView) view;
                isValid = validate(inputText, newCityRules);
            }
        });
    }

    private boolean validate(TextView inputText, Pattern newCityRules) {
        String value = inputText.getText().toString();
        if (!value.equals("")) {
            if (newCityRules.matcher(value).matches()) {
                inputText.setError(null);
                newCityName = value;
                return true;
            } else {
                inputText.setError(getString(R.string.wrong_name_message));
                return false;
            }
        } else return true;
    }

    private void setChangeFromNewCity (City city) {
        options.putString(WeatherActivity.cityKey, city.getName());
        options.putString(WeatherActivity.temperatureKey, city.getTemperature());
        options.putInt(WeatherActivity.weatherImageKey, city.getWeatherImage());
        options.putString(WeatherActivity.humidityKey, city.getHumidity());
        options.putString(WeatherActivity.uvIndexKey, city.getUvIndex());
        options.putString(WeatherActivity.chanceOfRainKey, city.getChanceOfRain());
        options.putString(WeatherActivity.pressureKey, city.getPressure());
        options.putString(WeatherActivity.windSpeedKey, city.getWindSpeed());
        options.putString(WeatherActivity.windDirectKey, city.getWindDirection());
        options.putString(WeatherActivity.sunriseKey, city.getSunrise());
        options.putString(WeatherActivity.sunsetKey, city.getSunset());
    }
}