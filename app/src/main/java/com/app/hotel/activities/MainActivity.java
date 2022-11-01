package com.app.hotel.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.hotel.R;
import com.app.hotel.databinding.ActivityMainBinding;
import com.app.hotel.fragments.FavouritesFragment;
import com.app.hotel.fragments.HomeFragment;
//import com.app.hotel.fragments.ProfileFragment;
import com.app.hotel.fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        if(FirebaseAuth.getInstance().getCurrentUser()==null)
//        {
//            startActivity(new Intent(MainActivity.this,LoginActivity.class));
////            finish();
//        }
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.favourites:
                    replaceFragment(new FavouritesFragment());
                    break;
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
}