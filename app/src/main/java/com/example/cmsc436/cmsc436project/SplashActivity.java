package com.example.cmsc436.cmsc436project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (password.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), EnterPasswordActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

}
