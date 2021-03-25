package com.example.n_one.FragmentProgressTwo;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.n_one.Activites.main_nav.MainNavigationActivity;
import com.example.n_one.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTenthProTwo extends Fragment {


    TextView find_work_btn;
    public FragTenthProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_tenth_pro_two, container, false);
        find_work_btn=view.findViewById(R.id.reg_find_work_button);
        find_work_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainNavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
