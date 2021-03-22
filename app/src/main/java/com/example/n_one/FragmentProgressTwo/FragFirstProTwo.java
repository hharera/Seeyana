package com.example.n_one.FragmentProgressTwo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.n_one.Activites.SecondProgressActivity;
import com.example.n_one.R;


public class FragFirstProTwo extends Fragment {

    EditText firstnameEditText;
    EditText lastnameEditText;

    TextView nextTextView;

    View mView;

    public FragFirstProTwo() {
        // Required empty public constructor
    }

    public FragFirstProTwo(EditText firstnameEditText, EditText lastnameEditText) {
        this.firstnameEditText = firstnameEditText;
        this.lastnameEditText = lastnameEditText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_frag_first_pro_two, container, false);

        firstnameEditText = mView.findViewById(R.id.firstname_edit_text);
        lastnameEditText = mView.findViewById(R.id.lastname_edit_text);

//        init();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((SecondProgressActivity) getActivity()).initUsernameFragment();
    }

    //    private void init() {
//        firstnameEditText = mView.findViewById(R.id.firstname_edit_text);
//        lastnameEditText = mView.findViewById(R.id.lastname_edit_text);
//        nextTextView = getActivity().findViewById(R.id.tvNextBottom);
//
//        nextTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ((SecondProgressActivity)getActivity()).setValues(2, false);
//            }
//        });
//    }



}
