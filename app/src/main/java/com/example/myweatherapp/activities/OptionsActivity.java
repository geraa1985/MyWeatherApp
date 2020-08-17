package com.example.myweatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myweatherapp.R;

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

    private Bundle options = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        findViews();
        setOnClickBehaviourToSave();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkAllOfOptions();
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(WeatherActivity.optionsDataKey, options);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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
    }

    private void setOnClickBehaviourToSave() {
        saveImage.setOnClickListener((v) -> {
            checkAllOfOptions();
            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra(WeatherActivity.optionsDataKey, options);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
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
}