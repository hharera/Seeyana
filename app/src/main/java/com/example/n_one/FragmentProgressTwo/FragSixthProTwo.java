package com.example.n_one.FragmentProgressTwo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSixthProTwo extends Fragment {

    private View view;
    private TextView tvNumbers;
    private EditText etText;


    public FragSixthProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\

        view = inflater.inflate(R.layout.fragment_frag_sixth_pro_two, container, false);
        init();
        setListemer();
        return view;
    }

    private void setListemer() {
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String desc = etText.getText().toString().trim();
                tvNumbers.setText(etText.getText().toString().length()+"/5000");
                SeeyanaApp.getInstance().getProvider().setOverview(desc);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init() {
        tvNumbers = view.findViewById(R.id.tvTotalLength);
        etText = view.findViewById(R.id.skill_description_edit_text);
    }

}
