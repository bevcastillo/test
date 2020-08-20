package com.example.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class UpdateProfActivity extends AppCompatActivity {

    EditText et_fname, et_mname, et_suffix, et_lname, et_username, et_email, et_bdate, et_contact,
                et_addr;

    String strToken;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_prof);

        setTitle("Update Profile Information");

        et_fname = findViewById(R.id.et_fname);
        et_mname = findViewById(R.id.et_mname);
        et_lname = findViewById(R.id.et_lname);
        et_suffix = findViewById(R.id.et_suffix);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_bdate = findViewById(R.id.et_bdate);
        et_contact = findViewById(R.id.et_ctc_num);
        et_addr = findViewById(R.id.et_radd);

        try {
            run();
        }catch (Exception e) {

        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    void run() throws IOException {

        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();

//        RequestBody requestBody =
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strToken = bundle.getString("user_token");
        }
    }
}
