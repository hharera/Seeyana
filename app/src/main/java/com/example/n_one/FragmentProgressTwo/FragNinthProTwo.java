package com.example.n_one.FragmentProgressTwo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.n_one.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragNinthProTwo extends Fragment {

    private View view;
    private ImageView ivShow;


    public FragNinthProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_ninth_pro_two, container, false);
        init();
        return view;
    }

    private void init() {
        ivShow = view.findViewById(R.id.show_profile_picture_image_view);
        if (FragSeventhProTwo.imageUri != null)
            ivShow.setImageURI(FragSeventhProTwo.imageUri);
    }

}
