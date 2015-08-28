package com.example.kouki.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MotionActivity extends ActionBarActivity implements SensorEventListener{

    private TextView motion_x;
    private TextView motion_y;
    private TextView motion_z;
    
    private SensorManager sensorManager;
    private Sensor        sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        motion_x = (TextView) findViewById(R.id.motion_x);
        motion_y = (TextView) findViewById(R.id.motion_y);
        motion_z = (TextView) findViewById(R.id.motion_z);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //加速度センサを取得
        Sensor acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //センサを有効化
        sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_NORMAL); //遅延1秒程度
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor , int accuracy) {
        //精度の変化時
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        //値の変化時
        motion_x.setText(Double.toString(event.values[0]));
        motion_y.setText(Double.toString(event.values[1]));
        motion_z.setText(Double.toString(event.values[2]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_motion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
