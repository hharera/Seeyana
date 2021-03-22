package com.example.n_one.Activites

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.n_one.R
import com.example.n_one.SeeyanaApp
import com.example.n_one.objects.Provider
import com.example.n_one.utils.DatabaseUtil
import com.example.n_one.utils.FetchLocationHelper
import com.example.n_one.utils.PermissionsUtils
import com.example.n_one.utils.QueryPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash_screen)

//        PodamFactory factory = new PodamFactoryImpl();
//        factory.getStrategy().setDefaultNumberOfCollectionElements(2);
//        Job job = factory.manufacturePojo(Job.class);
//        Provider provider = factory.manufacturePojo(Provider.class);
//        Message message = factory.manufacturePojo(Message.class);
//        Chat chat = factory.manufacturePojo(Chat.class);
//        Portfolio portfolio = factory.manufacturePojo(Portfolio.class);
//        PortfolioAlbum portfolioAlbum = factory.manufacturePojo(PortfolioAlbum.class);
//        Price price = factory.manufacturePojo(Price.class);
        if (!DatabaseUtil.isAuthed()) {
            QueryPreferences.setStoredLastLocation(applicationContext, null)
            QueryPreferences.setStoredPhoneNumber(applicationContext, null)
            QueryPreferences.setStoredUserId(applicationContext, null)
            SeeyanaApp.getInstance().isRegistered = false
            SeeyanaApp.getInstance().provider = null
            SeeyanaApp.getInstance().job = null
        }
        checkIfRegistered()
        fetchLocation()
        val imageView = findViewById<ImageView>(R.id.imageView)
        val anim = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.zoom_in_anim
        ) // Create the animation.
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        imageView.startAnimation(anim)
        //        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(mainIntent);
//                finish();
//            }
//        }, 3000);
    }

    private fun fetchLocation() {
        if (PermissionsUtils.hasPermissions(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            FetchLocationHelper(this@SplashScreen) { }
        }
    }

    private fun checkIfRegistered() {
        val userId = QueryPreferences.getStoredUserId(applicationContext)
        if (userId != null && !userId.isEmpty()) {
            val userRef = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_USERS)
            userRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val provider = snapshot.getValue(
                        Provider::class.java
                    )
                    if (provider != null) {
                        SeeyanaApp.getInstance().isRegistered = true
                        SeeyanaApp.getInstance().provider = provider
                    } else {
                        QueryPreferences.setStoredUserId(applicationContext, null)
                        // if the user is not in the database then he didn't sign up
                        // but maybe he has already verified his phone number so check first
                        checkIfPhoneVerfied()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        } else {
            checkIfPhoneVerfied()
        }
    }

    private fun checkIfPhoneVerfied() {
        val phoneNumber = QueryPreferences.getStoredPhoneNumber(applicationContext)
        if (phoneNumber != null
            && !phoneNumber.isEmpty()
        ) {
            val databaseReference =
                FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_USERS)
            databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val iteratorProvider: Iterator<DataSnapshot> = snapshot.children.iterator()
                        if (iteratorProvider.hasNext()) {
                            val snapshotProvider = iteratorProvider.next()
                            val key = snapshotProvider.key
                            val provider = snapshotProvider.getValue(
                                Provider::class.java
                            )!!
                            QueryPreferences.setStoredUserId(applicationContext, key)
                            SeeyanaApp.getInstance().isRegistered = true
                            SeeyanaApp.getInstance().provider = provider
                        } else {
                            SeeyanaApp.getInstance().isRegistered = false
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    companion object {
        private const val TAG = "SplashScreen"
    }
}