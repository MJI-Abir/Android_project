package com.app.hotel.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText editTextfName, editTextlname, editTextMobile, editTextemail, editTextPassword,
            editTextConfirmPassword;
    private FirebaseFirestore fstore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Sign up");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        editTextfName = findViewById(R.id.editTextfName);
        editTextlname = findViewById(R.id.editTextlName);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextemail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(this);

        Button loginButtonFromRegisterLayout = findViewById(R.id.loginButtonFromRegisterLayout);
        loginButtonFromRegisterLayout.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                createAccount();
                return;
            case R.id.loginButtonFromRegisterLayout:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }

    private void createAccount() {

        String fname, lname, password, email, mobile, confirmPassword;

        fname = editTextfName.getText().toString().trim();
        lname = editTextlname.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        email = editTextemail.getText().toString().trim();
        mobile = editTextMobile.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (fname.isEmpty()) {
            editTextfName.setError("Enter a First Name");
            editTextfName.requestFocus();
            return;
        }

        if (lname.isEmpty()) {
            editTextlname.setError("Enter a Last Name");
            editTextlname.requestFocus();
            return;
        }

        if (mobile.isEmpty()) {
            editTextMobile.setError("Enter a Phone Number");
            editTextMobile.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextemail.setError("Enter Your Email Address");
            editTextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Provide a Valid Email Address");
            editTextemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Enter a Password");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must contain at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)) {
            editTextConfirmPassword.setError("The password confirmation does not match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("Please wait...");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        assert firebaseUser != null;

                        firebaseUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Check email to verify your account", Toast.LENGTH_LONG).show();
                                //                        redirect to login page
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                // email not sent, so display message and restart the activity or do whatever you wish to do
                                Toast.makeText(RegisterActivity.this, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                //restart this activity
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                            }
                        });

                        userID = mAuth.getCurrentUser().getUid();

                        DocumentReference documentReference = fstore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("First_Name", fname);
                        user.put("Last_Name", lname);
                        user.put("email", email);
                        user.put("phone", mobile);

                        documentReference.set(user).addOnSuccessListener(unused ->
                                        Log.d(TAG, "user profile is created for " + userID))
                                .addOnFailureListener(e ->
                                        Log.d(TAG, e.toString()));


                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    pd.dismiss();

                });
    }

}