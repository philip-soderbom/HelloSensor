package com.example.hellosensor;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    ImageView img_compass;
    TextView txt_azimuth;
    TextView txt_north;
    int mAzimuth;
    private SensorManager SM;
    private Sensor mRotationV, mAccelerometer, mMagnetometer;
    float[] rMat = new float[9];
    float[] orientation = new float[9];

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean haveSensor = false, haveSensor2 = false;
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        img_compass = (ImageView) findViewById(R.id.img_compass);
        txt_azimuth = (TextView) findViewById(R.id.txt_azimuth);
        txt_north = (TextView) findViewById(R.id.txt_north);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.success_bell);
        Button playSoundBtn = (Button) findViewById(R.id.play_sound);

        playSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mp.start();
            }
        });

        start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values);
            mAzimuth = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0])+360)%360);

        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;

        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }

        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
            mAzimuth = (int) ((Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0])+360)%360);
        }

        mAzimuth = Math.round(mAzimuth);
        img_compass.setRotation(-mAzimuth);

        niceNorth(mAzimuth);

        String where = "NW";


        if (mAzimuth >= 350 || mAzimuth <= 10)
            where = "N";
        if (mAzimuth < 350 && mAzimuth > 280)
            where = "NW";
        if (mAzimuth <= 280 && mAzimuth > 260)
            where = "W";
        if (mAzimuth <= 260 && mAzimuth > 190)
            where = "SW";
        if (mAzimuth <= 190 && mAzimuth > 170)
            where = "S";
        if (mAzimuth <= 170 && mAzimuth > 100)
            where = "SE";
        if (mAzimuth <= 100 && mAzimuth > 80)
            where = "E";
        if (mAzimuth <= 80 && mAzimuth > 10)
            where = "NE";

        txt_azimuth.setText(mAzimuth + "° " + where);
        txt_north.setText(mAzimuth + "° " + where);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void start() {
        if (SM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if (SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null || SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
                noSensorAlert();
            }
            else {
                mAccelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mMagnetometer = SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

                haveSensor = SM.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
                haveSensor2 = SM.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
            }
        }
        else {
            mRotationV = SM.getDefaultSensor((Sensor.TYPE_ROTATION_VECTOR));
            haveSensor = SM.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void noSensorAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Compass.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        //alertDialog.show();
    }

    public void stop() {
        if(haveSensor && haveSensor2){
            SM.unregisterListener(this,mAccelerometer);
            SM.unregisterListener(this,mMagnetometer);
        }
        else{
            if(haveSensor)
                SM.unregisterListener(this,mRotationV);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    public void niceNorth(int azi){

        if(azi >= 350 || azi <= 10){
            txt_north.setVisibility(View.VISIBLE);
            txt_azimuth.setVisibility(View.INVISIBLE);

            final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.success_bell);
            mp2.start();

        }
        else {
            txt_north.setVisibility(View.INVISIBLE);
            txt_azimuth.setVisibility(View.VISIBLE);
        }
    }



}

