package com.example.androidtest.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidtest.helper.Constant;
import com.example.androidtest.R;
import com.example.androidtest.model.ApiResponse;
import com.example.androidtest.model.Payload;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_mssg, tv_usertype, tv_name, tv_bdate, tv_age, tv_cvstatus, tv_addr, tv_email,
                tv_createDate, tv_lastLoggedin, tv_iden_off_id, tv_iden_contact, tv_iden_name,
                tv_iden_bdate, tv_iden_age, tv_iden_cvstat, tv_iden_uname, tv_iden_email,
                tv_iden_residence, tv_iden_permanent_addr, tv_iden_create_date, tv_update_prof,
                tv_update_passw, tv_contact, tv_imagename;

    ImageView iv_thumbnail;

    Button btnUpload;

    ShimmerFrameLayout shimmerTitle;

    Handler handler;

    Gson gson;

    String userToken;
    String lname, fname, mname, suffix, bdate, uname, email, contact, residenceAddr, passw;

    private static final int PICK_IMAGE = 100;

    private RequestOptions options;

    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("View Profile");

        tv_mssg = findViewById(R.id.tv_mssg);
        tv_usertype = findViewById(R.id.tv_usertype);
        tv_name = findViewById(R.id.tv_name);
        tv_bdate = findViewById(R.id.tv_bdate);
        tv_age = findViewById(R.id.tv_age);
        tv_cvstatus = findViewById(R.id.tv_cvstat);
        tv_addr = findViewById(R.id.tv_addr);
        tv_email = findViewById(R.id.tv_email);
        tv_createDate = findViewById(R.id.tv_creation_date);
        tv_lastLoggedin = findViewById(R.id.tv_last_login);
        tv_iden_off_id = findViewById(R.id.tv_iden_id);
        tv_iden_contact = findViewById(R.id.tv_iden_ctcnum);
        tv_iden_name = findViewById(R.id.tv_iden_name);
        tv_iden_bdate = findViewById(R.id.tv_iden_bdate);
        tv_iden_age = findViewById(R.id.tv_iden_age);
        tv_iden_cvstat = findViewById(R.id.tv_iden_cvstat);
        tv_iden_uname = findViewById(R.id.tv_iden_uname);
        tv_iden_email = findViewById(R.id.tv_iden_email);
        tv_iden_residence = findViewById(R.id.tv_iden_radd);
        tv_iden_permanent_addr = findViewById(R.id.tv_iden_padd);
        tv_iden_create_date = findViewById(R.id.tv_iden_createdate);
        tv_update_prof = findViewById(R.id.tv_update_prof);
        tv_update_passw = findViewById(R.id.tv_update_passw);
        tv_contact = findViewById(R.id.tv_contact);
        iv_thumbnail = findViewById(R.id.iv_photo);
        btnUpload = findViewById(R.id.btn_upload);
        tv_imagename = findViewById(R.id.tv_image_name);



        options = new RequestOptions().centerCrop().placeholder(R.drawable.custom_loading_image).error(R.drawable.custom_loading_image);

//        //shimmers
//        shimmerTitle = findViewById(R.id.shimmer_title);
//
//        handler = new Handler();
//
//        //starting the shimmers
//        shimmerTitle.startShimmerAnimation();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                shimmerTitle.stopShimmerAnimation();
//                shimmerTitle.setVisibility(View.GONE);
//
//            }
//        }, 4000);

        tv_update_prof.setOnClickListener(this);
        tv_update_passw.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        iv_thumbnail.setOnClickListener(this);


        try {
            run();
        }catch (Exception e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson =gsonBuilder.create();
    }

    void run() throws IOException {
        //getting the saved token from Shared Preferences
        SharedPreferences userTokenPref = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = (userTokenPref.getString("token",""));


        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("include", "identification")
                .build();
        final Request request = new Request.Builder()
//                .url("http://internal.cebu.cityserv.ph/api/citizen/profile/show.json")
                .url(Constant.USER_PROFILE_API_URL)
                .method("POST", body)
                .addHeader("Authorization", "Bearer "+userToken)
//                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9pbnRlcm5hbC5jZWJ1LmNpdHlzZXJ2LnBoXC9hcGlcL2NpdGl6ZW5cL2xvZ2luLmpzb24iLCJpYXQiOjE1OTc4NTE1MjgsImV4cCI6MTU5Nzg1NTEyOCwibmJmIjoxNTk3ODUxNTI4LCJqdGkiOiJCclJFa3FkZHVqb1JURkV2Iiwic3ViIjo1NiwicHJ2IjoiZTAyYTFjOTJiZmVmNWM3MGM4YTIyMzQyNDc0ZjAzZWE0YzMwNjU2OCJ9.csDeT98rMnkuw_eb6egdEls3pe-_Cgk8I6ak42f--Xw")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                final String jsonData = response.body().string();
                final String code = String.valueOf(response.code());
                final Response responses = null;


                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ApiResponse apiResponse = gson.fromJson(jsonData, ApiResponse.class);
                        Payload payload = gson.fromJson(jsonData, Payload.class);

                        //getting data from ApiResponse object

                        String mssg = apiResponse.getMsg();
                        lname = apiResponse.getData().getLname();
                        fname = apiResponse.getData().getFname();
                        String userType = apiResponse.getData().getUserType();
                        mname = apiResponse.getData().getMname();
                        suffix = apiResponse.getData().getSuffix();
                        String withId = apiResponse.getData().getWithId();
                        bdate = apiResponse.getData().getBirthdate();
                        String age = apiResponse.getData().getAge();
                        String civilStat = apiResponse.getData().getCivilStatus();
                        String name = apiResponse.getData().getName();
                        uname = apiResponse.getData().getUsername();
                        email = apiResponse.getData().getEmail();
                        residenceAddr = apiResponse.getData().getResidenceAddress();
                        contact = apiResponse.getData().getContactNumber();


                        passw = payload.getPassw();

                        String thumbnailUrl = apiResponse.getData().getAvatar().getThumbPath();


                        String createDate = apiResponse.getData().getDateCreated().getDateDb();
                        String lastLoggedin = apiResponse.getData().getLastLogin().getDateDb();


                        //setting it to views

//                        tv_mssg.setText(mssg);
                        tv_usertype.setText(userType);
                        tv_name.setText(name);
                        tv_bdate.setText(bdate);
                        tv_age.setText(age);
                        tv_cvstatus.setText(civilStat);
                        tv_addr.setText(residenceAddr);
                        tv_email.setText(email);
                        tv_createDate.setText(createDate);
                        tv_lastLoggedin.setText(lastLoggedin);
                        tv_contact.setText(contact);

                        Glide.with(ProfileActivity.this).load(thumbnailUrl).into(iv_thumbnail);

                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.tv_update_prof:
                updateUserProfile();
                break;
            case R.id.tv_update_passw:
                updateUserPassw();
                break;
            case R.id.iv_photo:
                uploadImage();
                break;
            case R.id.btn_upload:

                break;
        }
    }



    private void uploadImage() {
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            iv_thumbnail.setImageURI(imageUri);

        } else {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            iv_thumbnail.setImageBitmap(bitmap);
        }


    }

    private void updateUserPassw() {
        Intent intent = new Intent(ProfileActivity.this, UpdatePasswActivity.class);
        intent.putExtra("passw", passw);
        startActivity(intent);

//        Toast.makeText(this, passw+" is your passw", Toast.LENGTH_SHORT).show();
    }

    private void updateUserProfile() {

        //passing the token to UpdateProfile activity
        Intent intent = new Intent(ProfileActivity.this, UpdateProfActivity.class);
        intent.putExtra("user_token", userToken);
        intent.putExtra("fname", fname);
        intent.putExtra("mname", mname);
        intent.putExtra("lname", lname);
        intent.putExtra("suffix", suffix);
        intent.putExtra("email", email);
        intent.putExtra("username", uname);
        intent.putExtra("contact", contact);
        intent.putExtra("residence", residenceAddr);
        intent.putExtra("bdate", bdate);
        startActivity(intent);

    }
}
