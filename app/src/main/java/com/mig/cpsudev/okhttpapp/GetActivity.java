package com.mig.cpsudev.okhttpapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        /*final TextView textView = (TextView) findViewById(R.id.textView);
        //Synchronous GET
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://jsonplaceholder.typicode.com/posts/1").build();

                String responseText = "";
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        responseText = response.body().string();
                    } else {
                        responseText = "Not Success - Code : " + response.code();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return responseText;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String time = jsonObject.getString("time");
                    textView.setText(time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        //Asynchronous GET
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://jsonplaceholder.typicode.com/posts/2").build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        updateView("Error " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            updateView(response.body().string());
                        } else {
                            updateView("Not Success Code : " + response.code());
                        }
                    }

                    public void updateView(final String strResult) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(strResult);
                            }
                        });
                    }
                });
                return null;
            }
        }.execute();*/
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        //Asynchronous POST
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("id", "1");
            jsonObject.accumulate("name", "Non");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String json = jsonObject.toString();

        new AsyncTask<Void, Void, String>() {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(GetActivity.this, "Please wait ...", "Downloading Data ...", true);
            }

            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                Request request = builder
                        .url("http://posttestserver.com/post.php")
                        .post(RequestBody.create(JSON, json))
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        updateView("Post Fail");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            updateView(response.body().string());
                        } else {
                            updateView("Not Success Code : " + response.code());
                        }
                    }

                    public void updateView(final String strResult) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView3.setText(strResult);
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }.execute();
    }
}
