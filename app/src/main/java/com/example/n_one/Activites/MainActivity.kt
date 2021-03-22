package com.example.n_one.Activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.n_one.R
import com.example.n_one.SeeyanaApp
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private var ivArabic: LinearLayout? = null
    private var ivEnglish: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        init()
        setListener()
    }

    private fun setListener() {
        ivArabic!!.setOnClickListener {
            SeeyanaApp.getInstance().setChosenLanguage(SeeyanaApp.ARABIC)
            proceed()
        }
        ivEnglish!!.setOnClickListener {
            SeeyanaApp.getInstance().setChosenLanguage(SeeyanaApp.ENGLISH)
            proceed()
        }
    }

    private fun proceed() {
        if (SeeyanaApp.getInstance().isRegistered) {
            startActivity(Intent(this@MainActivity, MainNavigationActivity::class.java))
        } else {
            startActivity(Intent(this@MainActivity, PagerActivity::class.java))
            initializeFirebase(this)
        }
    }

    private fun init() {
        ivArabic = findViewById(R.id.ivArabicIcon)
        ivEnglish = findViewById(R.id.ivEnglishIcon)
    }

    private fun initializeFirebase(context: Context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(
                context,
                FirebaseOptions.Builder()
                    .setProjectId("seeyana-824a8")
                    .setApiKey("AIzaSyDPXtltUaV0Q6Z1JDiUneeVoZhNPccqAd4")
                    .setApplicationId("1:93964821846:android:07b4dfcc1a33beb42022f7")
                    .build()
            )
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
            FirebaseDatabase.getInstance().setPersistenceCacheSizeBytes(50000000)
        }
    }
}