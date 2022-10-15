package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hotel.R;
import com.app.hotel.activities.GuestActivity;
import com.app.hotel.activities.LoginActivity;
import com.app.hotel.activities.MainActivity;

import java.util.Calendar;

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
        Button button_login_register = (Button) view.findViewById(R.id.button_login_register);
        button_login_register.setOnClickListener(this);
        datepicker = (TextView) view.findViewById(R.id.datepicker);
        datepicker.setOnClickListener(this);

        TextView guest = (TextView) view.findViewById(R.id.guest);
        guest.setOnClickListener(this);

        Calendar calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);

        setListener = (view1, year, month, dayofmonth) -> {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            datepicker.setText(date);
        };

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login_register:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;

            case R.id.guest:
                Toast.makeText(getContext(), "hello!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(),GuestActivity.class));
                break;

            case R.id.datepicker:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                break;
        }

    }

}