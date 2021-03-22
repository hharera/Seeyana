package com.example.n_one.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.n_one.R;
import com.google.android.material.snackbar.Snackbar;

public class NetworkUtil {
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_WIFI = 1;
    public static final int NETWORK_STATUS_MOBILE = 2;

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true;
                } else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            } else {
                return false;
            }
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                } else return activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET;


            } else {
                return false;
            }

        }

    }

//    public static void networkSnackbar(View v, Context packageContext, OnInternetStateListener listener) {
//        if (!isInternetConnected(packageContext)) {
//            noInternetSnackbar(v, packageContext, listener);
//        } else {
//            listener.doAfterInternetIsConnected();
//        }
//    }

//    public static void noInternetSnackbar(final View v, final Context packageContext, final OnInternetStateListener listener) {
//        listener.doIfInternetIsDisconnected();
//        Snackbar snackbar = Snackbar
//                .make(v, "Not Connected!",
//                        Snackbar.LENGTH_INDEFINITE)
//                .setAction("RETRY", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (isInternetConnected(packageContext)) {
//                            internetConnectedSnackbar(v, packageContext, listener);
//                        } else {
//                            noInternetSnackbar(v, packageContext, listener);
//                        }
//                    }
//                }).setActionTextColor(packageContext.getResources().getColor(R.color.red));
//
//
//        snackbar.show();
//    }

    public static void noInternetSnackbar(final View v, final Context packageContext) {

        Snackbar snackbar = Snackbar
                .make(v, "No Internet Connection!",
                        Snackbar.LENGTH_LONG)
                .setTextColor(ContextCompat.getColor(packageContext.getApplicationContext(),
                        R.color.orange));


        snackbar.show();
    }

//    public static void internetConnectedSnackbar(View v, Context packageContext, OnInternetStateListener listener) {
//        Snackbar snackbar = Snackbar
//                .make(v, "Connected!",
//                        Snackbar.LENGTH_LONG)
//                .setTextColor(packageContext.getResources().getColor(R.color.green));
//        snackbar.show();
//        listener.doAfterInternetIsConnected();
//    }

    public static void internetConnectedSnackbar(View v, Context packageContext) {
        Snackbar snackbar = Snackbar
                .make(v, "Connected!",
                        Snackbar.LENGTH_LONG)
                .setTextColor(ContextCompat.getColor(packageContext.getApplicationContext(),
                        R.color.green));
        snackbar.show();

    }

//    public static int getConnectivityStatusString(Context context) {
//        int conn = NetworkUtil.isInternetConnected(context);
//        int status = 0;
//        if (conn == NetworkUtil.TYPE_WIFI) {
//            status = NETWORK_STATUS_WIFI;
//        } else if (conn == NetworkUtil.TYPE_MOBILE) {
//            status = NETWORK_STATUS_MOBILE;
//        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
//            status = NETWORK_STATUS_NOT_CONNECTED;
//        }
//        return status;
//    }
}
