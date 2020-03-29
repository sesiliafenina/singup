package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.StrongBoxUnavailableException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DeleteEventActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> nameArray;
    private List<String> infoArray;
    private List<Bitmap> imageArray;
    private List<String> eventTime;
    private List<String> eventDate;
    private List<String> eventLocation;
    private List<URL> urlList;
    private List<JSONArray> guestUrlList;
    private List<List<Bitmap>> guestArray;
    private List<Bitmap> bmp;
    private List<String> idArray;

    private static String url = "http://infosysmock-env.eba-wntiasbh.ap-southeast-1.elasticbeanstalk.com/api/events/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist_delete);

        final SwipeRefreshLayout swipeRefreshLayout;
        getParams();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.Swipe2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO http request here
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);

                CustomListAdapter2 listAdapter = new CustomListAdapter2(DeleteEventActivity.this, nameArray, infoArray, imageArray, eventTime, idArray);

                listView = findViewById(R.id.listview_delete);
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                                                            long id) {
                                                        Toast.makeText(getApplicationContext(), "Button pressed!", Toast.LENGTH_LONG).show();
                                                        LayoutInflater inflater = (LayoutInflater)
                                                                getSystemService(LAYOUT_INFLATER_SERVICE);
                                                        final View popupView = inflater.inflate(R.layout.popup, null);
                                                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);
                                                        final String pos = idArray.get(position);

                                                        Button yes = popupView.findViewById(R.id.yes);
                                                        Button no = popupView.findViewById(R.id.no);

                                                        yes.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                String result = null;

                                                                HttpDeleteRequest deleteRequest = new HttpDeleteRequest();
                                                                Log.d("HTTP URL", url + pos);
                                                                try {
                                                                    result = deleteRequest.execute(url + pos).get();
                                                                }
                                                                catch (InterruptedException | ExecutionException e){
                                                                    Log.e("MainActivity", "Thread is interrupted!", e);
                                                                }

                                                                if (result == null){
                                                                    Log.e("MyApplication", "Http Request Failed");
                                                                    return;
                                                                }

                                                                if (result.equals("")){
                                                                    Toast.makeText(getApplicationContext(), "Event is successfully deleted", Toast.LENGTH_LONG).show();
                                                                    popupWindow.dismiss();
                                                                }
                                                                else{
                                                                    Log.e("MyApplication", "Event is not successfully deleted. See result for details :\n" + result);
                                                                }

                                                            }
                                                        });

                                                        no.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                popupWindow.dismiss();
                                                            }
                                                        });

                                                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                                    }
                                                });

                Handler mainHandler = new Handler(DeleteEventActivity.this.getApplicationContext().getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        getParams();
                    }
                };
                mainHandler.post(myRunnable);
            }
        });

    }
    private void getParams(){
        String url = "http://infosysmock-env.eba-wntiasbh.ap-southeast-1.elasticbeanstalk.com/api/events";
        String result = null;
        JSONArray response = new JSONArray(); // initialize with no default value

        HttpGetStringRequest getRequest = new HttpGetStringRequest();
        try {
            result = getRequest.execute(url).get();
        }
        catch (InterruptedException | ExecutionException e){
            Log.e("MainActivity", "Thread is interrupted!", e);
        }

        if (result == null){
            Toast.makeText(getApplicationContext(), "There is no current events", Toast.LENGTH_LONG).show();
        }

        Log.d("Response", result);

        try{
            response = new JSONArray(result);
        }catch (JSONException e){
            Log.e("MainActivity", "Could not parse malformed JSON", e);
        }
        Toast.makeText(getApplicationContext(), "Http successful", Toast.LENGTH_LONG).show();

        nameArray = new ArrayList<>();
        infoArray = new ArrayList<>();
        eventTime = new ArrayList<>();
        eventDate = new ArrayList<>();
        urlList = new ArrayList<>();
        guestUrlList = new ArrayList<>();
        eventLocation = new ArrayList<>();
        idArray = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                nameArray.add(response.getJSONObject(i).getString("title"));
                idArray.add(response.getJSONObject(i).getString("id"));
                infoArray.add(response.getJSONObject(i).getString("description"));
                String eventDateRaw = response.getJSONObject(i).getString("start");
                eventDate.add(eventDateRaw.split(" ")[0]);
                String startTime = eventDateRaw.split(" ")[1];
                String endTime = response.getJSONObject(i).getString("end").split(" ")[1];
                eventTime.add(startTime + " - " + endTime);
                eventLocation.add(response.getJSONObject(i).getString("location"));
                try {
                    URL picture = new URL(response.getJSONObject(i).getString("picture"));
                    JSONArray strings = (JSONArray) response.getJSONObject(i).get("speaker_images");
                    urlList.add(picture);
                    guestUrlList.add(strings);
                } catch (MalformedURLException e) {
                    // Log exception
                    Log.e("MainActivity", "Invalid URL given", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("MyApplication", "JSON Object not found", e);
        }

        Handler mainHandler = new Handler(this.getApplicationContext().getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                getEventImages();
            }
        };
        mainHandler.post(myRunnable);

        Handler mainHandler2 = new Handler(this.getApplicationContext().getMainLooper());
        Runnable myRunnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    getGuestImages();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mainHandler2.post(myRunnable2);
    }
    private void getEventImages() {

        imageArray = new ArrayList<Bitmap>(Collections.<Bitmap>nCopies(urlList.size(), null));


        for (int j = 0; j < urlList.size(); j++) {
            HttpGetImageRequest getImageRequest = new HttpGetImageRequest();
            Bitmap bitmap = null;
            try {
                bitmap = getImageRequest.execute(urlList.get(j).toString()).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.e("MainActivity", "Thread is interrupted!", e);
            }
            if (bitmap == null) {
                Log.e("MainActivity", "Failed fetching image from url");
            }
            imageArray.set(j, bitmap);
        }
    }
    private void getGuestImages() throws JSONException {
        guestArray = new ArrayList<>();
        for (final JSONArray ja : guestUrlList) {
            Handler mainHandler = new Handler(this.getApplicationContext().getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    bmp = new ArrayList<>();
                    for (int j = 0; j < ja.length(); j++) {
                        HttpGetImageRequest getImageRequest = new HttpGetImageRequest();
                        Bitmap bitmap = null;
                        try {
                            bitmap = getImageRequest.execute(ja.getString(j)).get();
                            bmp.add(bitmap);
                        } catch (InterruptedException | ExecutionException | JSONException e) {
                            Log.e("MainActivity", "Thread is interrupted!", e);
                        }
                        if (bitmap == null) {
                            Log.e("MainActivity", "Failed fetching image from url");
                        }
                    }
                }
            };
            mainHandler.post(myRunnable);
        }
        guestArray.add(bmp);
    }

    private void deleteEvent(int id){

    }
}
