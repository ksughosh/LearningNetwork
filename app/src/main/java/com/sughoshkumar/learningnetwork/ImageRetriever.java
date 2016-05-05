package com.sughoshkumar.learningnetwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

public class ImageRetriever extends AsyncTask<String, String, Bitmap> {
    ImageResult mDelegate;

    ImageRetriever(ImageResult result){
        mDelegate = result;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urldisplay = params[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null){
            mDelegate.finishedRetrieving(bitmap);
        }
    }

    public interface ImageResult{
        void finishedRetrieving(Bitmap image);
    }
}
