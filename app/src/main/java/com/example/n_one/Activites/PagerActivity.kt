package com.example.n_one.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.n_one.Activites.main_nav.MainNavigationActivity
import com.example.n_one.Adapter.MyViewPagerAdapter
import com.example.n_one.R
import com.example.n_one.SeeyanaApp
import com.example.n_one.objects.Provider
import com.example.n_one.utils.DatabaseUtil
import com.example.n_one.utils.QueryPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PagerActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager
    private var viewPagerCountDots: LinearLayout? = null
    private var dotsCount = 0
    private lateinit var dots: Array<ImageView?>
    private var tvFirst: TextView? = null
    private var tvSecond: TextView? = null
    private var tvDescription1: TextView? = null
    private var tvDescription2: TextView? = null
    private var tvDescription3: TextView? = null
    private var textViewSkip: TextView? = null
    private lateinit var imageViewNext: ImageView
    private var mPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_pager)
        init()
        setPagerListener()
        setPageViewIndicator()
    }

    private fun init() {
        mPosition = 0
        mViewPager = findViewById(R.id.viewpager)
        viewPagerCountDots = findViewById(R.id.viewPagerCountDots)
        mViewPager.setAdapter(MyViewPagerAdapter(supportFragmentManager))
        mViewPager.setCurrentItem(0)
        tvFirst = findViewById(R.id.tvFirstTitle)
        tvSecond = findViewById(R.id.tvSecondTitle)
        tvDescription1 = findViewById(R.id.tvDescription1)
        tvDescription2 = findViewById(R.id.tvDescription2)
        tvDescription3 = findViewById(R.id.tvDescription3)
        textViewSkip = findViewById(R.id.tvSkip)
        if (textViewSkip != null) {
            textViewSkip!!.setOnClickListener { //                    checkIfRegistered();
                proceedToApp()
            }
        }
        imageViewNext = findViewById(R.id.image_view_next)
        imageViewNext.setOnClickListener(View.OnClickListener {
            if (mPosition == 3) {
                proceedToApp()

//                        checkIfRegistered();
            } else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1)
            }
        })
    }

    private fun proceedToApp() {
        val intent = Intent(this@PagerActivity, MainNavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun checkIfRegistered() {
        if (SeeyanaApp.getInstance().isRegistered) {
            val intent = Intent(this@PagerActivity, MainNavigationActivity::class.java)
            startActivity(intent)
        }
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
                        val intent = Intent(this@PagerActivity, MainNavigationActivity::class.java)
                        startActivity(intent)
                    } else {
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
        val intent: Intent
        intent = if (phoneNumber != null
            && !phoneNumber.isEmpty()
        ) {
            Intent(this@PagerActivity, SecondProgressActivity::class.java)
        } else {
            PhoneNumberActivity.Companion.newIntent(this@PagerActivity)
        }
        startActivity(intent)
    }

    private fun setPagerListener() {
        mViewPager!!.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mPosition = position
                for (i in 0 until dotsCount) {
                    if (i == position) {
                        dots[position]!!.setImageDrawable(resources.getDrawable(R.drawable.selecteditem_dot))
                    } else {
                        dots[i]!!.setImageDrawable(resources.getDrawable(R.drawable.nonselecteditem_dot))
                    }
                }
                dots[position]!!.setImageDrawable(resources.getDrawable(R.drawable.selecteditem_dot))
                if (position == 0) {
                    tvFirst!!.text = "Make"
                    tvSecond!!.text = "Money"
                    tvDescription1!!.text = "Earn a living with Seeyana by getting"
                    tvDescription2!!.text = "more job orders that match"
                    tvDescription3!!.text = "your skill and experience."
                    tvDescription3!!.visibility = View.VISIBLE
                } else if (position == 1) {
                    tvFirst!!.text = "Built for"
                    tvSecond!!.text = "You"
                    tvDescription1!!.text = "Choose your expertise and add your"
                    tvDescription2!!.text = "skills in a few simple steps to"
                    tvDescription3!!.text = "match you with the right jobs."
                    tvDescription3!!.visibility = View.VISIBLE
                } else if (position == 2) {
                    tvFirst!!.text = "At your"
                    tvSecond!!.text = "Convenience"
                    tvDescription1!!.text = "Get job alerts in your area and"
                    tvDescription2!!.text = "accept the ones you find more"
                    tvDescription3!!.text = "suitable to you."
                    tvDescription3!!.visibility = View.VISIBLE
                } else if (position == 3) {
                    tvFirst!!.text = getString(R.string.earn_and_review)
                    tvSecond!!.text = "Review"
                    tvDescription1!!.text = "Get paid securely and leave reviews"
                    tvDescription2!!.text = "about the customer."
                    tvDescription3!!.text = ""
                    tvDescription3!!.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setPageViewIndicator() {
        Log.d("###setPageViewIndicator", " : called")
        dotsCount = 4 //mAdapter.getCount();
        dots = arrayOfNulls<ImageView>(dotsCount)
        for (i in 0 until dotsCount) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(resources.getDrawable(R.drawable.nonselecteditem_dot))
            val params = LinearLayout.LayoutParams(
                16,
                16
            )
            params.setMargins(10, 0, 10, 0)

//            final int presentPosition = i;
//            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    mViewPager.setCurrentItem(presentPosition);
//                    return true;
//                }
//
//            });
            viewPagerCountDots!!.addView(dots[i], params)
        }
        dots[0]!!.setImageDrawable(resources.getDrawable(R.drawable.selecteditem_dot))
    }
}