package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.hellosensor.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Call this method when Send-button is clicked **/
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void openCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);


        startActivity(intent);
    }

    public void openAccelerometer(View view) {
        Intent intent = new Intent(this, AccelerometerActivity.class);


        startActivity(intent);
    }


}