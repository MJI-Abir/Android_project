package com.app.hotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.hotel.R;
import com.app.hotel.activities.HotelDetailsActivity;
import com.app.hotel.viewModels.Hotel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> implements Filterable {
    private final Context mContext;
    ArrayList<Hotel> hotels;
    ArrayList<Hotel> hotelsFull;

//    private OnItemClickListener mListener;

    public HotelAdapter(Context context, ArrayList<Hotel> uploads) {
        mContext = context;
        hotels = uploads;
        this.hotelsFull = new ArrayList<>(hotels);
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.hotel, parent, false);
        return new HotelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        Hotel uploadCurrent = hotels.get(position);
        holder.hotelName.setText(uploadCurrent.getName());
        holder.hotelLocation.setText(uploadCurrent.getLocation());
        holder.hotelPrice.setText("BDT " + uploadCurrent.getPrice() + "/DAY");
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.hotelImage);

        holder.itemView.setOnClickListener(v ->
                mContext.startActivity(new Intent(mContext,HotelDetailsActivity.class)));
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    @Override
    public Filter getFilter() {
        return hotelFilter;
    }

    private Filter hotelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Hotel> filteredHotelList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredHotelList.addAll(hotelsFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Hotel hotel : hotelsFull){
                    if(hotel.getName().toLowerCase().contains(filterPattern)){
                        filteredHotelList.add(hotel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredHotelList;
            filterResults.count = filteredHotelList.size();
            return filterResults;
        }

//        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            hotels.clear();
            hotels.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        public TextView hotelName, hotelLocation,hotelPrice;
        public ImageView hotelImage;
        public FloatingActionButton favFab;

        public HotelViewHolder(View itemView) {
            super(itemView);

            hotelName = itemView.findViewById(R.id.hotelName);
            hotelPrice = itemView.findViewById(R.id.hotelPrice);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelImage = itemView.findViewById(R.id.hotelImage);
        }


    }


//    @Override
//    public void onClick(View v) {
//        if (mListener != null) {
//            int position = getAbsoluteAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                mListener.onItemClick(position);
//            }
//        }
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        menu.setHeaderTitle("Select Action");
//        MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
//        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
//
//        doWhatever.setOnMenuItemClickListener(this);
//        delete.setOnMenuItemClickListener(this);
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        if (mListener != null) {
//            int position = getAbsoluteAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//
//                switch (item.getItemId()) {
//                    case 1:
//                        mListener.onWhatEverClick(position);
//                        return true;
//                    case 2:
//                        mListener.onDeleteClick(position);
//                        return true;
//                }
//            }
//        }
//        return false;
//    }
}

//public interface OnItemClickListener {
//    void onItemClick(int position);
//
//    void onWhatEverClick(int position);
//
//    void onDeleteClick(int position);
//}
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mListener = listener;
//    }
//}
//}

