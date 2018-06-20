package org.samtech.earthquaketest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.samtech.earthquaketest.MainActivity;
import org.samtech.earthquaketest.R;

public class SplashActivity extends AppCompatActivity {
    public static final int SPLASH_TIME = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handlerActivity();
        setContentView(R.layout.act_splash);
    }

    private void handlerActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }
}