package com.example.mysangeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        getSupportActionBar().hide();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this,HomeActivity.class);
            startActivity(intent);
            finish();
        },800);
    }
}