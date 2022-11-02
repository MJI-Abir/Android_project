package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.hotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GuestBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    TextView value1,value2,value3;
    Button increment1,increment2,increment3,decrement1,decrement2,decrement3;
    Button done;
    int count = 0;

    public GuestBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_bottom_sheet, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        value1 = view.findViewById(R.id.val1);
        value2 = view.findViewById(R.id.val2);
        value3 = view.findViewById(R.id.val3);

        increment1 = view.findViewById(R.id.inc1);
        increment3=view.findViewById(R.id.inc3);
        increment2=view.findViewById(R.id.inc2);
        decrement1= view.findViewById(R.id.dec1);
        decrement2= view.findViewById(R.id.dec2);
        decrement3= view.findViewById(R.id.dec3);

        done = view.findViewById(R.id.apply);

        done.setOnClickListener(this);

        increment1.setOnClickListener(this);

        increment2.setOnClickListener(this);


        increment3.setOnClickListener(this);


        decrement1.setOnClickListener(this);


        decrement2.setOnClickListener(this);


        decrement3.setOnClickListener(this);


    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.apply:
                dismiss();
                break;
            case R.id.inc1:
                count++;
                value1.setText("" + count);
                break;
            case R.id.inc2:
                count++;
                value2.setText("" + count);
                break;
            case R.id.inc3:
                count++;
                value3.setText("" + count);
                break;
            case R.id.dec1:
                if (count <= 0) {
                    count = 0;
                } else count--;
                value1.setText("" + count);
                break;
            case R.id.dec2:
                if (count <= 0) {
                    count = 0;
                } else count--;
                value2.setText("" + count);
                break;
            case R.id.dec3:
                if (count <= 0) {
                    count = 0;
                } else count--;
                value3.setText("" + count);
                break;

        }
    }
}