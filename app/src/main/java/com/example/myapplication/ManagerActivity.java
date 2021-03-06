package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutionException;

public class ManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Button registerAttendeesButton = findViewById(R.id.registerAttendeesButton);
        Button manageEventButton = findViewById(R.id.manageEventButton);

        registerAttendeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, ManagerRegisterActivity.class);
                startActivity(intent);
            }
        });

        manageEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, ManagerManageActivity.class);
                startActivity(intent);
            }
        });

        /*
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
        });*/

    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,"Scanning cancelled",Toast.LENGTH_LONG).show();
            }
            else{
                //Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                sendEventForm2(result.getContents());
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void sendEventForm2(String str){
        String url = "http://infosysmock-env.eba-wntiasbh.ap-southeast-1.elasticbeanstalk.com/api/attendance/QR";
        String result = null;
        JSONObject response = new JSONObject(); // initialize with no default value
        HttpPostStringRequest postStringRequest = new HttpPostStringRequest();
        postStringRequest.addParams("qr_secret", str);
        postStringRequest.addParams("mode", "norman");

        try {
            result = postStringRequest.execute(url).get();
        }
        catch (InterruptedException | ExecutionException e){
            Log.e("MainActivity", "Thread is interrupted!", e);
        }

        if (result == null){
            Toast.makeText(getApplicationContext(), "Attendee does not exist!", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO: parse response here to see if http request is successful

        Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_LONG).show();

        /*
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameters = new RequestParams();
        parameters.put("qr_secret", str);
        parameters.put("mode", "norman");

        client.post("http://infosysmock-env.eba-wntiasbh.ap-southeast-1.elasticbeanstalk.com/attendance/QR", parameters, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                CharSequence c = "Successfully registered";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();

                //Intent intent = new Intent(AddEventActivity.this, EventCreatedActivity.class);
                //startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                CharSequence c = "Register failed";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });*/
    }
}
