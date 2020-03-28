package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class HttpPostStringRequest extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private JSONObject postDataParams = new JSONObject();

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result;
        String inputLine;

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(url);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(encodeParams(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            //Connect to our url
            connection.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();

        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    private static String encodeParams(JSONObject params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = null;
            try {
                value = params.get(key);
            }
            catch(JSONException e){
                Log.e("MyApplication", "Value for key " + key + " not found", e);
            }
            if (first)
                first = false;
            else
                result.append("&");
            try {
                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                assert value != null; // throws assertion exception if the value is null
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }
            catch (UnsupportedEncodingException e){
                Log.e("MyApplication", "Encoding UTF-8 unsupported", e);
            }
        }
        return result.toString();
    }

    public void addParams(String key, Object value){
        try {
            postDataParams.put(key, value);
        }
        catch(JSONException e){
            Log.e("MyApplication", "Adding http parameters failed", e);
        }
    }
}
