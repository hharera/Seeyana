package com.example.n_one.Activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.n_one.Fragments.PhoneNumberFragment

class PhoneNumberActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment? {
        return PhoneNumberFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newIntent(packageContext: Context?): Intent {
            return Intent(packageContext, PhoneNumberActivity::class.java)
        }
    }
}