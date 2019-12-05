package com.example.myapplication;

// Create a new android studio project with Empty Activity
// Copy the code below
// Go to your own MainActivity.java and
// paste it over the existing code BELOW the package statement ***
// ***Sep 2019

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import com.android.volley.toolbox.JsonArrayRequest;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;



//TODO 1.1 Put in some images in the drawables folder
//TODO 1.2 Go to activity_main.xml and modify the layout

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> nameArray;
    private List<String> infoArray;
    private List<Bitmap> imageArray;
    private List<String> eventTime;
    private List<String> eventDate;
    private List<URL> urlList;
    private List<JSONArray> guestUrlList;
    private List<List<Bitmap>> guestArray;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventslist);

        final SwipeRefreshLayout swipeRefreshLayout;
        getParams();
        //Log.d("testing", "HELLO WORLD");
        //Log.d("List of images", imageArray.toString());
        // have to get lists of name and info of events here
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.Swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //TODO http request here
                getParams();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);

                CustomListAdapter listAdapter = new CustomListAdapter(MainActivity.this, nameArray, infoArray, imageArray, eventTime);

                listView = findViewById(R.id.listviewID);
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        String eventTitle = nameArray.get(position); // creates the message. Later this will be event description
                        String eventInfo = infoArray.get(position);
                        Bitmap eventImage = imageArray.get(position);
                        List<Bitmap> guestImages = guestArray.get(position);
                        intent.putExtra("eventName", eventTitle);
                        intent.putExtra("eventInfo", eventInfo);
                        try {
                            File file = new File(getCacheDir(), "eventImages");

                            Log.d("Filepath", file.getAbsolutePath());
                            Log.d("MAIN ACTIVITY CACHE DIR", getCacheDir().toString());
                            FileOutputStream fOut = new FileOutputStream(file);
                            eventImage.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int i = 0;
                        for (Bitmap b : guestImages){
                            File file2 = new File(getCacheDir(), "guestImages" + i);
                            try {
                                FileOutputStream fOut2 = new FileOutputStream(file2);
                                b.compress(Bitmap.CompressFormat.PNG, 85, fOut2);
                                fOut2.close();
                                i++;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void getParams(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://infosys-mock.ap-southeast-1.elasticbeanstalk.com/api/events", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                nameArray = new ArrayList<>();
                infoArray = new ArrayList<>();
                eventTime = new ArrayList<>();
                eventDate = new ArrayList<>();
                urlList = new ArrayList<>();
                guestUrlList = new ArrayList<>();
                // If the response is JSONObject instead of expected JSONArray
                try {

                    Log.d("Response", response.toString());
                    Toast.makeText(getApplicationContext(), "Http successful", Toast.LENGTH_LONG);
                    Object a = response.getJSONObject(0).getString("id");
                    for (int i = 0; i < response.length(); i++){
                        nameArray.add(response.getJSONObject(i).getString("title"));
                        infoArray.add(response.getJSONObject(i).getString("description"));
                        String eventDateRaw = response.getJSONObject(i).getString("start");
                        eventDate.add(eventDateRaw.split(" ")[0]);
                        String startTime = eventDateRaw.split(" ")[1];
                        String endTime = response.getJSONObject(i).getString("end").split(" ")[1];
                        eventTime.add(startTime + " - " + endTime);
                        try {
                            URL url = new URL(response.getJSONObject(i).getString("picture"));
                            JSONArray strings = (JSONArray) response.getJSONObject(i).get("speaker_images");
                            urlList.add(url);
                            guestUrlList.add(strings);
                        } catch (IOException e) {
                            // Log exception
                            Log.d("FAILED FETCHING IMAGE", e.toString());
                        }
                    }
                    Log.d("JSONARRAY OBJECT", a.toString());
                }
                catch (JSONException e){
                    Log.d("BEEP!BEEP!BEEP", "SYSTEM FAILURE BLABLA THE CODE HAVE FAILED SO BADLY JUST LIKE" +
                            "HOW THEY NEVER MAKE NGNL SEASON 2");
                }
                getEventImages();
                try {
                    getGuestImages();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
                Toast.makeText(getApplicationContext(), "Http failed", Toast.LENGTH_LONG);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });
    }

    private void getEventImages(){
        AsyncHttpClient client = new AsyncHttpClient();
        imageArray = new ArrayList<>();
        for (URL i : urlList){
            client.get(i.toString(), new FileAsyncHttpResponseHandler(this){
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    Toast.makeText(getApplicationContext(), "fetching image failed", Toast.LENGTH_SHORT).show();
                    Log.d("GETTING IMAGE failed", "getting image failed");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File response) {
                    Log.d("GETTING IMAGE SUCCESSFUL", "getting image successful");
                    byte[] bytesArray = new byte[(int) response.length()];

                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(response);
                        fis.read(bytesArray); //read file into bytes[]
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // convert byteArray to bitmap and compress it
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 30, out);
                    bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                    imageArray.add(bitmap);
                }
            });
            Log.d("This is iamgeList", imageArray.toString());
        }
    }

    private void getGuestImages() throws JSONException {
        guestArray = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        for (JSONArray ls : guestUrlList){
            final List<Bitmap> bmp = new ArrayList<>();
            for (int i = 0; i < ls.length(); i++) {
                String str = ls.getString(i);
                client.get(str, new FileAsyncHttpResponseHandler(this) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        Toast.makeText(getApplicationContext(), "fetching image failed", Toast.LENGTH_SHORT).show();
                        Log.d("GETTING IMAGE failed", "getting image failed");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File response) {
                        Log.d("GETTING IMAGE SUCCESSFUL", "getting image successful");
                        byte[] bytesArray = new byte[(int) response.length()];

                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(response);
                            fis.read(bytesArray); //read file into bytes[]
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // convert byteArray to bitmap and compress it
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 30, out);
                        bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                        bmp.add(bitmap);
                    }
                });
            }
            guestArray.add(bmp);
            Log.d("This is guestList", guestArray.toString());
        }
    }
}
