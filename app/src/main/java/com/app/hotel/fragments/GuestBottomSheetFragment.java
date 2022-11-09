package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.hotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GuestBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    TextView value1, value2, value3;
    Button increment1, increment2, increment3, decrement1, decrement2, decrement3;
    Button done;
    int count1 = 0, count2 = 0, count3 = 0;
    private HomeFragment HomeFragment;

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

        HomeFragment homeFragment = new HomeFragment();

        value1 = view.findViewById(R.id.val1);
        value2 = view.findViewById(R.id.val2);
        value3 = view.findViewById(R.id.val3);

        increment1 = view.findViewById(R.id.inc1);
        increment3 = view.findViewById(R.id.inc3);
        increment2 = view.findViewById(R.id.inc2);
        decrement1 = view.findViewById(R.id.dec1);
        decrement2 = view.findViewById(R.id.dec2);
        decrement3 = view.findViewById(R.id.dec3);

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

        switch (view.getId()) {

            case R.id.inc1:
                count1++;
                value1.setText("" + count1);
                break;
            case R.id.inc2:
                count2++;
                value2.setText("" + count2);
                break;
            case R.id.inc3:
                count3++;
                value3.setText("" + count3);
                break;
            case R.id.dec1:
                if (count1 <= 0) {
                    count1 = 0;
                } else count1--;
                value1.setText("" + count1);
                break;
            case R.id.dec2:
                if (count2 <= 0) {
                    count2 = 0;
                } else count2--;
                value2.setText("" + count2);
                break;
            case R.id.dec3:
                if (count3 <= 0) {
                    count3 = 0;
                } else count3--;
                value3.setText("" + count3);
                break;

            case R.id.apply:
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra("val1", str);
//                startActivity(intent);

                Bundle bundle = new Bundle();
                String str = value1.getText().toString();
                bundle.putString("val1", str);
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flLayout,homeFragment).commit();
//                getActivity().getSupportFragmentManager().beginTransaction().commit();
                dismiss();
                break;

        }
    }
}