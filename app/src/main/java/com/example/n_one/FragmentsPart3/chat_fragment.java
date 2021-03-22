package com.example.n_one.FragmentsPart3;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.n_one.Activites.MainNavigationActivity;
import com.example.n_one.R;
import com.example.n_one.utils.CommentKeyBoardFix;

/**
 * A simple {@link Fragment} subclass.
 */
public class chat_fragment extends Fragment {

    private MainNavigationActivity baseActivity;
    private LinearLayout tabs;

    public chat_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_chat_fragment, container, false);
        new CommentKeyBoardFix(getActivity());
        baseActivity.tvTitleMain.setText("Messages");
        baseActivity.frameLayoutNotif.setVisibility(View.GONE);
        baseActivity.layoutBottomNavigation.setVisibility(View.GONE);
        baseActivity.back2.setVisibility(View.VISIBLE);
        tabs = view.findViewById(R.id.tabs);
        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentBrowseDetail());
            }
        });


        baseActivity.back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.bottomNavigation(5);
                baseActivity.layoutBottomNavigation.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        ImageView icon_txt = getActivity().findViewById(R.id.icon_txt);
        icon_txt.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (MainNavigationActivity) activity;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
