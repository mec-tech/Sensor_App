package com.example.sensor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    TextView  light, max, newValues;
    //Light Sensor
    private SensorManager mSensorManager;
    private Sensor LightSensor;
    private View root;
    private float maxValue;
    private SensorEventListener lightEventListener;
    //private Button button;
    boolean flag = false;
    long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Light
        light = findViewById(R.id.light);
        root = findViewById(R.id.root);
        max = findViewById(R.id.max);
        newValues = findViewById(R.id.newValue);
        LightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (LightSensor == null){
            Toast.makeText(this, "This device has no light sensor :(", Toast.LENGTH_SHORT).show();
            finish();
        }
        maxValue = LightSensor.getMaximumRange();
        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent){
                float value = sensorEvent.values[0];

                //this will change the background color
                getSupportActionBar().setTitle("Luminosity: " + value + "lx");
                int newValue = (int) (255f * value  / 100);
                if(newValue >= 1000){
                    newValue = 255;
                    flag = true;
                }
                //newvalue == 255 for white but starts at 12 lx 88lx for value of 10300*255/30000 we want my range to be 0-1000lx

                newValues.setText("new: " + newValue + "lx");
                root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));

                if(flag == true) {
                    root.setBackgroundColor(Color.rgb(255, 255, 255));
                    if(value <= 0) {
                        time = System.currentTimeMillis();
                        if(time >= 900000000) {
                            max.setText("Time: " + time + "ms");
                            flag = false;
                            openActivity3();
                        }
                    }

                }




            }
            @Override
            public  void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

    }
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(lightEventListener, LightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(lightEventListener);
    }
    public void openActivity3(){
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
}