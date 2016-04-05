package com.example.kj.myapplication.core.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

final public class NetworkUtils {
    Context mContext;

    public NetworkUtils(Context context) {
        mContext = context;
    }

    public boolean hasConnection()
    {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }

        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }

        return false;
    }
}
