package com.example.kouki.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class GridActivity extends Activity implements AdapterView.OnItemClickListener {

    //状態遷移時に渡すパス
    private ArrayList<String> pathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        Log.d("Debug", "OK,GridActivity.");

        GridView gridView = (GridView)findViewById(R.id.grid_view);

        gridView.setNumColumns(3);

/*
        Uri sdUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String uriStr = sdUri.getPath() + "/picture2";
        Uri uri = Uri.parse(uriStr);
*/

        //Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/picture2");


        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String imagePath = Environment.getExternalStorageDirectory().getPath()+"/picture2";



        Log.d("Debug","Uri : " + uri.getPath());

        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        ContentResolver contentResolver = getContentResolver();
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();
        pathList        = new ArrayList<String>();

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount() ; i++) {

            //このアプリで作ったディレクトリに入っていればサムネイルとして追加
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if(path.startsWith(imagePath)) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(contentResolver,id,
                        MediaStore.Images.Thumbnails.MINI_KIND,null);
                bitmapArrayList.add(bmp);
                pathList.add(path);
            }

            cursor.moveToNext();
        }

        GridAdapter adapter = new GridAdapter(getApplicationContext(),bitmapArrayList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid, menu);
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ImagePreviewActivity.class);
        intent.putExtra("item_id",id);
        intent.putExtra("item_path",pathList);

        startActivity(intent);
    }
}
