package com.example.kouki.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;


public class OrientationActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private TextView azimuthLabel;
    private TextView pitchLabel;
    private TextView rollLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        azimuthLabel = (TextView)findViewById(R.id.azimuth);
        pitchLabel   = (TextView)findViewById(R.id.pitch);
        rollLabel    = (TextView)findViewById(R.id.roll);


        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected  void onResume() {
        super.onResume();
        List<Sensor> sensorList =
                sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if(sensorList.size() > 0) {
            Sensor sensor = sensorList.get(0);
            sensorManager.registerListener(this , sensor , SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ORIENTATION) return;

        azimuthLabel.setText(getString(R.string.azimuth) +
                Double.toString(event.values[0]));

        pitchLabel.setText  (getString(R.string.pitch)   +
                Double.toString(event.values[1]));

        rollLabel.setText   (getString(R.string.roll)    +
                Double.toString(event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_orientation, menu);
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
