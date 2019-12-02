package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.register);

        String eventName = getIntent().getStringExtra("eventName");
        TextView getEventName = findViewById(R.id.eventName);
        getEventName.setText(eventName);

        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, QRCodeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendRegistrationFormJSON(android.view.View v){

        String url = "https://jsonplaceholder.typicode.com/todos/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            String userID = response.getString("userId");
                            String title = response.getString("title");
                            /*TextView getUserId = findViewById(R.id.userId);
                            TextView getTitle = findViewById(R.id.title);
                            getUserId.setText(response.toString());
                            getTitle.setText(userID + title);*/

                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        Queue.getInstance(this).addToRequestQueue(jsonObjectRequest);

        //Intent intent = new Intent(RegisterActivity.this, QRCodeActivity.class);
        //startActivity(intent);
    }
}

class AsyncHttpRequest extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... voids) {
        // this will later be changed according to the form values
        File uploadFile1 = new File("e:/Test/PIC1.JPG");
        File uploadFile2 = new File("e:/Test/PIC2.JPG");

        // need to change URL
        String requestURL = "http://localhost:8080/FileUploadSpringMVC/uploadFile.do";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, "UTF-8");

            // this will later be changed according to the form values
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");

            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");

            multipart.addFilePart("fileUpload", uploadFile1);
            multipart.addFilePart("fileUpload", uploadFile2);

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
            //return response;

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null; // code should never reach here
    }
}