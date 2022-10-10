package com.app.hotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.hotel.R;
import com.app.hotel.databinding.ActivityMainBinding;
import com.app.hotel.fragments.FavouritesFragment;
import com.app.hotel.fragments.HomeFragment;
import com.app.hotel.fragments.NotificationFragment;
import com.app.hotel.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    private Button button_login_register;


//    TextView textView;
//    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.notification:
                    replaceFragment(new NotificationFragment());
                    break;
                case R.id.favourites:
                    replaceFragment(new FavouritesFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flLayout, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public void onClick(View view) {
//        switch(view.getId()){
//            case R.id.button_login_register:
//                startActivity(new Intent(this,LoginActivity.class));
//                break;
//        }
    }
