package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class QRCodeActivity extends AppCompatActivity {
    public final static int WHITE = 0xFF282828;
    public final static int BLACK = 0xFFFFFFFF;
    public final static int WIDTH = 700;
    public final static int HEIGHT = 700;
    private Bitmap qrcode = null;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.qr_code);
        downloadQrCode(RegisterActivity.qrlink);
        Button toEventPageButton = findViewById(R.id.toEventPageButton);
        toEventPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(); //to Nina's events page
            }
        });
    }
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void downloadQrCode(String url) {
        HttpGetImageRequest getImageRequest = new HttpGetImageRequest();
        try {
            qrcode = getImageRequest.execute(url).get();
        }
        catch (InterruptedException | ExecutionException e){
            Log.e("MainActivity", "Thread is interrupted!", e);
        }
        if (qrcode == null){
            Log.e("MainActivity", "Failed fetching image from url");
        }

        ImageView imageView = (ImageView) findViewById(R.id.qrCodes);
        imageView.setImageBitmap(qrcode);

        /*
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                //Toast.makeText(getApplicationContext(), "fetching image failed", Toast.LENGTH_SHORT).show();
                Log.d("FETCHING QRCODE FAILED", "QR code fetching failed");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                byte[] bytesArray = new byte[(int) response.length()];
                Log.d("QR Code downloaded", "Attempt in downloading QR code successful");
                Log.d("THIS IS FILE", response.toString());

                String filePath = response.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                ImageView imageView = (ImageView) findViewById(R.id.qrCodes);
                imageView.setImageBitmap(bitmap);
            }
        });*/
    }
}
