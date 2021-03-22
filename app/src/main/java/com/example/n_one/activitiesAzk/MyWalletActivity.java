package com.example.n_one.activitiesAzk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.n_one.Adapter.VP;
import com.example.n_one.R;
import com.example.n_one.fragmentsAzk.FullPaymentFragment;
import com.example.n_one.fragmentsAzk.MPaymentFragment;
import com.example.n_one.fragmentsAzk.MyWalletFragment;
import com.example.n_one.fragmentsAzk.ReviewFragment;

public class MyWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my_wallet);

        ViewPager viewPager = findViewById(R.id.vp);

        VP adapter = new VP(getSupportFragmentManager());
        adapter.addFrag(new MyWalletFragment(), "");
//        adapter.addFrag(new MPaymentFragment(), "");
//        adapter.addFrag(new FullPaymentFragment(), "");
//        adapter.addFrag(new ReviewFragment(), "");
        viewPager.setAdapter(adapter);

    }
}
