package com.app.hotel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.hotel.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);
    }

}

/* ---------------------------------Shoeb's Code for splashActivity-------------------------------*/
//package com.app.hotel;
//
//        import static java.lang.Thread.sleep;
//
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//
//public class splash_activity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        Thread thread=new Thread() {
//            public void run() {
//                try {
//                    sleep(3000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    Intent intent = new Intent(com.app.hotel.splash_activity.this, MainActivity.class);
//                }
//            }
//        };
//
//        thread.start();
//    }
//}