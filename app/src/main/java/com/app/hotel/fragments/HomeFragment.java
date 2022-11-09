package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.app.hotel.R;
import com.app.hotel.activities.HotelActivity;
import com.app.hotel.activities.MapsActivity;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public TextView dateRangePicker, guest;
    MaterialDatePicker materialDatePicker;
    Button searchButton;

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

        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();

        dateRangePicker = view.findViewById(R.id.dateRangePicker);
        dateRangePicker.setOnClickListener(this);

        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        guest = view.findViewById(R.id.guest);
        guest.setOnClickListener(this);



        TextView search = view.findViewById(R.id.search);
        search.setOnClickListener(this);

        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());

        materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setCalendarConstraints(constraintsBuilder.build()).
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())).build();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //------------------------------select guests------------------------//
            case R.id.guest:



                GuestBottomSheetFragment bottomSheetFragment = new GuestBottomSheetFragment();
                bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());

                Bundle bundle = new Bundle();
                String str = bundle.getString("val1");
                guest.setText(str);
//
//                Intent intent = getActivity().getIntent();
//                String str = intent.getStringExtra("val1");
//                guest.setText(str);
////                Bundle bundle = getActivity().getIntent().getExtras();
////                String str = bundle.getString("message");
////                guest.setText(str);
                break;

            //--------------------------------map search----------------------//
            case R.id.search:
                startActivity(new Intent(getContext(),MapsActivity.class));
                break;

            //--------------------------------datepicker dialog -----------------------//
            case R.id.dateRangePicker:
                materialDatePicker.show(getParentFragmentManager(), "tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(selection ->
                        dateRangePicker.setText(materialDatePicker.getHeaderText()));
                break;
            case R.id.searchButton:
                startActivity(new Intent(getContext(), HotelActivity.class));
                break;
        }
    }

}








