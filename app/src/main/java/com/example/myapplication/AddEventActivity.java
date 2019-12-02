package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddEventActivity extends AppCompatActivity {
    private int EVENT_IMAGE_REQUEST_CODE = 1;
    private int GUEST_IMAGE_REQUEST_CODE = 2;

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
                // TODO : also add the bitmap to a list of images to send to the server
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == GUEST_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.registerEventImage);
                imageView.setImageBitmap(bitmap);
                // TODO : also add the bitmap to a list of images to send to the server
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

    private void pickGuestImage(){
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GUEST_IMAGE_REQUEST_CODE);
    }

}
