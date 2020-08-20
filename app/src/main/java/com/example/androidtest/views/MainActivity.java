package com.example.androidtest.views;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtest.helper.Constant;
import com.example.androidtest.R;
import com.example.androidtest.helper.Api;
import com.example.androidtest.model.ApiResponse;
import com.example.androidtest.model.Payload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_passw;
    Button btnLogin;
    TextView tvRegister;
    ImageView iv_logo;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //changing to ligh status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //
        et_email = findViewById(R.id.et_email);
        et_passw = findViewById(R.id.et_passw);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        iv_logo = findViewById(R.id.iv_logo);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        //adding animation to logo
        Animator translateAnimator = AnimatorInflater.loadAnimator(this, R.animator.translate);
        translateAnimator.setTarget(iv_logo);
        translateAnimator.start();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_login:
                String email = et_email.getText().toString().trim();
                String passw = et_passw.getText().toString().trim();

                try {
                    userLogin(email, passw);
                }catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tv_register:
                userRegister();
                break;
        }
    }

    private boolean validateFields() {
        String strUsername = et_email.getText().toString();
        String strPassw = et_passw.getText().toString();
        boolean ok = true;

        if (strUsername.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            ok = false;
        }

        if (strPassw.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT);
            ok = false;
        }

        return ok;
    }

    private void userRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void userLogin(String email, String passw) throws JSONException {
        if (validateFields()) {
            Payload payload = new Payload();
            payload.setEmail(email);
            payload.setPassw(passw);

            doLoginUser(payload, Constant.USER_LOGIN_API_URL);
        }
    }

    private void doLoginUser(Payload payload, String url) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", payload.getEmail());
        jsonObject.put("password", payload.getPassw());

        Api api = new Api();
        String json = api.payload(jsonObject);


        api.post(url, json, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String strResponse = response.body().string();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ApiResponse apiResponse = gson.fromJson(strResponse, ApiResponse.class);

                            //saving token to shared preference
                            SharedPreferences userTokenPref = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
                            SharedPreferences.Editor editor = userTokenPref.edit();

                            editor.putString("token", apiResponse.getToken());
                            editor.putString("id", apiResponse.getData().getUserId().toString());
                            editor.commit();

                            Toast.makeText(MainActivity.this, apiResponse.getMsg(), Toast.LENGTH_SHORT).show();

                            //passing mssg response to home screen
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("mssg", apiResponse.getMsg());
                            startActivity(intent);

//                            Bundle bundle = new Bundle();
//                            bundle.putString("mssg", apiResponse.getMsg());
//
//                            HomeFragment fragment = new HomeFragment();
//                            fragment.setArguments(bundle);
//
//                            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
//                            startActivity(intent);


//                            FragmentManager manager = getSupportFragmentManager();
//                            FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                            HomeFragment homeFragment = new HomeFragment();
//
//                            Bundle bundle = new Bundle();
//                            bundle.putString("mssg", apiResponse.getMsg());
//
//                            homeFragment.setArguments(bundle);
//                            fragmentTransaction.add()


                        }
                    });
                }

            }
        });
    }
}