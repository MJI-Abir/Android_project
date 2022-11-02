package com.app.hotel.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.app.hotel.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Connection.isConnectedToInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog,null);
            builder.setView(layout_dialog);

            AppCompatButton retryBtn = layout_dialog.findViewById(R.id.retryBtn);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(true);

            dialog.getWindow().setGravity(Gravity.CENTER);

            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
}
