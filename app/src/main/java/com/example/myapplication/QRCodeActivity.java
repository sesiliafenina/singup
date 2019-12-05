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
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QRCodeActivity extends AppCompatActivity {
    public final static int WHITE = 0xFF282828;
    public final static int BLACK = 0xFFFFFFFF;
    public final static int WIDTH = 700;
    public final static int HEIGHT = 700;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.qr_code);
        ImageView imageView = findViewById(R.id.qrCode);
        File qrCode = new File(getCacheDir(), "qrCode");
        imageView.setImageBitmap(BitmapFactory.decodeFile(qrCode.getPath()));
    }
}
