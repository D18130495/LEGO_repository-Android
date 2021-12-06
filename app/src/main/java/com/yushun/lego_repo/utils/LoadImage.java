package com.yushun.lego_repo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imv;
    private String path;

    public LoadImage(ImageView imv) {
        this.imv = imv;
        this.path = imv.getTag().toString();
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
        bitmap = getBitmapFromURL(this.path);
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (!imv.getTag().toString().equals(path)) {
            return;
        }

        if(result != null && imv != null){
            imv.setVisibility(View.VISIBLE);
            imv.setImageBitmap(result);
        }else{
            imv.setVisibility(View.GONE);
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }
}
