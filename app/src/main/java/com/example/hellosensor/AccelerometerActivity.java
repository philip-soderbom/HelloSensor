package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import android.os.Bundle;

import java.text.DecimalFormat;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xValueText, yValueText, zValueText, orientation, flatText;
    ConstraintLayout bg;
    private Sensor accelerometer;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        // create SensorManager
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        // accelerometer Sensor
        accelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // register Sensor Listener
        SM.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign variables to layout components
        xValueText = findViewById(R.id.xValue);
        yValueText = findViewById(R.id.yValue);
        zValueText = findViewById(R.id.zValue);
        flatText = findViewById(R.id.flat_text);
        bg = findViewById(R.id.accelerometer_bg);
        orientation = findViewById(R.id.orientationText);

    }


    // methods from "implements SensorEventListener"
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // sensor data
        double xValue, yValue, zValue;
        xValue = sensorEvent.values[0];
        yValue = sensorEvent.values[1];
        zValue = sensorEvent.values[2];


        // Formatting sensor decimals
        DecimalFormat df = new DecimalFormat("###.##");
        String x = df.format(xValue);
        String y = df.format(yValue);
        String z = df.format(zValue);

        // updating TextViews using resource string with placeholders
        xValueText.setText(getString(R.string.accelerometer_value, "X", x));
        yValueText.setText(getString(R.string.accelerometer_value, "Y", y));
        zValueText.setText(getString(R.string.accelerometer_value, "Z", z));


        detectOrientation(xValue, yValue, zValue);
        detectFlat(zValue);
    }

    private void detectOrientation(double x, double y, double z) {
        int limit = 5;
        if (y > limit) setOrientation("Portrait (Up-Right)");
        else if (y < -limit) setOrientation("Upside-Down");
        else if (x > limit) setOrientation("Left");
        else if (x < -limit) setOrientation("Right");
        else if (z > limit) setOrientation("Facing Up");
        else if (z < -limit) setOrientation("Facing Down");
        else setOrientation("Not Detected");
    }

    private void setOrientation(String orientationString) {
        orientation.setText(getString(R.string.orientation_value, "Orientation", orientationString));
    }

    private void detectFlat(Double zValue) {
        if (zValue > 9.8 && zValue < 9.82) {
            flatText.setText(getString(R.string.flat_text, "Flat"));
            bg.setBackgroundResource(R.color.green);
        } else {
            flatText.setText("");
            bg.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // not in use
    }
}