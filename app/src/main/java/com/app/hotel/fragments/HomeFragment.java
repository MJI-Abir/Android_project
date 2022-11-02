package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.hotel.R;
import com.app.hotel.activities.MapsActivity;
import com.app.hotel.activities.NetworkChangeListener;

import java.util.Calendar;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private TextView datepicker;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener setListener;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

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

//        @Override
//        public void onStart() {
//
//            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//            registerReceiver(networkChangeListener,intentFilter);
//            super.onStart();
//        }
//
//        public void onStop() {
//            unregisterReceiver(networkChangeListener);
//            super.onStop();
//        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //------------------------------select guests------------------------//
            case R.id.guest:
                GuestBottomSheetFragment bottomSheetFragment = new GuestBottomSheetFragment();
                bottomSheetFragment.show(getParentFragmentManager(),bottomSheetFragment.getTag());
                break;

                //--------------------------------map search----------------------//
            case R.id.search:
                startActivity(new Intent(getContext(), MapsActivity.class));
                break;

                //--------------------------------datepicker dialog -----------------------//
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








