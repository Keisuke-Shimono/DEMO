package com.example.kouki.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;


public class ImagePreviewActivity extends Activity implements View.OnClickListener{

    int nowId;
    ArrayList<String> pathList;
    Handler handler;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        handler = new Handler();

        //遷移前からデータ取得
        Intent intent = getIntent();
        nowId = (int)intent.getLongExtra("item_id", 0);
        pathList = intent.getStringArrayListExtra("item_path");

        Log.d("Debug", Integer.toString(nowId));
        Log.d("Debug", pathList.get(nowId));


        //画像表示
        imageView = (ImageView)findViewById(R.id.image_preview);
        changeImage(nowId);
        //ボタン
        Button left_image_button  = (Button)findViewById(R.id.left_image_button);
        Button right_image_button = (Button)findViewById(R.id.right_image_button);

        left_image_button.setOnClickListener(this);
        right_image_button.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        nowId = (int)intent.getLongExtra("item_id", 0);
    }


    @Override
    public void onClick(View v) {
        //押下したボタンによって画像変更
        int viewId = v.getId();
        switch (viewId) {
            case R.id.right_image_button:
                nowId = (nowId + 1 ) % pathList.size();
                break;

            case R.id.left_image_button:
                nowId = (nowId + pathList.size() - 1) % pathList.size();
                break;
        }

        changeImage(nowId);
    }

    private void changeImage(int id) {
        File file = new File(pathList.get(id));
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_preview, menu);
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
