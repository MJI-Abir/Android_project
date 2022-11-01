package com.app.hotel.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hotel.R;
import com.app.hotel.activities.LoginActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView nameTextView, numberTextView, emailTextView;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        nameTextView = view.findViewById(R.id.nameTextView);
        numberTextView = view.findViewById(R.id.numberTextView);
        emailTextView = view.findViewById(R.id.emailTextView);

        Button signOut = view.findViewById(R.id.signOut);
        signOut.setOnClickListener(this);

        String userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                nameTextView.setText(new StringBuilder().append(documentSnapshot.getString("First_Name"))
                        .append("  ").append(documentSnapshot.getString("Last_Name")).toString());
                numberTextView.setText(documentSnapshot.getString("phone"));
                emailTextView.setText(documentSnapshot.getString("email"));
            }
            else{
                Toast.makeText(getContext(),"Data not found",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show());

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(),"Logged out successfully!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
        }

    }
}
