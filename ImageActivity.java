package com.example.kouki.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ImageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView image = (ImageView)findViewById(R.id.hatsune_image);
        //image.setImageResource(R.drawable.eibesu);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.eibesu);
        final Bitmap copyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);



        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(50);

        final Canvas offScreen =  new Canvas(copyBitmap);
        offScreen.drawText("函館市",offScreen.getWidth()-300,offScreen.getHeight()-100,paint);

        image.setImageBitmap(copyBitmap);

        Button save_button = (Button)findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String saveDirectoryPath = Environment.getExternalStorageDirectory().getPath() + "/picture2";
                    String saveFilePath = saveDirectoryPath + "/sample.png";

                    File directoryFile = new File(saveDirectoryPath);

                    if ( !directoryFile.exists()) {
                        if(!directoryFile.mkdir()) Log.d ("Error" , "Make Directory Error");
                    }

                    FileOutputStream fos = new FileOutputStream(new File(saveFilePath));
                    copyBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);


                    //アンドロイドに画像を登録
                    registAndroidDataBase(saveFilePath);

                    fos = null;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
           }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
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

    private void registAndroidDataBase(String path) {
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = ImageActivity.this.getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE , "image/jpeg");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
    }
}
