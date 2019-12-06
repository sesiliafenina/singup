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

public class QRCodeActivity extends AppCompatActivity {
    public final static int WHITE = 0xFF282828;
    public final static int BLACK = 0xFFFFFFFF;
    public final static int WIDTH = 700;
    public final static int HEIGHT = 700;
    private Bitmap qrcode;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //File qrCode = new File(getCacheDir(), "qrCode");
        //imageView.setImageBitmap(BitmapFactory.decodeFile(qrCode.getPath()));
        setContentView(R.layout.qr_code);
        downloadQrCode(RegisterActivity.qrlink);


    }

    private void downloadQrCode(String url) {
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
                Log.d("DOWNLOAD QR CODE SUCCESSFUL", "LOLOLOLOLOOL");
                Log.d("THIS IS FILE", response.toString());
/*
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(response);
                    fis.read(bytesArray); //read file into bytes[]
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // convert byteArray to bitmap and compress it
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                Log.d("THIS IS BITMAP", bitmap.toString());*/
                String filePath = response.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                ImageView imageView = (ImageView) findViewById(R.id.qrCodes);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
