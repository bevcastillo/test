package com.example.androidtest.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.androidtest.Constant;
import com.example.androidtest.R;
import com.example.androidtest.model.ApiResponse;
import com.example.androidtest.model.Payload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdatePasswActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_currpassw, et_newpassw, et_repassw;
    Button btnUpdate;
    String userToken, strpassw;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_passw);

        setTitle("Update Password");

        et_currpassw = findViewById(R.id.et_curr_passw);
        et_newpassw = findViewById(R.id.et_new_passw);
        et_repassw = findViewById(R.id.et_re_passw);
        btnUpdate = findViewById(R.id.btn_update_passw);

        btnUpdate.setOnClickListener(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_update_passw:
                try {
                    run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strpassw = bundle.getString("passw");

//            et_currpassw.setText(strpassw);

            Toast.makeText(this, strpassw+" is your passw", Toast.LENGTH_SHORT).show();
        }
    }

    void run() throws IOException {

        String currpassw = et_currpassw.getText().toString().trim();
        String newpassw = et_newpassw.getText().toString().trim();
        String repassw = et_repassw.getText().toString().trim();

        //getting the saved token from Shared Preferences
        SharedPreferences userTokenPref = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = (userTokenPref.getString("token",""));

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("current_password", currpassw)
                .addFormDataPart("password", newpassw)
                .addFormDataPart("password_confirmation", repassw)
                .build();

        Request request = new Request.Builder()
                .url(Constant.EDIT_USER_PASSWORD_API)
                .method("POST", body)
                .addHeader("Authorization", "Bearer "+userToken)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonData = response.body().string();

                UpdatePasswActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ApiResponse apiResponse = gson.fromJson(jsonData, ApiResponse.class);
                        Payload payload = gson.fromJson(jsonData, Payload.class);

                        String currpassw = et_currpassw.getText().toString().trim();
                        String newpassw = et_newpassw.getText().toString().trim();
                        String repassw = et_repassw.getText().toString().trim();

                        Log.i("UPDATEPASSW", jsonData);
                        Log.i("UPDATEPASSW", apiResponse.toString());
                        Log.i("UPDATEPASSW", payload.toString());

                        Toast.makeText(UpdatePasswActivity.this, apiResponse.getMsg()+" is the message", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}
