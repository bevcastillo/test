package com.example.androidtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_container, new ArticlesFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment selectedFragment = null;

        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_news:
                selectedFragment = new ArticlesFragment();
                break;
            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, selectedFragment).commit();

        return true;
    }
}
