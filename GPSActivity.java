package com.example.kouki.myapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;
import java.util.logging.LogRecord;


public class GPSActivity extends ActionBarActivity  implements LocationListener {
    private LocationManager locationManager; // 位置情報管理.
    private TextView        latitudeLabel;
    private TextView        longitudeLabel;
    private TextView        locationName;
    private Location        location;
    private boolean         GPSEnabled = false;

    Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        latitudeLabel = (TextView)findViewById(R.id.latitude_label);
        longitudeLabel = (TextView)findViewById(R.id.longitude_label);
        locationName = (TextView)findViewById(R.id.location_name);

        Button move_to_map          = (Button)findViewById(R.id.move_to_map);
        Button move_to_simple_map   = (Button)findViewById(R.id.move_to_simple_map);

        move_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //位置情報を取得していなければ何もしない
                if (!GPSEnabled) {
                    Log.d("gps_activity", "GPS情報をまだ取得していません");
                    return;
                }

                //緯度・経度情報を付加した状態遷移（明示的インテント）
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.MapActivity");

                intent.putExtra("latitude",     location.getLatitude() );
                intent.putExtra("longitude",    location.getLongitude() );


                startActivity(intent);
            }
        });

        move_to_simple_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //位置情報を取得していなければ何もしない
                if(!GPSEnabled) {
                    Log.d("gps_activity", "GPS情報をまだ取得していません");
                    return;
                }

                //暗黙的インテントで地図アプリを起動してみる
                Uri uri = Uri.parse("geo:"+
                        Double.toString(location.getLatitude()) + "," +
                        Double.toString(location.getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW , uri);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
        GPSEnabled = true;
        this.location = location;
        locationName.setText("Changed!");

        latitudeLabel.setText( Double.toString(location.getLatitude()));
        longitudeLabel.setText(Double.toString(location.getLongitude()));


        CountDownLatch latch = new CountDownLatch(1);
        uiHandler = new Handler();
        APIManager manager = new APIManager(latch , uiHandler , locationName);

        manager.execute(location);

        /*
        try {
            Thread.sleep(1000);
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        locationManager.removeUpdates(this);
    }


    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        GPSEnabled = false;
    }

    @Override
    public void onStatusChanged(String provider,int status,Bundle extras){

    }

    @Override
    protected void onStart() {
        super.onStart();

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    }

    protected void onStop() {
        super.onStop();
        if( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) locationManager.removeUpdates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_g, menu);
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
