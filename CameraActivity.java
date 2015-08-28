package com.example.kouki.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class CameraActivity extends Activity {

    private Camera        camera        = null;
    private CameraPreview cameraPreview = null;

    private boolean isDoubleTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //カメラインスタンスの取得
        try {
            camera = Camera.open();
        } catch(Exception e) {
            // Nothing
        }




        //カメラプレビュー
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        cameraPreview = new CameraPreview(this , camera);
        preview.addView(cameraPreview);

        //タッチでオートフォーカス
        cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                camera.autoFocus(null);

                return true;
            }


        });

        //ボタンで撮影
        Button takePictureButton = (Button)findViewById(R.id.takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Touch","Button Touched");
                if(isDoubleTouch) return;

                isDoubleTouch = true;
                camera.takePicture(null,null,takePictureCallback);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(camera != null) {
            camera.release();
            camera = null; //解放してほしい
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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

    private Camera.PictureCallback takePictureCallback = new Camera.PictureCallback() {
        //撮影後に呼び出される
        public void onPictureTaken(byte[] data , Camera camera) {
            if (data == null) return;

            // 撮影した画像の保存
            savePicture(data);

            //撮影するとプレビューが止まるので再開
            camera.startPreview();

            //再びタッチすると撮影できるようにする
            isDoubleTouch = false;
        }

        private void savePicture(byte[] data) {
            String saveDirectory = Environment.getExternalStorageDirectory().getPath() + "/picture";

            File file = new File(saveDirectory);

            //ディレクトリがなければ作成
            if ( !file.exists()) {
                if(!file.mkdir()) Log.d ("Error" , "Make Directory Error");
            }

            //時刻からファイル名とパスを作成
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String imgPath = saveDirectory + "/" + simpleDate.format(calendar.getTime()) + ".jpg";

            //保存
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(imgPath , true);
                fileOutputStream.write(data);
                fileOutputStream.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }

            //アンドロイドに画像を登録
            registAndroidDataBase(imgPath);

            fileOutputStream = null;
        }
    };



    private void registAndroidDataBase(String path) {
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = CameraActivity.this.getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE , "image/jpeg");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
    }

}
