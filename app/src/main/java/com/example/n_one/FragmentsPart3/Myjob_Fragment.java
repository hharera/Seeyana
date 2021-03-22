package com.example.n_one.FragmentsPart3;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_one.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Myjob_Fragment extends Fragment {

    LinearLayout layoutJobs;
    RelativeLayout tab1,tab2,not_found;
    TextView txt1,txt2;
    View v1,v2;
    public Myjob_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_myjob_, container, false);

        tab1=view.findViewById(R.id.tab1);
        tab2=view.findViewById(R.id.tab2);
        txt1=view.findViewById(R.id.tab1_txt);
        txt2=view.findViewById(R.id.tab2_txt);
        v1=view.findViewById(R.id.tab1_line);
        v2=view.findViewById(R.id.tab2_line);
        not_found=view.findViewById(R.id.not_found);
        layoutJobs=view.findViewById(R.id.layoutJobs);

        hideNoti();

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              txt1.setTextColor(getResources().getColor(R.color.light_blue_menu));
              txt2.setTextColor(getResources().getColor(R.color.black));
              v1.setVisibility(View.VISIBLE);
              v2.setVisibility(View.GONE);
                not_found.setVisibility(View.GONE);
                layoutJobs.setVisibility(View.VISIBLE);
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setTextColor(getResources().getColor(R.color.black));
                txt2.setTextColor(getResources().getColor(R.color.light_blue_menu));
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);
                not_found.setVisibility(View.VISIBLE);
                layoutJobs.setVisibility(View.GONE);


            }
        });
        layoutJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentBrowseDetail());
            }
        });
        return view;
    }

    private void hideNoti() {
        FrameLayout frameLayout = getActivity().findViewById(R.id.fLayoutNotif);
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();

        FrameLayout frameLayout = getActivity().findViewById(R.id.fLayoutNotif);
        frameLayout.setVisibility(View.VISIBLE);

        ImageView icon_txt = getActivity().findViewById(R.id.icon_txt);
        icon_txt.setVisibility(View.GONE);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
