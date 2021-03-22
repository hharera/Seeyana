package com.example.n_one.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.n_one.Fragments.PhoneNumberFragment;

public class PhoneNumberActivity extends SingleFragmentActivity {



    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext,PhoneNumberActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return PhoneNumberFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}