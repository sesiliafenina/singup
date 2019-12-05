package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class DetailsActivity extends AppCompatActivity {

    private String eventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        eventName = getIntent().getStringExtra("eventName");
        String eventInfo = getIntent().getStringExtra("eventInfo");
        String eventDate = getIntent().getStringExtra("eventDate");
        String eventLocation = getIntent().getStringExtra("eventLocation");
        String eventTime = getIntent().getStringExtra("eventTime");
        //Integer eventImage = getIntent().getIntExtra("eventPicture", 0);
        TextView getEventName = findViewById(R.id.eventName);
        TextView getEventInfo = findViewById(R.id.eventDetails);
        ImageView getEventImage = findViewById(R.id.event_image);
        TextView getEventLocation = findViewById(R.id.eventPlace);
        TextView getEventTime = findViewById(R.id.eventTime);
        getEventName.setText(eventName);
        getEventInfo.setText(eventInfo);
        getEventLocation.setText(eventLocation);
        getEventTime.setText(eventTime);
        //getEventImage.setImageResource(eventImage);
        File cacheFile = new File(getCacheDir(), "eventImages");
        Log.d("DETAILSACTIVITY CACHE DIR", getCacheDir().toString());
        getEventImage.setImageBitmap(BitmapFactory.decodeFile(cacheFile.getPath()));

        ImageView getGuestImage1 = findViewById(R.id.profile_image);
        ImageView getGuestImage2 = findViewById(R.id.profile_image2);
        ImageView getGuestImage3 = findViewById(R.id.profile_image3);
        ImageView getGuestImage4 = findViewById(R.id.profile_image4);

        File guest1 = new File(getCacheDir(), "guestImages1");
        File guest2 = new File(getCacheDir(), "guestImages2");
        File guest3 = new File(getCacheDir(), "guestImages3");
        File guest4 = new File(getCacheDir(), "guestImages4");

        getGuestImage1.setImageBitmap(BitmapFactory.decodeFile(guest1.getPath()));
        getGuestImage2.setImageBitmap(BitmapFactory.decodeFile(guest2.getPath()));
        getGuestImage3.setImageBitmap(BitmapFactory.decodeFile(guest3.getPath()));
        getGuestImage4.setImageBitmap(BitmapFactory.decodeFile(guest4.getPath()));

        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerPerson(v);
            }
        });
    }

    public void registerPerson(android.view.View v){
        Intent intent = new Intent(DetailsActivity.this, RegisterActivity.class);
        String eventTitle = eventName;
        intent.putExtra("eventName", eventTitle);
        startActivity(intent);
    }
}
