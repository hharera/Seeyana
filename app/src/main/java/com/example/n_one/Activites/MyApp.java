package com.example.n_one.Activites;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "cairo_regular.ttf");

    }
}
