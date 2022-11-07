package com.app.hotel.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.hotel.R;
import com.app.hotel.fragments.HomeFragment;

public class HotelDetailsActivity extends AppCompatActivity {

    TextView range;
    HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

//        range = findViewById(R.id.range);
//        range.setText(homeFragment.dateRangePicker.getText());


    }
}