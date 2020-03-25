package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetImageRequest extends AsyncTask<String, Void, Bitmap> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        String result;
        String inputLine;
        Bitmap bmp = null;

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(url);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();

            bmp = BitmapFactory.decodeStream(connection.getInputStream());

        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }
        return bmp;
    }
}

