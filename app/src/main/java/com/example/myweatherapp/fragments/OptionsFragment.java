package com.example.myweatherapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.activities.SettingsActivity;
import com.example.myweatherapp.activities.WeatherActivity;
import com.example.myweatherapp.adapters.CitiesListRVAdapter;
import com.example.myweatherapp.inputdata.City;
import com.example.myweatherapp.interfaces.IRVonCityClick;
import com.example.myweatherapp.interfaces.OnFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class OptionsFragment extends Fragment implements IRVonCityClick {

    TextView cityName;
    TextView temperature;
    ImageView weatherImage;

    RecyclerView cityRV;
    CitiesListRVAdapter adapter;

    public static String[] getCities() {
        return cities;
    }

    private static String[] cities;

    private Bundle options = new Bundle();
    private Bundle settings = new Bundle();

    private OnFragmentInteractionListener mListener;

    Handler handler = new Handler(Looper.getMainLooper());

       @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        findViews();
        setInfoFromBundle();
        setupAdapter();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.optionsDataKey) == null) {
                new Thread(()->{
                    City firstCity = new City(cities[0], getContext());
                    onCityClick(firstCity);
                }).start();
            }
        }
    }

    private void findViews() {
        cityName = Objects.requireNonNull(getActivity()).findViewById(R.id.cityName);
        temperature = Objects.requireNonNull(getActivity()).findViewById(R.id.temperatureReading);
        weatherImage = Objects.requireNonNull(getActivity()).findViewById(R.id.weatherImage);

        cities = getResources().getStringArray(R.array.cities);
        cityRV = Objects.requireNonNull(getActivity()).findViewById(R.id.cities_rv);
    }

    private void setInfoFromBundle() {
        if (Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.optionsDataKey) != null) {
            options = Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.optionsDataKey);
        }
        if (Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.settingsDataKey) != null) {
            settings = Objects.requireNonNull(getActivity()).getIntent().getBundleExtra(WeatherActivity.settingsDataKey);
        }
    }

    private void setupAdapter() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        cityRV.setLayoutManager(lm);
        adapter = new CitiesListRVAdapter(City.getCitiesList(), this);
        cityRV.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCityClick(City city) {
        handler.post(() -> {
            options.putString(WeatherActivity.cityKey, city.getName());
            options.putString(WeatherActivity.temperatureKey, city.getTemperature());
            options.putString(WeatherActivity.weatherImageKey, city.getWeatherImage());
            options.putString(WeatherActivity.feelsLikeTempKey, city.getFeelsLikeTemp());
            options.putString(WeatherActivity.humidityKey, city.getHumidity());
            options.putString(WeatherActivity.visibilityKey, city.getVisibility());
            options.putString(WeatherActivity.pressureKey, city.getPressure());
            options.putString(WeatherActivity.windSpeedKey, city.getWindSpeed());
            options.putString(WeatherActivity.windDirectKey, city.getWindDirection());
            options.putString(WeatherActivity.sunriseKey, city.getSunrise());
            options.putString(WeatherActivity.sunsetKey, city.getSunset());
            mListener.onFragmentInteraction(options);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                cityName.setText(options.getString(WeatherActivity.cityKey));
                temperature.setText(options.getString(WeatherActivity.temperatureKey));
                Picasso.get().load(options.getString(WeatherActivity.weatherImageKey)).resizeDimen(R.dimen.width_weatherImage, R.dimen.height_weatherImage).into(weatherImage);
                setAllSettings();
            } else {
                Intent intent = new Intent(Objects.requireNonNull(getActivity()), WeatherActivity.class);
                intent.putExtra(WeatherActivity.optionsDataKey, options);
                intent.putExtra(WeatherActivity.settingsDataKey, settings);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void setAllSettings() {
        setSettingValue(temperature, SettingsActivity.tempValueCKey, getString(R.string.degreesC));
        setSettingValue(temperature, SettingsActivity.tempValueFKey, getString(R.string.degreesF));
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

}