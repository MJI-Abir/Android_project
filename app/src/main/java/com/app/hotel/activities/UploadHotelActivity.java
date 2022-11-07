package com.app.hotel.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.hotel.R;
import com.app.hotel.viewModels.Hotel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class UploadHotelActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    //    private TextView mTextViewShowUploads;
    private EditText hotel_name, hotel_price, hotel_location;
    private ImageView image_view;
    private ProgressBar progress_bar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hotel);

        FloatingActionButton button_choose_image = findViewById(R.id.button_choose_image);
        Button button_upload = findViewById(R.id.button_upload);
//        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);

        hotel_name = findViewById(R.id.hotel_name);
        image_view = findViewById(R.id.image_view);
        hotel_price = findViewById(R.id.hotel_price);
        hotel_location = findViewById(R.id.hotel_location);

        progress_bar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        button_choose_image.setOnClickListener(v ->
                openFileChooser());

        button_upload.setOnClickListener(v -> {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(UploadHotelActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        });

//        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(image_view);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        String hotelName = hotel_name.getText().toString().trim();
        String hotelLocation = hotel_location.getText().toString().trim();
        String hotelPrice = hotel_price.getText().toString().trim();
        if (hotelName.isEmpty()) {
            hotel_name.setError("Enter Hotel Name");
            hotel_name.requestFocus();
            return;
        }
        if (hotelLocation.isEmpty()) {
            hotel_location.setError("Enter Hotel Location");
            hotel_location.requestFocus();
            return;
        }
        if (hotelPrice.isEmpty()) {
            hotel_price.setError("Enter room price per day");
            hotel_price.requestFocus();
            return;
        }
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> progress_bar.setProgress(0), 500);

                        Toast.makeText(UploadHotelActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Uri downloadUrl = uri;
                                Hotel hotel = new Hotel(hotel_name.getText().toString().trim(),
                                        downloadUrl.toString(), hotel_price.getText().toString().trim(), hotel_location.getText().toString().trim());
                                String uploadId = mDatabaseRef.push().getKey();
                                assert uploadId != null;
                                mDatabaseRef.child(uploadId).setValue(hotel);
                            }
                        });
                    }).addOnFailureListener(e -> Toast.makeText(UploadHotelActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progress_bar.setProgress((int) progress);
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

//    private void openImagesActivity() {
//        Toast.makeText(this, "upload successful!", Toast.LENGTH_SHORT).show();
//       // Intent intent = new Intent(this, MainActivity.class);
//     //   startActivity(intent);
//    }
}