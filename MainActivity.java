package com.example.kouki.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity  {

    private TextView view;
    private EditText edit;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (TextView)findViewById(R.id.text_view1);
        edit = (EditText)findViewById(R.id.edit_text1);

        Button button                = (Button)findViewById(R.id.button1);
        Button button_to_motion      = (Button)findViewById(R.id.button_to_motion);
        Button button_to_gps         = (Button)findViewById(R.id.button_to_gps);
        Button button_to_list        = (Button)findViewById(R.id.button_to_list);
        Button button_to_camera      = (Button)findViewById(R.id.button_to_camera);
        Button button_to_image       = (Button)findViewById(R.id.button_to_image);
        Button button_to_orientation = (Button)findViewById(R.id.button_to_orientation);
        Button button_to_service     = (Button)findViewById(R.id.button_to_service);
        Button button_to_grid        = (Button)findViewById(R.id.button_to_grid);
        handler = new Handler();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setText(edit.getText());
                    }
                });
            };
        });

        button_to_motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.MotionActivity");

                startActivity(intent);
            }
        });

        button_to_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.GPSActivity");

                startActivity(intent);
            }
        });

        button_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.ListviewActivity");

                startActivity(intent);
            }
        });

        button_to_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.CameraActivity");

                startActivity(intent);
            }
        });

        button_to_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.ImageActivity");

                startActivity(intent);
            }
        });

        button_to_orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.OrientationActivity");

                startActivity(intent);
            }
        });

        button_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.ServiceActivity");

                startActivity(intent);
            }
        });

        button_to_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.kouki.myapplication","com.example.kouki.myapplication.GridActivity");

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
