package com.example.myweatherapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myweatherapp.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private ImageView saveImage;

    private RadioButton tempValueC;
    private RadioButton tempValueF;
    private RadioButton windSpeedValMS;
    private RadioButton windSpeedValKmH;
    private RadioButton pressureValMm;
    private RadioButton pressureValGPa;
    private RadioButton nightTheme;
    private RadioButton dayTheme;
    private RadioGroup theme;

    public static String tempValueCKey = "tempValueCKey";
    public static String tempValueFKey = "tempValueFKey";
    public static String windSpeedValMSKey = "windSpeedValMSKey";
    public static String windSpeedValKmHKey = "windSpeedValKmHKey";
    public static String pressureValMmKey = "pressureValMmKey";
    public static String pressureValGPaKey = "pressureValGPaKey";
    public static String nightThemeKey = "nightThemeKey";
    public static String dayThemeKey = "dayThemeKey";

    private Bundle settings = new Bundle();
    private Bundle options = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_settings);
        findViews();
        setOnClickBehaviourToSave();
        setSettingsFromMainDisplay();
        onChangeThemeRadioButton();
    }

    private void findViews() {
        saveImage = findViewById(R.id.homeImage);
        tempValueC = findViewById(R.id.tempValueC);
        tempValueF = findViewById(R.id.tempValueF);
        windSpeedValMS = findViewById(R.id.windSpeedValMS);
        windSpeedValKmH = findViewById(R.id.windSpeedValKmH);
        pressureValMm = findViewById(R.id.pressureValMm);
        pressureValGPa = findViewById(R.id.pressureValGPa);
        nightTheme = findViewById(R.id.nightTheme);
        dayTheme = findViewById(R.id.dayTheme);
        theme = findViewById(R.id.theme);

        settings = getIntent().getBundleExtra(WeatherActivity.settingsDataKey);
        options = getIntent().getBundleExtra(WeatherActivity.optionsDataKey);
    }

    private void setOnClickBehaviourToSave() {
        saveImage.setOnClickListener((v) -> {
            checkedAllRadioButtons();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Intent intent = new Intent(this, WeatherActivity.class);
                intent.putExtra(WeatherActivity.settingsDataKey, settings);
                intent.putExtra(WeatherActivity.optionsDataKey, options);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(WeatherActivity.settingsDataKey, settings);
                intent.putExtra(WeatherActivity.optionsDataKey, options);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setSettingsFromMainDisplay() {

        tempValueC.setChecked(settings.getBoolean(tempValueCKey));
        tempValueF.setChecked(settings.getBoolean(tempValueFKey));
        pressureValMm.setChecked(settings.getBoolean(pressureValMmKey));
        pressureValGPa.setChecked(settings.getBoolean(pressureValGPaKey));
        windSpeedValMS.setChecked(settings.getBoolean(windSpeedValMSKey));
        windSpeedValKmH.setChecked(settings.getBoolean(windSpeedValKmHKey));
        nightTheme.setChecked(settings.getBoolean(nightThemeKey));
        dayTheme.setChecked(settings.getBoolean(dayThemeKey));
    }

    private void checkedAllRadioButtons() {
        fillSettings(tempValueC, tempValueCKey);
        fillSettings(tempValueF, tempValueFKey);
        fillSettings(pressureValMm, pressureValMmKey);
        fillSettings(pressureValGPa, pressureValGPaKey);
        fillSettings(windSpeedValMS, windSpeedValMSKey);
        fillSettings(windSpeedValKmH, windSpeedValKmHKey);
        fillSettings(nightTheme, nightThemeKey);
        fillSettings(dayTheme, dayThemeKey);
    }

    private void fillSettings(RadioButton radioButton, String key) {
        if (radioButton.isChecked()) {
            settings.putBoolean(key, true);
        } else {
            settings.putBoolean(key, false);
        }
    }

    private void onChangeThemeRadioButton() {
        theme.setOnCheckedChangeListener((radioGroup, id) -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            switch (id) {
                case R.id.nightTheme:
                    nightTheme.setChecked(true);
                    settings.putBoolean(nightThemeKey, true);
                    settings.putBoolean(dayThemeKey, false);
                    intent.putExtra(WeatherActivity.optionsDataKey, options);
                    intent.putExtra(WeatherActivity.settingsDataKey, settings);
                    finish();
                    startActivity(intent);
                    break;
                case R.id.dayTheme:
                    dayTheme.setChecked(true);
                    settings.putBoolean(nightThemeKey, false);
                    settings.putBoolean(dayThemeKey, true);
                    intent.putExtra(WeatherActivity.optionsDataKey, options);
                    intent.putExtra(WeatherActivity.settingsDataKey, settings);
                    finish();
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });
    }

    private void checkTheme() {
        if (Objects.requireNonNull(getIntent().getBundleExtra(WeatherActivity.settingsDataKey)).getBoolean(nightThemeKey)) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

}