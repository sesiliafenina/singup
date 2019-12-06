package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Activity activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Button qrcodeButton = findViewById(R.id.qrCodeButton);
        Button button = findViewById(R.id.createEvent);
        Button toFRpage = findViewById(R.id.facialrecognitionButton);
        toFRpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, FR_CAMERA.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,"Scanning cancelled",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();

            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
