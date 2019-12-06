package com.example.myapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private Bitmap picture;
    public static String qrlink;
    Button mCaptureBtn;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.register);

        Button selfie = findViewById(R.id.selfieButton);

        selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                    PackageManager.PERMISSION_DENIED ){
                        String [] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        try {
                            openCamera();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    try {
                        openCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        String eventName = getIntent().getStringExtra("eventName");
        TextView getEventName = findViewById(R.id.eventName);
        getEventName.setText(eventName);

        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendRegistrationForm();
            }
        });
    }

    private void sendRegistrationForm() {

        String url = "http://infosys-mock.ap-southeast-1.elasticbeanstalk.com/api/events/1/register/QR";
        EditText emailtext = findViewById(R.id.email);
        EditText nameText = findViewById(R.id.name);
        String email = emailtext.getText().toString();
        String name = nameText.getText().toString();
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("name", name);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setEnableRedirects(true);
        client.addHeader("Accept", "application/json");
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                CharSequence c = "Registered";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();
                try {
                    String qrLink = response.getString("qr_link");
                    Log.d("QR LINK", qrLink);
                    qrlink = qrLink;
                    //downloadQrCode(qrLink);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //move to a different intent here
                Intent intent = new Intent(RegisterActivity.this, QRCodeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                Log.d("ERROR MESSAGE String", string);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                if (statusCode == 400){
                    try {
                        String error = errorResponse.getJSONObject(1).getString("error");
                        if (error.equals("waitlist")){
                            // TODO: change the intent
                            Intent intent = new Intent(RegisterActivity.this, QRCodeActivity.class);
                            startActivity(intent);
                        }
                        else if (error.equals("full")){
                            // TODO: change the intent
                            Intent intent = new Intent(RegisterActivity.this, QRCodeActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openCamera() throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
        picture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
        sendPictureToServer();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    try {
                        openCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(this, "Permissied denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            sendPictureToServer();
        }
    }

    private void sendPictureToServer(){           //TODO COPY PASTED FROM NINA sendEventForm2() in AddEventActivity    //TODO Toast message if Person VALID or INVALID
        AsyncHttpClient client = new AsyncHttpClient();
        //getAllParams();

        RequestParams parameters = new RequestParams();

        /*
        parameters.put("title", title);
        parameters.put("description", description);
        parameters.put("location", location);
        for (Bitmap butt : listOfGGuest){
            parameters.put("speaker_images[]", new ByteArrayInputStream(bitmapToByteArray(butt)));
        }*/
        parameters.put("picture", new ByteArrayInputStream(bitmapToByteArray(picture)));
        //parameters.put("start", start);
        //parameters.put("end", end);

        client.post("http://infosys-mock.ap-southeast-1.elasticbeanstalk.com/api/events", parameters, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                CharSequence c = "Http successful";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();
                // called when response HTTP status is "200 OK"
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                CharSequence c = "Http failed";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });
    }
    private byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}