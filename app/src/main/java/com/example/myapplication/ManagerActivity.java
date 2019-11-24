package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ManagerActivity extends AppCompatActivity {
    Button qrCodeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        qrCodeButton = findViewById(R.id.qrCodeButton);
        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQR_AttendanceManager();
            }
        });



    }

    public void openQR_AttendanceManager() {
        Intent intentQR = new Intent(this,QR_Attendance_Manager.class);
        startActivity(intentQR);

    }

}
