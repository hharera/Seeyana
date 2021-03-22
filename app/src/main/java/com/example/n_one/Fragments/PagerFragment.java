package com.example.n_one.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.n_one.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment {

    int pos;
    View view;
//    ImageView ivNext;
    LinearLayout layoutFrist, layoutSecond, layoutThird, layoutFourth;

    public PagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pager, container, false);
        pos = getArguments().getInt("pos", 0) + 1;
        init();
        setDescText();
        return view;
    }

    private void init() {
        layoutFrist = view.findViewById(R.id.layoutFirst);
        layoutSecond = view.findViewById(R.id.layoutSecond);
        layoutThird = view.findViewById(R.id.layoutThird);
        layoutFourth = view.findViewById(R.id.layoutFourth);
//        ivNext = view.findViewById(R.id.ivNext);
//        ivNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), FirstProgressActivity.class));
//            }
//        });
    }

    public void setDescText() {
        switch (pos) {
            case 4:
                layoutFrist.setVisibility(View.GONE);
                layoutSecond.setVisibility(View.GONE);
                layoutThird.setVisibility(View.GONE);
                layoutFourth.setVisibility(View.VISIBLE);
                break;
            case 3:
                layoutFrist.setVisibility(View.GONE);
                layoutSecond.setVisibility(View.GONE);
                layoutThird.setVisibility(View.VISIBLE);
                layoutFourth.setVisibility(View.GONE);
                break;
            case 2:
                layoutFrist.setVisibility(View.GONE);
                layoutSecond.setVisibility(View.VISIBLE);
                layoutThird.setVisibility(View.GONE);
                layoutFourth.setVisibility(View.GONE);
                break;
            case 1:
                layoutFrist.setVisibility(View.VISIBLE);
                layoutSecond.setVisibility(View.GONE);
                layoutThird.setVisibility(View.GONE);
                layoutFourth.setVisibility(View.GONE);
                break;
        }
    }

}
