package com.example.kinga.maslo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView iv;
    private Sensor ruch;
    private SensorManager manager;
    private float currentDegree;
    private Sensor mSensor;
    private Sensor natezenie;
    private TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx=(TextView) findViewById(R.id.textView);
        iv = (ImageView) findViewById(R.id.imageView);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ruch = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);//zblizenie
        natezenie=manager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }
    protected void onResume() {
        super.onResume();
        //manager.registerListener(this, step, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this,ruch, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener( this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener( this, natezenie, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Sensor.TYPE_ACCELEROMETER == event.sensor.getType()) {/////////////
            float degree = Math.round(event.values[0]) * 10;
            //tx.setText(""+degree);

            
            RotateAnimation ra = new RotateAnimation(
                    currentDegree,
                    degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);
// how long the animation will take place
            ra.setDuration(210);
// set the animation after the end of the reservation status
            ra.setFillAfter(true);

// Start the animation
            iv.startAnimation(ra);
            currentDegree = degree;
        }
        else if(Sensor.TYPE_PROXIMITY == event.sensor.getType()){
            if (event.values[0] == 0) {
                iv.setImageResource(R.drawable.blisko); //obrazek jt jak sie zblizy reke
            } else {
                iv.setImageResource(R.drawable.daleko);// jt jak sie zabierze
            }
        }
        else if(Sensor.TYPE_LIGHT == event.sensor.getType()){
            tx.setText("Natężenie światła"+event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onStop() {

        super.onStop();
    }

}
