package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.hotel.R;
import com.app.hotel.activities.GuestActivity;
import com.app.hotel.activities.LoginActivity;
import com.app.hotel.activities.MapsActivity;

import java.util.Calendar;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private TextView datepicker;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener setListener;

    public HomeFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //         Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((AppCompatActivity)getActivity()).getSupportActionBar()).hide();


        datepicker = view.findViewById(R.id.datepicker);
        datepicker.setOnClickListener(this);

        TextView guest = (TextView) view.findViewById(R.id.guest);
        guest.setOnClickListener(this);

        TextView search = (TextView) view.findViewById(R.id.search);
        search.setOnClickListener(this);

        Calendar calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.guest:
                startActivity(new Intent(getContext(), GuestActivity.class));
                break;

            case R.id.search:
                startActivity(new Intent(getContext(), MapsActivity.class));
                break;

            case R.id.datepicker:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, month, day) -> {
                    month = month + 1;
                    String date = day + "/" + month + "/" + year;
                    datepicker.setText(date);
                }, year, month, day);

                datePickerDialog.show();
                break;
        }
    }

}








