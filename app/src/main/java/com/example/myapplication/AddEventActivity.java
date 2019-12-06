package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {
    private int EVENT_IMAGE_REQUEST_CODE = 1;
    private int GUEST_IMAGE_REQUEST_CODE_1 = 2;
    private int GUEST_IMAGE_REQUEST_CODE_2 = 3;
    private int GUEST_IMAGE_REQUEST_CODE_3 = 4;
    private int GUEST_IMAGE_REQUEST_CODE_4 = 5;

    // List of params to create events
    private String title;
    private String description;
    private String location;
    private String start;
    private String end;
    private String date;
    private List<Bitmap> listOfGGuest = new ArrayList<>();
    private Bitmap eventImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        final TextView mTextView = findViewById(R.id.textCounter);
        final EditText mEditText = findViewById(R.id.eventDescription);

        Button button = findViewById(R.id.addImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickEventImage();
            }
        });
        Button button2 = findViewById(R.id.addGuest);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGuestImage(R.id.guestImage);
            }
        });

        Button button3 = findViewById(R.id.addGuest2);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGuestImage(R.id.guestImage2);
            }
        });

        Button button4 = findViewById(R.id.addGuest3);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGuestImage(R.id.guestImage3);
            }
        });

        Button button5 = findViewById(R.id.addGuest4);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGuestImage(R.id.guestImage4);
            }
        });

        Button register = findViewById(R.id.addEvent);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEventForm2();
            }
        });

        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                mTextView.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        mEditText.addTextChangedListener(mTextEditorWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EVENT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.registerEventImage);
                imageView.setImageBitmap(bitmap);
                eventImage = bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == GUEST_IMAGE_REQUEST_CODE_1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.guestImage);
                imageView.setImageBitmap(bitmap);
                listOfGGuest.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == GUEST_IMAGE_REQUEST_CODE_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.guestImage2);
                imageView.setImageBitmap(bitmap);
                listOfGGuest.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == GUEST_IMAGE_REQUEST_CODE_3 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.guestImage3);
                imageView.setImageBitmap(bitmap);
                listOfGGuest.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == GUEST_IMAGE_REQUEST_CODE_4 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.guestImage4);
                imageView.setImageBitmap(bitmap);
                listOfGGuest.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void pickEventImage(){
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,EVENT_IMAGE_REQUEST_CODE);
    }

    private void pickGuestImage(int id){
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        if (R.id.guestImage == id) {
            startActivityForResult(intent, GUEST_IMAGE_REQUEST_CODE_1);
        }
        else if (R.id.guestImage2 == id) {
            startActivityForResult(intent, GUEST_IMAGE_REQUEST_CODE_2);
        }
        else if (R.id.guestImage3 == id) {
            startActivityForResult(intent, GUEST_IMAGE_REQUEST_CODE_3);
        }
        else if (R.id.guestImage4 == id) {
            startActivityForResult(intent, GUEST_IMAGE_REQUEST_CODE_4);
        }
    }

    private void sendEventForm2(){
        AsyncHttpClient client = new AsyncHttpClient();
        getAllParams();
        RequestParams parameters = new RequestParams();
        parameters.put("title", title);
        parameters.put("description", description);
        parameters.put("location", location);
        for (Bitmap butt : listOfGGuest){
            parameters.put("speaker_images[]", new ByteArrayInputStream(bitmapToByteArray(butt)));
        }
        parameters.put("picture", new ByteArrayInputStream(bitmapToByteArray(eventImage)));
        parameters.put("start", start);
        parameters.put("end", end);

        client.post("http://infosys-mock.ap-southeast-1.elasticbeanstalk.com/api/events", parameters, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                CharSequence c = "Http successful";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddEventActivity.this, EventCreatedActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                CharSequence c = "Http failed";
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG).show();
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

    private void getAllParams(){
        EditText eventTitle = findViewById(R.id.eventTitle);
        title = eventTitle.getText().toString();

        EditText eventDescription = findViewById(R.id.eventDescription);
        description = eventDescription.getText().toString();

        EditText eventLocation = findViewById(R.id.eventLocation);
        location = eventLocation.getText().toString();

        EditText dateD = findViewById(R.id.eventDate);
        date = dateD.getText().toString();

        EditText startTime = findViewById(R.id.eventStartTime);
        String startT = startTime.getText().toString();
        start = date + " " + startT;

        EditText endTime = findViewById(R.id.eventEndTime);
        String endT = endTime.getText().toString();
        end = date + " " + endT;
    }
}
