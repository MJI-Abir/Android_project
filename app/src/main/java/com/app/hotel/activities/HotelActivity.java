package com.app.hotel.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hotel.R;
import com.app.hotel.adapters.HotelAdapter;
import com.app.hotel.viewModels.Hotel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Objects;

public class HotelActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HotelAdapter mAdapter;

    private ProgressBar mProgressCircle;
    ShimmerFrameLayout shimmerFrameLayout;

    FirebaseStorage mStorage;

    private ArrayList<Hotel> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();

        shimmerFrameLayout = findViewById(R.id.shimmerHotel);
        shimmerFrameLayout.startShimmer();

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Hotel upload = postSnapshot.getValue(Hotel.class);
                    mUploads.add(upload);

                }

                mAdapter = new HotelAdapter(HotelActivity.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

//                shimmerFrameLayout.stopShimmer();

//                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HotelActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        //                mProgressCircle.setVisibility(View.INVISIBLE);
        //                mProgressCircle.setVisibility(View.INVISIBLE);
        ValueEventListener mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Hotel upload = postSnapshot.getValue(Hotel.class);
                    Objects.requireNonNull(upload).setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();



//                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HotelActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

//


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotel,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


//    @Override
//        public void onItemClick(int position) {
//            Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onWhatEverClick(int position) {
//            Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onDeleteClick(int position) {
//            Hotel selectedItem = mUploads.get(position);
//            final String selectedKey = selectedItem.getKey();
//
//            StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
//            imageRef.delete().addOnSuccessListener(aVoid -> {
//                mDatabaseRef.child(selectedKey).removeValue();
//                Toast.makeText(HotelActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
//            });
//        }
//
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            mDatabaseRef.removeEventListener(mDBListener);
//        }


}