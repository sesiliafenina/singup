package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerManageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_manage_events);

        Button createEventButton = findViewById(R.id.createEventButton);
        Button deleteEventButton = findViewById(R.id.deleteEventButton);

        createEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ManagerManageActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        deleteEventButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerManageActivity.this, DeleteEventActivity.class);
                startActivity(intent);
            }
        }));
    }
}
