package com.example.androidtest.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.androidtest.R;

public class HomeActivity extends AppCompatActivity {

    TextView tv_mssg;
    String strMssg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_mssg = findViewById(R.id.tv_mssg);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //getting the mssg data from MainActivity
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strMssg = bundle.getString("mssg");
            tv_mssg.setText(strMssg);
        }

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
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
