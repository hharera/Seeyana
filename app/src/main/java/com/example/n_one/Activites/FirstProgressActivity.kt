package com.example.n_one.Activites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.n_one.Fragments.*
import com.example.n_one.R
import com.example.n_one.UtilityClass

class FirstProgressActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    private var tvFirstBottom: TextView? = null
    private var tvSecondBottom: TextView? = null
    private var tvNextRightSkip: TextView? = null
    private var tvNextRightSkipYe: TextView? = null
    private var layoutBottomFourth: LinearLayout? = null
    private var layoutBottomLast: LinearLayout? = null
    private var btnSkip: Button? = null
    private var btnSkipLast: Button? = null
    private var fragmentView: FrameLayout? = null
    private var flag = "one"
    private var flagForLast = "one"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_first_progress)
        init()
        setListener()
        replaceFragment(EnterNumberActivity())
    }

    private fun setListener() {
        // first
        UtilityClass.buttonEffect(tvFirstBottom) {
            if (flagForLast == "one") {
                tvFirstBottom!!.visibility = View.GONE
                tvSecondBottom!!.visibility = View.VISIBLE
                replaceFragment(VerifyActivity())
            } else {
                startActivity(Intent(this@FirstProgressActivity, SignUpWelcomeActivity::class.java))
            }
        }
        //second
        tvSecondBottom!!.setOnClickListener {
            replaceFragment(EmailFragment())
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.VISIBLE
        }
        // third fourth fifth
        btnSkip!!.setOnClickListener {
            if (flag == "one") {
                replaceFragment(BadgeFragmentOne())
                flag = "two"
            } else if (flag == "two") {
                replaceFragment(BadgeFragmentTwo())
                flag = "three"
            } else if (flag == "three") {
                replaceFragment(BadgeFragmentThree())
                layoutBottomFourth!!.visibility = View.GONE
                layoutBottomLast!!.visibility = View.VISIBLE
            }
        }
        UtilityClass.buttonEffect(tvNextRightSkip) {
            if (flag == "one") {
                replaceFragment(BadgeFragmentOne())
                flag = "two"
            } else if (flag == "two") {
                replaceFragment(BadgeFragmentTwo())
                flag = "three"
            } else if (flag == "three") {
                replaceFragment(BadgeFragmentThree())
                layoutBottomFourth!!.visibility = View.GONE
                layoutBottomLast!!.visibility = View.VISIBLE
            }
        }

        // last
        btnSkipLast!!.setOnClickListener {
            replaceFragment(TermsAndConditions())
            layoutBottomLast!!.visibility = View.GONE
            tvFirstBottom!!.visibility = View.VISIBLE
            flagForLast = "two"
        }
        tvNextRightSkipYe!!.setOnClickListener {
            replaceFragment(TermsAndConditions())
            layoutBottomLast!!.visibility = View.GONE
            tvFirstBottom!!.visibility = View.VISIBLE
            flagForLast = "two"
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment is EnterNumberActivity) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentCenter, fragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentCenter, fragment)
                .addToBackStack(null).commit()
        }
        progressBar!!.progress = progressBar!!.progress + 14
    }

    private fun init() {
        progressBar = findViewById(R.id.progressBarOne)
        fragmentView = findViewById(R.id.fragmentCenter)
        tvFirstBottom = findViewById(R.id.tvFirstBottom)
        tvSecondBottom = findViewById(R.id.tvSecondBottom)
        tvNextRightSkip = findViewById(R.id.tvNextRightSkip)
        btnSkip = findViewById(R.id.btnSkipThird)
        btnSkipLast = findViewById(R.id.btnSkipLast)
        tvNextRightSkipYe = findViewById(R.id.tvBottomLast)
        layoutBottomFourth = findViewById(R.id.layoutBottomThird)
        layoutBottomLast = findViewById(R.id.layoutBottomLast)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        progressBar!!.progress = progressBar!!.progress - 14
        setBottom()
    }

    private fun setBottom() {
        if (visibleFragment is EnterNumberActivity) {
            flagForLast = "one"
            tvFirstBottom!!.visibility = View.VISIBLE
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomLast!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.GONE
        } else if (visibleFragment is VerifyActivity) {
            tvFirstBottom!!.visibility = View.GONE
            tvSecondBottom!!.visibility = View.VISIBLE
            layoutBottomLast!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.GONE
        } else if (visibleFragment is EmailFragment) {
            flag = "one"
            tvFirstBottom!!.visibility = View.GONE
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomLast!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.VISIBLE
        } else if (visibleFragment is BadgeFragmentOne) {
            flag = "two"
            tvFirstBottom!!.visibility = View.GONE
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomLast!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.VISIBLE
        } else if (visibleFragment is BadgeFragmentTwo) {
            flag = "three"
            tvFirstBottom!!.visibility = View.GONE
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomLast!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.VISIBLE
        } else if (visibleFragment is BadgeFragmentThree) {
            tvFirstBottom!!.visibility = View.GONE
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomLast!!.visibility = View.VISIBLE
            layoutBottomFourth!!.visibility = View.GONE
        } else if (visibleFragment is TermsAndConditions) {
            flagForLast = "two"
            tvFirstBottom!!.visibility = View.VISIBLE
            tvFirstBottom!!.text = "done"
            tvSecondBottom!!.visibility = View.GONE
            layoutBottomLast!!.visibility = View.GONE
            layoutBottomFourth!!.visibility = View.VISIBLE
        }
    }

    val visibleFragment: Fragment?
        get() {
            val fragmentManager = this@FirstProgressActivity.supportFragmentManager
            val fragments = fragmentManager.fragments
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible) return fragment
            }
            return null
        }
}