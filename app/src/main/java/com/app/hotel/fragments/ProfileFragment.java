package com.app.hotel.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.hotel.R;
import com.app.hotel.activities.LoginActivity;
import com.app.hotel.activities.UploadHotelActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView nameTextView, numberTextView, emailTextView;
    ShimmerFrameLayout shimmerFrameLayout;
    private View layoutProfile;
    private FloatingActionButton addPhotoButton;
    private final int GALLERY_REQ_CODE = 1000;
    private ImageView imgGallery;
    StorageReference storageReference;

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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");


        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        nameTextView = view.findViewById(R.id.nameTextView);
        numberTextView = view.findViewById(R.id.numberTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        layoutProfile = view.findViewById(R.id.layoutProfile);

        addPhotoButton = view.findViewById(R.id.addPhotoButton);
        addPhotoButton.setOnClickListener(this);

        imgGallery = view.findViewById(R.id.imgGallery);

        Button signOut = view.findViewById(R.id.signOut);
        signOut.setOnClickListener(this);

        Button switchHost = view.findViewById(R.id.swithHost);
        switchHost.setOnClickListener(this);

        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();

        findUser();

    }

    private void findUser() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                layoutProfile.setVisibility(View.VISIBLE);

                nameTextView.setText(new StringBuilder().append(documentSnapshot.getString("First_Name"))
                        .append("  ").append(documentSnapshot.getString("Last_Name")).toString());
                numberTextView.setText(documentSnapshot.getString("phone"));
                emailTextView.setText(documentSnapshot.getString("email"));
            } else {
                Toast.makeText(getContext(), "Data not found", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logged out successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.addPhotoButton:
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_REQ_CODE);
//                addProfilePhoto();
                break;

            case R.id.swithHost:
                startActivity(new Intent(getContext(), UploadHotelActivity.class));
                break;
        }

    }

//    private void addProfilePhoto() {
//
//
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                Uri imageUri = data.getData();
                imgGallery.setImageURI(imageUri);
                uploadImageFirebase(imageUri);
//                Bitmap imgBitmap = (Bitmap) (data.getExtras().get("data"));
//                imgGallery.setImageBitmap(imgBitmap);
            }
        }
    }

    private void uploadImageFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(getContext(),"Image uploaded.", Toast.LENGTH_LONG).show();
            fileRef.getDownloadUrl().addOnSuccessListener(uri ->
                    Picasso.get().load(uri).into(imgGallery));
        }).addOnFailureListener(e ->
                Toast.makeText(getContext(),"Failed to upload image.", Toast.LENGTH_LONG).show());
    }
}
