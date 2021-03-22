package com.example.n_one;

import android.app.Application;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.example.n_one.Activites.TypefaceUtil;
import com.example.n_one.objects.Job;
import com.example.n_one.objects.Provider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeeyanaApp extends Application {

    public static final int ARABIC = 0;
    public static final int ENGLISH = 1;

    private int mChosenLanguage = 1;

    private Provider mProvider;
    private Job mJob;

    private Map<String, List<Uri>> album;
    private String mAlbumName;
    private List<Uri> mImageUris;
    private Uri mUriProfilePicture;
    private Location mCurrentLocation;

    private static final String TAG = "SeeyanaApp";

    private boolean isRegistered = true;

    private static SeeyanaApp mInstance;

    public static SeeyanaApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        Log.d(TAG, timestamp.toString());
        mInstance = this;
        mAlbumName = "";
        mImageUris = new ArrayList<>();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "cairo_regular.ttf");
    }

    public boolean isRegistered() {return isRegistered;}

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public int chosenLanguage() {return mChosenLanguage;}

    public void setChosenLanguage(int chosenLanguage) {
        mChosenLanguage = chosenLanguage;
    }

    public Provider getProvider() {
        return mProvider;
    }

    public void setProvider(Provider provider) {
        mProvider = provider;
    }

    public Map<String, List<Uri>> getAlbum() {
        return album;
    }

    public void setAlbum(Map<String, List<Uri>> album) {
        this.album = album;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public List<Uri> getImageUris() {
        return mImageUris;
    }

    public void setImageUris(List<Uri> imageUris) {
        mImageUris = imageUris;
    }

    public Uri getUriProfilePicture() {
        return mUriProfilePicture;
    }

    public void setUriProfilePicture(Uri uriProfilePicture) {
        mUriProfilePicture = uriProfilePicture;
    }

    public Job getJob() {
        return mJob;
    }

    public void setJob(Job job) {
        mJob = job;
    }

    public void setCurrentLocation(Location currentLocation) {
        mCurrentLocation = currentLocation;
    }

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }
}
