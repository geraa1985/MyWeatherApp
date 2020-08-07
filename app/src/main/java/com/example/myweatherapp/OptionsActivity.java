package com.example.myweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {
    private ImageView homeImage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        findViews();
        setOnClickBehaviourToHome();
        setOptionsFromMainDisplay();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        findViews();
        setOptionsFromMainDisplay();
    }

    private void findViews() {
        homeImage = findViewById(R.id.homeImage);
        enterCity = findViewById(R.id.enterCity);
        humidity = findViewById(R.id.checkBoxHumidity);
        uvIndex = findViewById(R.id.checkBoxUVIndex);
        chanceOfRain = findViewById(R.id.checkBoxChanceOfRain);
        pressure = findViewById(R.id.checkBoxPressure);
        windSpeed = findViewById(R.id.checkBoxWindSpeed);
        windDirect = findViewById(R.id.checkBoxWindDirection);
        sunrise = findViewById(R.id.checkBoxSunrise);
        sunset = findViewById(R.id.checkBoxSunset);

        options = getIntent().getBundleExtra(MainActivity.optionsDataKey);
    }

    private void setOnClickBehaviourToHome() {
        homeImage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            checkAllOfOptions();
            intent.putExtra(MainActivity.optionsDataKey, options);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void checkAllOfOptions() {
        enteredCity();
        CheckboxClicked(humidity);
        CheckboxClicked(uvIndex);
        CheckboxClicked(chanceOfRain);
        CheckboxClicked(pressure);
        CheckboxClicked(windSpeed);
        CheckboxClicked(windDirect);
        CheckboxClicked(sunrise);
        CheckboxClicked(sunset);
    }

    private void enteredCity() {
        if (!enterCity.getText().toString().equals("")) {
            options.putString(MainActivity.cityKey, enterCity.getText().toString());
        }
    }

    private void CheckboxClicked(CheckBox box) {
        if (box.isChecked()) {
            options.putBoolean(box.getText().toString(), true);
        } else {
            options.putBoolean(box.getText().toString(), false);
        }
    }

    private void setOptionsFromMainDisplay() {
        humidity.setChecked(options.getBoolean(humidity.getText().toString(), true));
        uvIndex.setChecked(options.getBoolean(uvIndex.getText().toString(), true));
        chanceOfRain.setChecked(options.getBoolean(chanceOfRain.getText().toString(), true));
        pressure.setChecked(options.getBoolean(pressure.getText().toString(), true));
        windSpeed.setChecked(options.getBoolean(windSpeed.getText().toString(), true));
        windDirect.setChecked(options.getBoolean(windDirect.getText().toString(), true));
        sunrise.setChecked(options.getBoolean(sunrise.getText().toString(), true));
        sunset.setChecked(options.getBoolean(sunset.getText().toString(), true));
    }
}