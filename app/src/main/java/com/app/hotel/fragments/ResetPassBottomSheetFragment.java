package com.app.hotel.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.hotel.R;
import com.app.hotel.activities.LoginActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {


    private EditText etEmailInReset;
    private FirebaseAuth mAuth;
    public ResetPassBottomSheetFragment() {
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
        return inflater.inflate(R.layout.fragment_reset_pass_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmailInReset = view.findViewById(R.id.etEmailInReset);

        Button resetPasswordButton = view.findViewById(R.id.resetPasswordButton);
        resetPasswordButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        resetPassword();
    }

    private void resetPassword() {

        String email = etEmailInReset.getText().toString().trim();

        if (email.isEmpty()) {
            etEmailInReset.setError("Enter Your Email Address");
            etEmailInReset.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailInReset.setError("Provide a Valid Email Address");
            etEmailInReset.requestFocus();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait...");
        pd.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                Toast.makeText(getContext(),"Check email to reset your password",Toast.LENGTH_SHORT).show();
                dismiss();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }

            else{
                Toast.makeText(getContext(),"This email is not ",Toast.LENGTH_LONG).show();
            }

            pd.dismiss();

        });
    }
}