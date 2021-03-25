package com.example.n_one.Activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.n_one.Fragments.VerifyNumberFragment

class VerifyNumberActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment? {
        val phoneNumber = intent
            .getSerializableExtra(EXTRA_PHONE_NUMBER) as String?
        val sendCode = intent.getBooleanExtra(EXTRA_SEND_CODE, false)
        return VerifyNumberFragment.newInstance(phoneNumber, sendCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        private const val EXTRA_PHONE_NUMBER = "com.utoxas.android.upheave.phoneNumber"
        private const val EXTRA_SEND_CODE = "com.utoxas.android.upheave.sendCode"
        @JvmStatic
        fun newIntent(packageContext: Context?, phoneNumber: String?, sendCode: Boolean): Intent {
            val intent = Intent(packageContext, VerifyNumberActivity::class.java)
            intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber)
            intent.putExtra(EXTRA_SEND_CODE, sendCode)
            return intent
        }
    }
}