package com.example.myweatherapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myweatherapp.R;
import com.example.myweatherapp.fragments.WeatherFragment;
import com.example.myweatherapp.interfaces.OnFragmentInteractionListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTheme();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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

    @Override
    public void onFragmentInteraction(Bundle bundle) {
        WeatherFragment fragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.weatherFrame);
        if (fragment != null && fragment.isInLayout()) {
            fragment.getOptionsFromContext(bundle);
        }
    }
}