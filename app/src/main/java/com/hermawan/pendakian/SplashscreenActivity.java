package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.hermawan.pendakian.preference.AppPreference;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        int loadingTime = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreference.getUser(SplashscreenActivity.this) != null) {
                    if (AppPreference.getUser(SplashscreenActivity.this).roleUser.equalsIgnoreCase("user")){
                        startActivity(new Intent(SplashscreenActivity.this, UserMainMenuActivity.class));
                    } else {
                        startActivity(new Intent(SplashscreenActivity.this, AdminMainMenuActivity.class));
                    }
                    finish();
                } else {
                    startActivity(new Intent(SplashscreenActivity.this, SignInActivity.class));
                    finish();
                }
            }
        }, loadingTime);
    }
}
