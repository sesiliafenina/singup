package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddEventActivity extends AppCompatActivity {
    private int EVENT_IMAGE_REQUEST_CODE = 1;
    private int GUEST_IMAGE_REQUEST_CODE_1 = 2;
    private int GUEST_IMAGE_REQUEST_CODE_2 = 3;
    private int GUEST_IMAGE_REQUEST_CODE_3 = 4;
    private int GUEST_IMAGE_REQUEST_CODE_4 = 5;

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

        else if (requestCode == GUEST_IMAGE_REQUEST_CODE_1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.guestImage);
                imageView.setImageBitmap(bitmap);
                // TODO : also add the bitmap to a list of images to send to the server
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            // TODO: add another button right beside the previous button
            LinearLayout linearLayout = findViewById(R.id.guestListAdd);
            RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            Button btn = new Button(this);
            btn.setId(R.id.addGuest);
            //btn.setLayoutParams(params);
            btn.setBackgroundColor(Color.BLACK);
            btn.setWidth(210);
            btn.setHeight(210);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickGuestImage();
                }
            });

            ImageView imageView = new ImageView(this);
            imageView.setMaxWidth(210);
            imageView.setMaxHeight(210);
            //imageView.setScaleType();
            imageView.setId(R.id.guestImage);
            imageView.setBackgroundColor(Color.DKGRAY);

            //relativeLayout.addView(btn);
            //relativeLayout.addView(imageView);
            //linearLayout.addView(relative
            // Layout, params);
            //linearLayout.addView(btn);
            linearLayout.addView(imageView);
*/
        }
        else if (requestCode == GUEST_IMAGE_REQUEST_CODE_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.guestImage2);
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

}
