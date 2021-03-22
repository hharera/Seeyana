package com.example.n_one.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.n_one.R;


public class EmailFragment extends Fragment {


    public EmailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {







        return inflater.inflate(R.layout.fragment_email, container, false);
    }

}
