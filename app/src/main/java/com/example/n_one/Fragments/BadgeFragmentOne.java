package com.example.n_one.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.n_one.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BadgeFragmentOne extends Fragment {


    public BadgeFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_badge_fragment_one, container, false);
    }

}
