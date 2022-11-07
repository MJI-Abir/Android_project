package com.app.hotel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hotel.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_DURATION=3000;
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash);

        image = findViewById(R.id.imageView2);
        logo = findViewById(R.id.bookpaltv);
        slogan = findViewById(R.id.slogan);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//Set animation to elements
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            // Attach all the elements those you want to animate in design
            Pair[]pairs=new Pair[2];pairs[0]=new Pair<View, String>(image,"logo_img_transition");pairs[1]=new Pair<View, String>(logo,"logo_text_transition");
            //wrap the call in API level 21 or higher
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
            startActivity(intent,options.toBundle());
            finish();
        },SPLASH_DURATION);
    }

}