package com.example.n_one.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.n_one.SeeyanaApp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.LOCATION_SERVICE;

public class FetchLocationHelper {
    private static final String TAG = "FetchLocationHelper";

    private static final int TIME_OUT = 3000;


    private Location mCurrentLocation;

    private final FusedLocationProviderClient mFusedLocationClient;

    private boolean mLocationSent;

    private final Context mContext;
    private final LocationListener mListener;

    public FetchLocationHelper(Context context, LocationListener listener) {
        DatabaseUtil.sendLog(TAG, "FetchLocationHelper()");

        mContext = context;
        mListener = listener;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);


        useFusedLocationClient();


    }

    @SuppressLint("MissingPermission")
    private void useFusedLocationClient() {
        DatabaseUtil.sendLog(TAG, "useFusedLocationClient()");


        if (!mLocationSent) {
            fetchLastKnownFusedLocation();
        }



    }

    @SuppressLint("MissingPermission")
    private void fetchLastKnownFusedLocation() {
        DatabaseUtil.sendLog(TAG, "fetchLastKnownFusedLocation()");


        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DatabaseUtil.sendLog(TAG, "fetchLastKnownFusedLocation() timed out");
                mCurrentLocation = null;
                fetchFusedLocationUpdates();
            }
        };

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.sendToTarget();
            }
        }, TIME_OUT);


        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                t.cancel();
                t.purge();
                if (task.isSuccessful()) {
                    DatabaseUtil.sendLog(TAG, "fetchLastKnownFusedLocation() onComplete() success");
                    mCurrentLocation = task.getResult();

                    if (mCurrentLocation != null) {

                        DatabaseUtil.sendLog(TAG, "fetchLastKnownFusedLocation() onComplete() mCurrentLocation != null");
//                        ((SeeyanaApp) FetchLocationService.this.getApplication()).setCurrentLocation(mCurrentLocation);
                        SeeyanaApp.getInstance().setCurrentLocation(mCurrentLocation);
                        Log.d(TAG, "Using FusedLocationProviderClient");
                        Log.d(TAG, mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());

//                        sendLocationToApp();
                        mListener.onLocationListener(mCurrentLocation);
                        mLocationSent = true;
                    } else {
                        DatabaseUtil.sendLog(TAG, "fetchLastKnownFusedLocation() onComplete() mCurrentLocation = null");

                        if (!mLocationSent) {
                            fetchFusedLocationUpdates();
                        }

                    }
                } else {
                    DatabaseUtil.sendLog(TAG, "useFusedLocationClient() USE_GET_LAST_KNOWN_LOCATION onComplete() fail");
                    DatabaseUtil.sendLog(TAG, task.getException().getMessage());
                    Log.e(TAG, task.getException().getMessage());

                    if (!mLocationSent) {
                        fetchFusedLocationUpdates();
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void fetchFusedLocationUpdates() {
        DatabaseUtil.sendLog(TAG, "fetchFusedLocationUpdates()");
        LocationRequest request =
                LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);


        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DatabaseUtil.sendLog(TAG, "fetchFusedLocationUpdates() timed out");
                mCurrentLocation = null;
                useLocationManager();
            }
        };

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.sendToTarget();
            }
        }, TIME_OUT);

        mFusedLocationClient.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                t.cancel();
                t.purge();
                DatabaseUtil.sendLog("FusedLocationProviderClient", "USE_REQUEST_LOCATION_UPDATES onLocationResult()");
                mCurrentLocation = locationResult.getLastLocation();
                if (mCurrentLocation != null) {

                    DatabaseUtil.sendLog(TAG, "fetchFusedLocationUpdates() mCurrentLocation != null");

//                    ((SeeyanaApp) FetchLocationService.this.getApplication()).setCurrentLocation(mCurrentLocation);
                    SeeyanaApp.getInstance().setCurrentLocation(mCurrentLocation);
                    Log.d(TAG, "Using FusedLocationProviderClient");
                    Log.d(TAG, mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());

//                    sendLocationToApp();
                    mListener.onLocationListener(mCurrentLocation);
                    mLocationSent = true;
                } else {

                    DatabaseUtil.sendLog(TAG, "fetchFusedLocationUpdates() mCurrentLocation = null");
                    if (!mLocationSent) {
                        useLocationManager();
                    }
                }


            }


        }, Looper.myLooper());

    }




    private void useLocationManager() {
        DatabaseUtil.sendLog(TAG, "useLocationManager()");
        LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        if (!mLocationSent) {
            fetchLastKnowLocationManagerLocation(locationManager);
        }


    }


    @SuppressLint("MissingPermission")
    private void fetchLastKnowLocationManagerLocation(LocationManager locationManager) {

        DatabaseUtil.sendLog(TAG, "fetchLastKnowLocationManagerLocation()");

        mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (mCurrentLocation == null) {
            mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

//        ((SeeyanaApp) FetchLocationService.this.getApplication()).setCurrentLocation(mCurrentLocation);
        SeeyanaApp.getInstance().setCurrentLocation(mCurrentLocation);
        if (mCurrentLocation != null) {
            DatabaseUtil.sendLog(TAG, "fetchLastKnowLocationManagerLocation mCurrentLocation != null");
            Log.d(TAG, "Using LocationManager");
            Log.d(TAG, mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());
//            sendLocationToApp();
            mListener.onLocationListener(mCurrentLocation);
            mLocationSent = true;
        } else {
            DatabaseUtil.sendLog(TAG, "fetchLastKnowLocationManagerLocation mCurrentLocation = null");
            if (!mLocationSent) {
                requestLocationManagerLocationUpdates(locationManager);
            }

        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocationManagerLocationUpdates(final LocationManager locationManager) {
        DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates()");

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() timed out");
                mCurrentLocation = null;
                mListener.onLocationListener(mCurrentLocation);
                mLocationSent = true;
            }
        };

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.sendToTarget();
            }
        }, TIME_OUT);

        android.location.LocationListener locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                t.cancel();
                t.purge();
                mCurrentLocation = location;
//                ((SeeyanaApp) FetchLocationService.this.getApplication()).setCurrentLocation(mCurrentLocation);
                SeeyanaApp.getInstance().setCurrentLocation(mCurrentLocation);
                if (mCurrentLocation != null) {
                    DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() mCurrentLocation != null");
                    Log.d(TAG, "Using LocationManager");
                    Log.d(TAG, mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());

                } else {
                    DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() mCurrentLocation = null");
                }

//                    sendLocationToApp();
                mListener.onLocationListener(mCurrentLocation);
                mLocationSent = true;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() onStatusChanged()");
                Log.e(TAG, "Location Manager Status Changed");
            }

            @Override
            public void onProviderEnabled(String s) {
                DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() onProviderEnabled()");
                Log.e(TAG, "Location Manager Provider Enabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() onProviderDisabled()");
                Log.e(TAG, "Location Manager Status Disabled.");
            }
        };

        boolean gpsExists = false;
        boolean networkExits = false;

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, locationListener);
            gpsExists = true;
        }

        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 20, locationListener);
            networkExits = true;
        }

        if (!gpsExists && !networkExits) {
            DatabaseUtil.sendLog(TAG, "requestLocationManagerLocationUpdates() both providers doesn't exist");
//                sendLocationToApp();
            mListener.onLocationListener(mCurrentLocation);
        }

    }

}


