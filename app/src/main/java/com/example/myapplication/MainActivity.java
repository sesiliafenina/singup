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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//TODO 1.1 Put in some images in the drawables folder
//TODO 1.2 Go to activity_main.xml and modify the layout

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> nameArray = new ArrayList<>();

    private List<String> infoArray = new ArrayList<>();

    private List<Bitmap> imageArray = new ArrayList<>();

    private List<String> eventTime = new ArrayList<>();

    private List<String> eventDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventslist);

        final SwipeRefreshLayout swipeRefreshLayout;

        // have to get lists of name and info of events here
        CustomListAdapter listAdapter = new CustomListAdapter(this, nameArray, infoArray, imageArray, eventTime);

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
                intent.putExtra("eventName", eventTitle);
                intent.putExtra("eventInfo", eventInfo);
                intent.putExtra("eventPicture", eventImage);
                startActivity(intent);
            }
        });

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
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap myBitmap = BitmapFactory.decodeStream(input);
                            imageArray.add(myBitmap);
                        } catch (IOException e) {
                            // Log exception
                            Log.d("FAILED FETCHING IMAGE", e.toString());
                        }
                    }
                    Log.d("JSONARRAY LENGTH", String.valueOf(response.length()));
                    Log.d("JSONARRAY OBJECT", a.toString());
                }
                catch (JSONException e){
                    Log.d("BEEP!BEEP!BEEP", "SYSTEM FAILURE BLABLA THE CODE HAVE FAILED SO BADLY JUST LIKE" +
                            "HOW THEY NEVER MAKE NGNL SEASON 2");
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
}
