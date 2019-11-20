package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.register);

        String eventName = getIntent().getStringExtra("eventName");
        TextView getEventName = findViewById(R.id.eventName);
        getEventName.setText(eventName);

        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendRegistrationForm(v);
            }
        });
    }

    public void sendRegistrationForm(android.view.View v){
        //Intent intent = new Intent(DetailsActivity.this, RegisterActivity.class);
        //startActivity(intent);

    }
}
