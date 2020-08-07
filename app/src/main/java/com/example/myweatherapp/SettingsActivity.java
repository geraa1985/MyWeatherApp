package com.example.myweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private ImageView homeImage;

    RadioButton tempValueC;
    RadioButton tempValueF;
    RadioButton windSpeedValMS;
    RadioButton windSpeedValKmH;
    RadioButton pressureValMm;
    RadioButton pressureValGPa;
    RadioButton nightTheme;
    RadioButton dayTheme;

    private Bundle settings = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
        setOnClickBehaviourToHome();
        setSettingsFromMainDisplay();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        findViews();
        setSettingsFromMainDisplay();
    }

    private void findViews() {
        homeImage = findViewById(R.id.homeImage);
        tempValueC = findViewById(R.id.tempValueC);
        tempValueF = findViewById(R.id.tempValueF);
        windSpeedValMS = findViewById(R.id.windSpeedValMS);
        windSpeedValKmH = findViewById(R.id.windSpeedValKmH);
        pressureValMm = findViewById(R.id.pressureValMm);
        pressureValGPa = findViewById(R.id.pressureValGPa);
        nightTheme = findViewById(R.id.nightTheme);
        dayTheme = findViewById(R.id.dayTheme);

        settings = getIntent().getBundleExtra(MainActivity.settingsDataKey);
    }

    private void setOnClickBehaviourToHome() {
        homeImage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            checkAllSettings();
            intent.putExtra(MainActivity.settingsDataKey, settings);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private void setSettingsFromMainDisplay() {
        tempValueC.setChecked(Objects.requireNonNull(settings.getString(MainActivity.temperatureKey)).contains(getString(R.string.degreesC)));
        tempValueF.setChecked(Objects.requireNonNull(settings.getString(MainActivity.temperatureKey)).contains(getString(R.string.degreesF)));
        pressureValMm.setChecked(Objects.requireNonNull(settings.getString(MainActivity.pressureKey)).contains(getString(R.string.mm)));
        pressureValGPa.setChecked(Objects.requireNonNull(settings.getString(MainActivity.pressureKey)).contains(getString(R.string.gpa)));
        windSpeedValMS.setChecked(Objects.requireNonNull(settings.getString(MainActivity.windSpeedKey)).contains(getString(R.string.ms)));
        windSpeedValKmH.setChecked(Objects.requireNonNull(settings.getString(MainActivity.windSpeedKey)).contains(getString(R.string.kmh)));
    }

    private void checkAllSettings() {
        onCheckedRadioButton(tempValueC, MainActivity.temperatureKey, getString(R.string.degreesC));
        onCheckedRadioButton(tempValueF, MainActivity.temperatureKey, getString(R.string.degreesF));
        onCheckedRadioButton(pressureValMm, MainActivity.pressureKey, getString(R.string.mm));
        onCheckedRadioButton(pressureValGPa, MainActivity.pressureKey, getString(R.string.gpa));
        onCheckedRadioButton(windSpeedValMS, MainActivity.windSpeedKey, getString(R.string.ms));
        onCheckedRadioButton(windSpeedValKmH, MainActivity.windSpeedKey, getString(R.string.kmh));
    }

    private void onCheckedRadioButton(RadioButton radioButton, String key, String units) {
        String value = settings.getString(key);
        boolean isUnits = Objects.requireNonNull(value).contains(units);

        if (radioButton.isChecked() && !isUnits) {
            int newDigitValue;
            if (radioButton.getId() == R.id.tempValueC) {
                newDigitValue = fToC(digitalValue(value));
            } else if (radioButton.getId() == R.id.tempValueF) {
                newDigitValue = cToF(digitalValue(value));
            } else if (radioButton.getId() == R.id.pressureValMm) {
                newDigitValue = gPaToMm(digitalValue(value));
            } else if (radioButton.getId() == R.id.pressureValGPa) {
                newDigitValue = mmToGPa(digitalValue(value));
            } else if (radioButton.getId() == R.id.windSpeedValMS) {
                newDigitValue = kmhToMs(digitalValue(value));
            } else if (radioButton.getId() == R.id.windSpeedValKmH) {
                newDigitValue = msToKmh(digitalValue(value));
            } else {
                newDigitValue = 0;
            }
            settings.putString(key, newValue(value, newDigitValue, units));
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
}