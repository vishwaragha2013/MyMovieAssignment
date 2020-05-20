package com.mymovieassignment.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.mymovieassignment.R;


/**
 * Class contains the util methods
 */
public class Utility {

    /**
     * method used to check the connectivity
     *
     * @param context
     * @return boolean
     */
    public static boolean checkConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            return false;
        }
    }

    /**
     * This method shows the Snackbar if network not available
     *
     * @param context
     * @param view
     */
    public static void showNetworkError(Activity context, View view) {
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.text_network_error_message), Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(R.string.text_ok), v1 -> context.finish());
        snackbar.show();
    }
}
