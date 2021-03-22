package com.example.n_one.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.n_one.Fragments.PagerFragment;


public class MyViewPagerAdapter extends FragmentPagerAdapter {


    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", i);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
