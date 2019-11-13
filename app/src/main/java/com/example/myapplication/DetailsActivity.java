package com.example.myapplication;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String savedExtra = getIntent().getStringExtra("animal");
        TextView myText = findViewById(R.id.eventDetails);
        myText.setText(savedExtra);
    }
}
