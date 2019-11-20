package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    private String eventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        eventName = getIntent().getStringExtra("eventName");
        String eventInfo = getIntent().getStringExtra("eventInfo");
        TextView getEventName = findViewById(R.id.eventName);
        TextView getEventInfo = findViewById(R.id.eventDetails);
        getEventName.setText(eventName);
        getEventInfo.setText(eventInfo);

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
