package org.samtech.earthquaketest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            assert connectivityManager != null;
            return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo
                            (ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        } catch (Exception e) {
            ConnectivityManager CManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert CManager != null;
            NetworkInfo NInfo = CManager.getActiveNetworkInfo();
            return NInfo != null && NInfo.isConnected() && NInfo.isAvailable();
        }
    }


}
