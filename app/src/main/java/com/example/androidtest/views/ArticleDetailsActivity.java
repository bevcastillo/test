package com.example.androidtest.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidtest.R;

import java.security.PrivateKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ArticleDetailsActivity extends AppCompatActivity {

    TextView tv_title, tv_date_time, tv_content;
    String strTitle, strDate, strTime, strContent, strThumbnail;
    ImageView iv_thumbnail;
    RequestOptions requestOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        setTitle("Article Details");

        tv_title = findViewById(R.id.tv_title);
        tv_date_time = findViewById(R.id.tv_date_time);
        tv_content = findViewById(R.id.tv_content);
        iv_thumbnail = findViewById(R.id.iv_thumbnail);

        requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.custom_circle_image).error(R.drawable.custom_circle_image);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strTitle = bundle.getString("article_title");
            strDate = bundle.getString("article_date");
            strTime = bundle.getString("article_tStamp");
            strContent = bundle.getString("article_content");
            strThumbnail = bundle.getString("article_fullImage");

            tv_title.setText(strTitle);
            tv_date_time.setText(strTime);
            tv_content.setText(strContent);

            Glide.with(ArticleDetailsActivity.this).load(strThumbnail).into(iv_thumbnail);
        }
    }
}
