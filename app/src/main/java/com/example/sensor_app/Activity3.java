package com.example.sensor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {
    TextView x_output, y_output, z_output;
    //Accelerometer Sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private boolean  itIsNotFirst = false;
    private float lastZ, zdifference;
    private float flip = 3f;

    int countF = 0;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            //009 ready
            //090 flip
            if(itIsNotFirst){
                zdifference = Math.abs(lastZ - z);
                if ( zdifference > flip) {
                   // if (z >= 9 & y <= 2){
                     //   Toast toast= Toast.makeText(getApplicationContext(), "Flipped", Toast.LENGTH_SHORT);
                     //   toast.show();
                     //   countF++;
                    ///}
                    if(z <=0 & y >= 8 ){
                        Toast toas = Toast.makeText(getApplicationContext(), "Flipped", Toast.LENGTH_SHORT);
                        toas.show();
                        countF++;
                    }
                    if (countF >=10 ){
                        Toast toa = Toast.makeText(getApplicationContext(), "Done Flipping", Toast.LENGTH_LONG);
                        toa.show();

                    }
                }
            }

            lastZ = z;
            itIsNotFirst = true;
            x_output.setText("Flipped:  " + (int) (countF/2));
            y_output.setText("Y = " + (int) y);
            z_output.setText("Z = " + (int) z);

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        x_output = findViewById(R.id.x_output);
        y_output = findViewById(R.id.y_output);
        z_output = findViewById(R.id.z_output);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //image
        //image = (ImageView) findViewById(R.id.image);

    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);

    }
}