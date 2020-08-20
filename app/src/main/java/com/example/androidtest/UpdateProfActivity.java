package com.example.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidtest.model.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.POST;

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

//        try {
//            run();
//        }catch (Exception e) {
//
//        }

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
                Toast.makeText(this, et_bdate.getText().toString()+" is your bdate!!", Toast.LENGTH_SHORT).show();
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


//    void run() throws IOException {
//
//        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .build();
//
//        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("include", "identification")
//                .build();
//
//        final Request request = new Request.Builder()
//                .url(Constant.EDIT_USER_PROFILE_API_URL)
//                .method("POST", requestBody)
//                .addHeader("Authorization", "Bearer"+strToken)
//                .build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                final String jsonData = response.body().string();
//
//                UpdateProfActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ApiResponse apiResponse = gson.fromJson(jsonData, ApiResponse.class);
//
//
//                        //getting data
//                        // firstname, middle name, lastname, suffix, username, email, bdate, contact, residence
//
//                        String fname = apiResponse.getData().getFname();
//                        String mname = apiResponse.getData().getMname();
//                        String lname = apiResponse.getData().getLname();
//                        String suffix = apiResponse.getData().getSuffix();
//                        String username = apiResponse.getData().getUsername();
//                        String email = apiResponse.getData().getEmail();
//                        String bdate = apiResponse.getData().getBirthdate();
//                        String contact = apiResponse.getData().getContactNumber();
//                        String residence = apiResponse.getData().getResidenceAddress();
//
////                        et_fname.setText(fname);
//////                        et_mname.setText(mname);
//////                        et_lname.setText(lname);
//////                        et_suffix.setText(suffix);
//////                        et_username.setText(username);
//////                        et_email.setText(email);
//////                        et_bdate.setText(bdate);
//////                        et_contact.setText(contact);
//////                        et_addr.setText(residence);
//
//                        Toast.makeText(UpdateProfActivity.this, fname.toString()+" is your lastname", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        });
//    }

}
