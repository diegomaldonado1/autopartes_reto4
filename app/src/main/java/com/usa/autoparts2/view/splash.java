package com.usa.autoparts2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.usa.autoparts2.R;

public class splash extends AppCompatActivity {
    private final int Time = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, Introduccion.class );
                startActivity(intent);
                finish();
            }
        }, Time );
    }
}