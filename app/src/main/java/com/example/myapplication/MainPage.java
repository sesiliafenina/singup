package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    Button buttonRegister;
    Button buttonManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonManager = findViewById(R.id.buttonManager);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();   //open's Nina's event page
            }
        });

        /*buttonManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManagerActivity();
            }
        });*/

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /*
    public void openManagerActivity(){
        Intent intent = new Intent(this, ManagerActivity.class);
        startActivity(intent);
    }*/


}
