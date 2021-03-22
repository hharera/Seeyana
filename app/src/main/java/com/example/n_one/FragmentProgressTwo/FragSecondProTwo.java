package com.example.n_one.FragmentProgressTwo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n_one.Adapter.FragTwoProAdapter;
import com.example.n_one.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSecondProTwo extends Fragment {

    private View view;
    RecyclerView rv;
    private ArrayList<String> list;


    public FragSecondProTwo() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_second_pro_two, container, false);
        init();
        populateList();
        setAdapter();
        return view;

    }

    private void populateList() {
//        list = new ArrayList<>();
//        list.add("Plumber");
//        list.add("AC Technician");
//        list.add("Electrician");
//        list.add("Mechanic");
//        list.add("Carpenter");
//        list.add("AC Technician");
//        list.add("Plumber");
//        list.add("AC Technician");

//        list = (ArrayList<String>) Arrays.asList(getResources().getStringArray(R.array.expertises));
//        List<String> expertises = Arrays.asList(getResources().getStringArray(R.array.expertises));
        list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.expertises)));
        /*layoutCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondProgressActivity.flag = 2;
                ((TextView)getActivity().findViewById(R.id.tvFirstProSecond)).setText("Step 3");
                ((TextView)getActivity().findViewById(R.id.tvSecondProSecond)).setText("Choose your skills");
                ((TextView)getActivity().findViewById(R.id.tvCompleteText)).setText("Complete your profile");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layoutFrame, new FragThirdProTwo()).addToBackStack(null).commit();
            }
        });*/
    }

    private void init() {
        rv = view.findViewById(R.id.main_skill_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAdapter() {
        FragTwoProAdapter adapter = new FragTwoProAdapter(getContext(), list);
        rv.setAdapter(adapter);
    }
}
