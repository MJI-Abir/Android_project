package com.app.hotel.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.NetworkInterface;

public class Connection {
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            for(int i=0; i<info.length; i++){
                if(info[i].getState() == NetworkInfo.State.CONNECTED) return true;
            }
        }
        return false;
    }
}
