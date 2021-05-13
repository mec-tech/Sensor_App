package com.example.sensor_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
//import android.view.View;
import android.view.View;

//////////////

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/////////////////

public class MainActivity extends AppCompatActivity {

    CustomDrawableView mCustomDrawableView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static int x_point;
    public static int y_point;

    TextView x_output, y_output, z_output;
    //Accelerometer Sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean isAccelerometerSensorAvailable, itIsNotFirst = false;
    private float lastX, xdifference;
    private float shake = 10f;


    private Button button;
    int countL = 0;
    int countR = 0;
    boolean flag = false;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    //image
    //private ImageView image;



    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

                //x_point = (int) Math.pow(sensorEvent.values[0], 3);
                //y_point = (int) Math.pow(sensorEvent.values[1], 1);

            if(itIsNotFirst){
                xdifference = Math.abs(lastX - x);
                if ( xdifference > shake) {

                    if (x >= 9 ){
                          Toast toast= Toast.makeText(getApplicationContext(), "Shaken to 9", Toast.LENGTH_SHORT);
                          toast.show();
                        countL++;
                    }
                    if(x <= -9 ){
                          Toast toas = Toast.makeText(getApplicationContext(), "Shaken to -9", Toast.LENGTH_SHORT);
                          toas.show();
                        countR++;
                    }
                    if (countL == 50 || countR == 50){
                        Toast ast= Toast.makeText(getApplicationContext(), "Almost done shaking", Toast.LENGTH_SHORT);
                        ast.show();
                    }
                    if (countL >=100&& countR >=100){
                          Toast toa = Toast.makeText(getApplicationContext(), "You Shook the soda!", Toast.LENGTH_LONG);
                          toa.show();
                          openActivity2();
                    }
                }
            }

            lastX = x;
            x_output.setText("X = " + (int) x);
         // y_output.setText("Y = " + (int) y);
         // z_output.setText("Z = " + (int) z);
            itIsNotFirst = true;
            z_output.setText("CountL = " + (int) countL);
            y_output.setText("CountR = " + (int) countR);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomDrawableView = new CustomDrawableView(this);
        //setContentView(mCustomDrawableView);

        x_output = findViewById(R.id.x_output);
        y_output = findViewById(R.id.y_output);
        z_output = findViewById(R.id.z_output);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //image
        //image = (ImageView) findViewById(R.id.image);

        //Button
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openActivity2();
            }
        });



    }
     protected void onResume() {
         super.onResume();
         mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

     }
     protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);

     }
     public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
     }
      public class CustomDrawableView extends View
    {
        static final int width = 50;
        static final int height = 50;

        public CustomDrawableView(Context context)
        {
            super(context);

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xff74AC23);
            mDrawable.setBounds(x_point, y_point, x_point + width, y_point + height);
        }

        protected void onDraw(Canvas canvas)
        {
            RectF oval = new RectF(MainActivity.x_point, MainActivity.y_point, MainActivity.x_point + width, MainActivity.y_point
                    + height); // set bounds of rectangle
            Paint p = new Paint(); // set some paint options
            p.setColor(Color.BLUE);
            canvas.drawOval(oval, p);
            invalidate();
        }
    }
}