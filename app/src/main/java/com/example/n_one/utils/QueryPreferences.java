package com.example.n_one.utils;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;

public class QueryPreferences {
    private static final String TAG = "QueryPreferences";

    private static final String PREF_IS_USER_AUTH = "isUserAuth";
    private static final String PREF_USER_ID = "userId";
    private static final String PREF_PHONE_NUMBER = "phoneNumber";
    private static final String PREF_LAST_LATLNG = "latLng";
    private static final String PREF_LOCATION_PERMISSTION_GRANTED = "locationGranted";

//    public static boolean getStoredIsAuthed(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//                .getBoolean(PREF_IS_USER_AUTH, false);
//    }
//
//    public static void setStoredIsAuthed(Context context, boolean isAuthed) {
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putBoolean(PREF_IS_USER_AUTH, isAuthed)
//                .apply();
//    }

    public static String getStoredUserId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USER_ID, null);
    }

    public static void setStoredUserId(Context context, String userId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USER_ID, userId)
                .apply();
    }

    public static String getStoredPhoneNumber(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_PHONE_NUMBER, null);
    }

    public static void setStoredPhoneNumber(Context context, String phoneNumber) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_PHONE_NUMBER, phoneNumber)
                .apply();
    }

    public static LatLng getStoredLastLocation(Context context) {
        String strLatLng = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_LATLNG, null);
        if (strLatLng != null) {
            String[] latLngArray = strLatLng.split("S");
            return new LatLng(Double.parseDouble(latLngArray[0]),
                    Double.parseDouble(latLngArray[1]));
        }
        return null;

    }

public static void setStoredLastLocation(Context context, LatLng latLng) {
        String strLatlng = "";
        if (latLng == null) {
            strLatlng = null;
        } else {
            strLatlng = latLng.latitude +"S" + latLng.longitude;
        }
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_LATLNG, strLatlng)
                .apply();
}

public static boolean isLocationPermisstionGranted(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LOCATION_PERMISSTION_GRANTED, false);
}

public static void setStoredLocationGranted(Context context, boolean granted) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LOCATION_PERMISSTION_GRANTED, granted)
                .apply();
}


}
