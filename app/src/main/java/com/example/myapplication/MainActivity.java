package com.example.myapplication;

// Create a new android studio project with Empty Activity
// Copy the code below
// Go to your own MainActivity.java and
// paste it over the existing code BELOW the package statement ***
// ***Sep 2019

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;


//TODO 1.1 Put in some images in the drawables folder
//TODO 1.2 Go to activity_main.xml and modify the layout

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String[] nameArray = {"Google Recruitment Talk", "Apple Recruitment Talk", "Facebook Coding Workshop", "Men in Technology",
    "How to Fix Your Resume?", "A Better World By Design", "Minecraft Workshop"};

    String[] infoArray = {"Google is recruiting and YOU can be one of it!! Register now, slots are limited!",
            "Apple is recruiting and YOU can be one of it!! Register now, slots are limited!",
            "Wanna learn how to code better? Attend a coding workshop hosted by Facebook in their headquarters and" +
                    "use this opportunity to learn what it is like to work in facebook!",
            "Too many women in tech-related jobs? Fear not! We will be discussing on how men can contribute to technology!",
            "Your resume looks like its made by a 5 year-old? We are here to fix it! Attend our workshop for free and" +
                    "fix your resume now! We will be providing free consultation as well!!",
            "Ever wanted to know how design can impact our daily lives? Join SUTD and you will be exposed to our diverse" +
                    "environment.",
            "Wanted to make your own minecraft modpack but don't know how? Well after you attend this workshop you will know" +
                    "everything you need to know about coding your own modpack and integrating it with minecraft"
    };

    Integer[] imageArray = {R.drawable.google, R.drawable.apple, R.drawable.facebook, R.drawable.menintech, R.drawable.resume,
                        R.drawable.sutd, R.drawable.minecraft};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventslist);

        // have to get lists of name and info of events here
        CustomListAdapter listAdapter = new CustomListAdapter(this, nameArray, infoArray, imageArray);

        listView = findViewById(R.id.listviewID);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                String eventTitle = nameArray[position]; // creates the message. Later this will be event description
                String eventInfo = infoArray[position];
                Integer eventImage = imageArray[position];
                intent.putExtra("eventName", eventTitle);
                intent.putExtra("eventInfo", eventInfo);
                intent.putExtra("eventPicture", eventImage);
                startActivity(intent);
            }
        });

    }
}
