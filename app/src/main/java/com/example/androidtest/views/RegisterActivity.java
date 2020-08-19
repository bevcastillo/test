package com.example.androidtest.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtest.Constant;
import com.example.androidtest.R;
import com.example.androidtest.helper.Api;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText et_fname, et_lname, et_contact, et_email, et_passw, et_repassw;
    Spinner spinnerType;
    Button btnRegister;
    TextView tvLogin;
    String selectedUserType;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");

        //
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_contact = findViewById(R.id.et_ctc_num);
        et_email = findViewById(R.id.et_email);
        et_passw = findViewById(R.id.et_passw);
        et_repassw = findViewById(R.id.et_re_passw);
        spinnerType = findViewById(R.id.spinner_type);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        spinnerType.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_register:
                String fname = et_fname.getText().toString().trim();
                String lname = et_lname.getText().toString().trim();
                String contact = et_contact.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String passw = et_passw.getText().toString().trim();
                String repassw = et_repassw.getText().toString().trim();

                try {
                    registerUser(fname, lname, contact, email, passw, repassw, selectedUserType);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tv_login:
                userLogin();
                break;
        }
    }

    public boolean validateFields() {
        String strfname = et_fname.getText().toString();
        String strlname = et_lname.getText().toString();
        String strcontact = et_contact.getText().toString();
        String stremail = et_email.getText().toString();
        String strpassw = et_passw.getText().toString();
        String strrepassw = et_repassw.getText().toString();
        boolean ok = true;

        if (strfname.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (strlname.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (strcontact.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (stremail.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (strpassw.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (strrepassw.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return ok;
    }

    public boolean comparePassw() {
        String strpassw = et_passw.getText().toString();
        String strrepassw = et_repassw.getText().toString();
        boolean ok = true;

        if (!strrepassw.equals(strpassw)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return ok;
    }

    public void registerUser(String fname, String lname, String contact, String email, String passw, String confPassw, String userType) throws JSONException {
        if (validateFields()) {
            if (comparePassw()) {
                Payload payload = new Payload();
                payload.setFname(fname);
                payload.setLname(lname);
                payload.setContact_number(contact);
                payload.setEmail(email);
                payload.setPassw(passw);
                payload.setConfPassw(confPassw);
                payload.setUser_type(userType);

                doRegisterUser(payload, Constant.USER_REGISTER_API_URL);

            }
        }

    }

    public void doRegisterUser(Payload payload, String url) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fname", payload.getFname());
        jsonObject.put("lname", payload.getLname());
        jsonObject.put("contact_number", payload.getContact_number());
        jsonObject.put("email", payload.getEmail());
        jsonObject.put("password", payload.getPassw());
        jsonObject.put("password_confirmation", payload.getConfPassw());
        jsonObject.put("type", payload.getUser_type());

        Api api = new Api();
        String json = api.payload(jsonObject);
        api.post(url, json, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String strResponse = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }



    public void userLogin() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int spinner_id = parent.getId();

        switch (spinner_id) {
            case R.id.spinner_type:
                selectedUserType = this.spinnerType.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
