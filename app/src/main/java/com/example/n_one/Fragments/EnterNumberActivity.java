package com.example.n_one.Fragments;

import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n_one.R;

public class EnterNumberActivity extends Fragment implements View.OnClickListener {

    View view;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv0;
    private ImageView ivCross;
    private EditText etNumber;
    private TextView tvFirstBottom;

    public EnterNumberActivity() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_enter_number, container, false);
        init();
        setListener();
        return view;
    }

    private void setListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv0.setOnClickListener(this);
        ivCross.setOnClickListener(this);

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    tvFirstBottom.setBackgroundColor(Color.parseColor("#FFC107"));
                } else {
                    tvFirstBottom.setBackgroundColor(Color.parseColor("#28C9C7C7"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void init() {
        tv1 = view.findViewById(R.id.tv1Dialer);
        tv2 = view.findViewById(R.id.tv2Dialer);
        tv3 = view.findViewById(R.id.tv3Dialer);
        tv4 = view.findViewById(R.id.tv4Dialer);
        tv5 = view.findViewById(R.id.tv5Dialer);
        tv6 = view.findViewById(R.id.tv6Dialer);
        tv7 = view.findViewById(R.id.tv7Dialer);
        tv8 = view.findViewById(R.id.tv8Dialer);
        tv9 = view.findViewById(R.id.tv9Dialer);
        tv0 = view.findViewById(R.id.tv0Dialer);
        ivCross = view.findViewById(R.id.ivCrossDialer);
        etNumber = view.findViewById(R.id.etNumberDialer);

        tvFirstBottom = getActivity().findViewById(R.id.tvFirstBottom);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv1Dialer) {
            setCircle(tv1);
            etNumber.setText(etNumber.getText().toString().trim() + 1);
        } else if (view.getId() == R.id.tv2Dialer) {
            setCircle(tv2);
            etNumber.setText(etNumber.getText().toString().trim() + 2);
        } else if (view.getId() == R.id.tv3Dialer) {
            setCircle(tv3);
            etNumber.setText(etNumber.getText().toString().trim() + 3);
        } else if (view.getId() == R.id.tv4Dialer) {
            setCircle(tv4);
            etNumber.setText(etNumber.getText().toString().trim() + 4);
        } else if (view.getId() == R.id.tv5Dialer) {
            setCircle(tv5);
            etNumber.setText(etNumber.getText().toString().trim() + 5);
        } else if (view.getId() == R.id.tv6Dialer) {
            setCircle(tv6);
            etNumber.setText(etNumber.getText().toString().trim() + 6);
        } else if (view.getId() == R.id.tv7Dialer) {
            setCircle(tv7);
            etNumber.setText(etNumber.getText().toString().trim() + 7);
        } else if (view.getId() == R.id.tv8Dialer) {
            setCircle(tv8);
            etNumber.setText(etNumber.getText().toString().trim() + 8);
        } else if (view.getId() == R.id.tv9Dialer) {
            setCircle(tv9);
            etNumber.setText(etNumber.getText().toString().trim() + 9);
        } else if (view.getId() == R.id.tv0Dialer) {
            setCircle(tv0);
            etNumber.setText(etNumber.getText().toString().trim() + 0);
        } else if (view.getId() == R.id.ivCrossDialer) {
            if (!etNumber.getText().toString().trim().equals(""))
                etNumber.setText(etNumber.getText().toString().trim().substring(0, etNumber.getText().toString().trim().length() - 1));
        }
    }

    private void setCircle(TextView textView) {
        tv1.setBackgroundResource(R.drawable.border_circle);
        tv1.setTextColor(Color.BLACK);
        tv2.setBackgroundResource(R.drawable.border_circle);
        tv2.setTextColor(Color.BLACK);
        tv3.setBackgroundResource(R.drawable.border_circle);
        tv3.setTextColor(Color.BLACK);
        tv4.setBackgroundResource(R.drawable.border_circle);
        tv4.setTextColor(Color.BLACK);
        tv5.setBackgroundResource(R.drawable.border_circle);
        tv5.setTextColor(Color.BLACK);
        tv6.setBackgroundResource(R.drawable.border_circle);
        tv6.setTextColor(Color.BLACK);
        tv7.setBackgroundResource(R.drawable.border_circle);
        tv7.setTextColor(Color.BLACK);
        tv8.setBackgroundResource(R.drawable.border_circle);
        tv8.setTextColor(Color.BLACK);
        tv9.setBackgroundResource(R.drawable.border_circle);
        tv9.setTextColor(Color.BLACK);
        tv0.setBackgroundResource(R.drawable.border_circle);
        tv0.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.circle_background);
        textView.setTextColor(Color.WHITE);
    }
}
