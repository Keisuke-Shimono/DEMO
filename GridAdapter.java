package com.example.kouki.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Kouki on 2015/06/01.
 */
public class GridAdapter extends ArrayAdapter<Bitmap> {

    private static int GRID_STYLE_ID = R.layout.grid_style;
    LayoutInflater inflater;

    public GridAdapter(Context context, List<Bitmap> objects) {
        super(context, GRID_STYLE_ID , objects);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(GRID_STYLE_ID,null);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.grid_image);
        imageView.setImageBitmap(getItem(position));
        return convertView;
    }
}
