package com.example.n_one.Activites

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "cairo_regular.ttf")
    }
}