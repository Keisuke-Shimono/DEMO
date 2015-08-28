package com.example.kouki.myapplication;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Kouki on 2015/05/24.
 */
public class APIManager extends AsyncTask<Location, Void , String> {

    private static final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private String content;
    private CountDownLatch latch;
    private Handler handler;
    private TextView locationName;

    public APIManager(CountDownLatch latch , Handler handler, TextView name) {
        content = "Still Not Get";
        this.latch = latch;
        this.handler = handler;
        this.locationName = name;
    }

    public String getContent() {
        return content;
    }

    @Override
    protected String doInBackground(Location... locations) {
        return getHttpContent(locations[0]);
    }

    @Override
    public void onPostExecute(String result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                locationName.setText(content);
            }
        });
    }

    private String getHttpContent(Location location) {
        String fullURL = API_URL + "?latlng="
                + Double.toString(location.getLatitude()) + ","
                + Double.toString(location.getLongitude()) + "&sensor=false";

        Log.d("Debug", fullURL);

        try {
            URL url = new URL(fullURL);
            URLConnection urlConnection;
            urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Accept-Language", "ja");

            InputStream input = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader( new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();

            String line;

            while((line = br.readLine()) != null ) {
                sb.append(line);
            }

            br.close();

            // ここをうまくパースできるようにすることですな
            JSONObject json = new JSONObject(sb.toString());
            JSONArray result = json.getJSONArray("results");
            JSONArray components = result.getJSONObject(0).getJSONArray("address_components");
            for (int i=0 ; i < components.length() ; i++) {
                JSONObject component = components.getJSONObject(i);
                JSONArray types = component.getJSONArray("types");
                for(int j=0 ; j<types.length() ; j++) {
                    String local = types.getString(j);
                    if( local.equals("locality")) {
                        content = component.getString("long_name");
                        Log.d("Debug" , content);
                        return content;
                    }
                }
            }

            return sb.toString();

        } catch(Exception e) {
            e.printStackTrace();
            return "Not Data.";
        } finally {
            latch.countDown();
        }
    }




}
