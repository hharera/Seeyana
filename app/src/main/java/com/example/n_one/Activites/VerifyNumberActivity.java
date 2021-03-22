package com.example.n_one.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.n_one.Fragments.VerifyNumberFragment;

public class VerifyNumberActivity extends SingleFragmentActivity {

    private static final String EXTRA_PHONE_NUMBER = "com.utoxas.android.upheave.phoneNumber";
    private static final String EXTRA_SEND_CODE = "com.utoxas.android.upheave.sendCode";

    public static Intent newIntent(Context packageContext, String phoneNumber, boolean sendCode) {
        Intent intent = new Intent(packageContext, VerifyNumberActivity.class);
        intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
        intent.putExtra(EXTRA_SEND_CODE, sendCode);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String phoneNumber = (String) getIntent()
                .getSerializableExtra(EXTRA_PHONE_NUMBER);
        boolean sendCode = getIntent().getBooleanExtra(EXTRA_SEND_CODE, false);
        return VerifyNumberFragment.newInstance(phoneNumber, sendCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
