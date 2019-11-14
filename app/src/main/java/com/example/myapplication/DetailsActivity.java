package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerPerson(v);
            }
        });
    }

    public void registerPerson(android.view.View v){
        Log.d("CALLING", "register person is executed");
        Intent intent = new Intent(DetailsActivity.this, RegisterActivity.class);
        startActivity(intent); // this is causing exception
    }
}
