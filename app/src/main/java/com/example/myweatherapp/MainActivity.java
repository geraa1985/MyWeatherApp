package com.example.myweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private TextView temperatureTextView;
    private final String temperatureDataKey = "temperatureDataKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setOnClickBehaviourToTmp();

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected  void onSaveInstanceState(@NonNull Bundle outState){
        outState.putString(temperatureDataKey, temperatureTextView.getText().toString());
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected  void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        temperatureTextView.setText(savedInstanceState.getString(temperatureDataKey));
        Toast.makeText(this, "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestoreInstanceState");
    }

    private void findViews() {
        temperatureTextView = findViewById(R.id.temperatureReading);
    }

    private void setOnClickBehaviourToTmp() {
        temperatureTextView.setOnClickListener((view) -> {
            StringBuilder sb = new StringBuilder(temperatureTextView.getText().toString());
            StringBuilder digitValue = new StringBuilder();
            for (int i = 0; i < sb.length(); i++) {
                if (Character.isDigit(sb.charAt(i))){
                    digitValue.append(sb.charAt(i));
                }
            }
            int newDigitValue = Integer.parseInt(digitValue.toString()) + 3;

            StringBuilder newValue = new StringBuilder();
            newValue.append(sb.charAt(0));
            newValue.append(newDigitValue);
            for (int i = 1; i < sb.length(); i++) {
                if (!Character.isDigit(sb.charAt(i))){
                    newValue.append(sb.charAt(i));
                }
            }

            temperatureTextView.setText(newValue.toString());
        });
    }
}