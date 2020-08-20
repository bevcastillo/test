package com.example.androidtest.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidtest.helper.Constant;
import com.example.androidtest.R;
import com.example.androidtest.model.ApiResponse;
import com.example.androidtest.model.Payload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateProfActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_fname, et_mname, et_suffix, et_lname, et_username, et_email, et_bdate, et_contact,
                et_addr;

    String strToken, strfname, strmname, strlname, strsuffix, stremail, strusername, strbdate, strcontact,
            straddr;

    Button btnUpdate;

    Gson gson;

    DatePickerDialog datePickerDialog;

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
        btnUpdate = findViewById(R.id.btn_update_prof);

        btnUpdate.setOnClickListener(this);
        et_bdate.setOnClickListener(this);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strToken = bundle.getString("user_token");
            strfname = bundle.getString("fname");
            strlname = bundle.getString("lname");
            strmname = bundle.getString("mname");
            strsuffix = bundle.getString("suffix");
            strusername = bundle.getString("username");
            stremail = bundle.getString("email");
            strbdate = bundle.getString("bdate");
            strcontact = bundle.getString("contact");
            straddr = bundle.getString("residence");

            et_fname.setText(strfname);
            et_lname.setText(strlname);
            et_mname.setText(strmname);
            et_suffix.setText(strsuffix);
            et_username.setText(strusername);
            et_email.setText(stremail);
            et_bdate.setText(strbdate);
            et_contact.setText(strcontact);
            et_addr.setText(straddr);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_update_prof:
                try {
                    run();
//                    updateUser();
                }catch (Exception e) {

                }
                break;
            case R.id.et_bdate:
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(UpdateProfActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_bdate.setText(year+"-"+month+"-"+day);
                    }
                }, year, month, day);

                datePickerDialog.show();
                break;
        }
    }

    private boolean validateFields() {
        String fname = et_fname.getText().toString().trim();
        String lname = et_lname.getText().toString().trim();
        String bdate = et_bdate.getText().toString().trim();
        String addr = et_addr.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        boolean ok = true;
        
        if (fname.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (lname.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (bdate.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (addr.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (username.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return ok;
    }


    void run() throws IOException {

        String lname = et_lname.getText().toString().trim();
        String fname = et_fname.getText().toString().trim();
        String mname = et_mname.getText().toString().trim();
        String suffix = et_suffix.getText().toString().trim();
        String contact = et_contact.getText().toString().trim();
        String addr = et_addr.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String username = et_username.getText().toString();
        String bday = et_bdate.getText().toString().trim();

        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("include", "identification")
                .addFormDataPart("fname", fname)
                .addFormDataPart("mname", mname)
                .addFormDataPart("lname", lname)
                .addFormDataPart("suffix", suffix)
                .addFormDataPart("username", username)
                .addFormDataPart("email", email)
                .addFormDataPart("birthdate", bday)
                .addFormDataPart("contact_number", contact)
                .addFormDataPart("residence_address", addr)
                .build();

        final Request request = new Request.Builder()
                .url(Constant.EDIT_USER_PROFILE_API_URL)
                .method("POST", body)
                .addHeader("Authorization", "Bearer"+strToken)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonData = response.body().string();

                UpdateProfActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("UPDATE ACTIVITY", jsonData);

                        if (validateFields()) {

                            ApiResponse apiResponse = gson.fromJson(jsonData, ApiResponse.class);
                            Payload payload = gson.fromJson(jsonData, Payload.class);

                            String lname = et_lname.getText().toString().trim();
                            String fname = et_fname.getText().toString().trim();
                            String mname = et_mname.getText().toString().trim();
                            String suffix = et_suffix.getText().toString().trim();
                            String contact = et_contact.getText().toString().trim();
                            String addr = et_addr.getText().toString().trim();
                            String email = et_email.getText().toString().trim();
                            String username = et_username.getText().toString();
                            String bday = et_bdate.getText().toString().trim();


                            apiResponse.getData().setLname(lname);
                            apiResponse.getData().setFname(fname);
                            apiResponse.getData().setMname(mname);
                            apiResponse.getData().setSuffix(suffix);
                            apiResponse.getData().setContactNumber(contact);
                            apiResponse.getData().setResidenceAddress(addr);
                            apiResponse.getData().setEmail(email);
                            apiResponse.getData().setUsername(username);

                            Toast.makeText(UpdateProfActivity.this, apiResponse.getMsg()+"message", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UpdateProfActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

}
