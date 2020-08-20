package com.example.androidtest.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.androidtest.Constant;
import com.example.androidtest.R;
import com.example.androidtest.adapter.ArticlesAdpater;
import com.example.androidtest.model.ApiResponse;
import com.example.androidtest.model.articles.Articles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeActivity extends AppCompatActivity {

    TextView tv_mssg;
    String strMssg;
    SwipeRefreshLayout swipeRefreshLayout;
    String userToken;
    RecyclerView rv_articles;
    Gson gson;

    private ArticlesAdpater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Home");

        tv_mssg = findViewById(R.id.tv_mssg);
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        rv_articles = findViewById(R.id.rv_articles);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayMssg();
                Toast.makeText(HomeActivity.this, "Refreshed.", Toast.LENGTH_SHORT).show();
            }
        });

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson =gsonBuilder.create();

        //recyclerview
        rv_articles.setHasFixedSize(true);
        rv_articles.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        displayMssg();

        try {
            displayArticles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayMssg() {
        //getting the mssg data from MainActivity
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strMssg = bundle.getString("mssg");
            tv_mssg.setText(strMssg);
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_my_profile:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //do logout

                try {
                    run();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();


    }

    @Override
    public void onBackPressed() {
        //disable onbackpressed
        super.onBackPressed();
    }

    void displayArticles() throws IOException {

        SharedPreferences userTokenPref = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = (userTokenPref.getString("token",""));


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody body1 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("page", "1")
                .addFormDataPart("per_page", "10")
                .build();

        Request request1 = new Request.Builder()
                .url("http://internal.cebu.cityserv.ph/api/article/all.json")
                .method("POST", body1)
                .addHeader("Authorization", "Bearer "+userToken)
                .build();


        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonResponse = response.body().string();

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Articles articles = gson.fromJson(jsonResponse, Articles.class);
                        Log.i("JSON_RES", jsonResponse);
                        Log.i("MESSAGE", articles.getMsg());
                        Log.i("ARTICLES", articles.toString());

                        Toast.makeText(HomeActivity.this, articles.getMsg()+"\n"+articles.toString(), Toast.LENGTH_LONG).show();


                    }
                });
            }
        });

    }

    void run() throws IOException {

        SharedPreferences userTokenPref = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
        userToken = (userTokenPref.getString("token",""));

        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "")
                .build();

        final Request request = new Request.Builder()
                .url(Constant.USER_LOGOUT_API)
                .method("POST", body)
                .addHeader("Authorization", "Bearer"+userToken)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonData = response.body().string();

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ApiResponse apiResponse = gson.fromJson(jsonData, ApiResponse.class);

                        Toast.makeText(HomeActivity.this, apiResponse.getMsg(), Toast.LENGTH_SHORT).show();

                        Log.i("HOMEACTIVITY", jsonData);

                        //remove saved shared preferences
                        SharedPreferences userTokenPref = getApplicationContext().getSharedPreferences("UserToken", MODE_PRIVATE);
                        SharedPreferences.Editor editor = userTokenPref.edit();

                        editor.remove("token");
                        editor.remove("id");
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                });

            }
        });
    }
}
