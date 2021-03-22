package com.example.n_one.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.n_one.R;

public class VerifyActivity extends Fragment implements View.OnClickListener {

    View view;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv0;
    private ImageView ivCross;
    private TextView et1, et2, et3, et4;
    private int crossFlag = 4;
    private TextView tvSecondBottom;

    public VerifyActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_verify, container, false);
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
        et1 = view.findViewById(R.id.et1Verify);
        et2 = view.findViewById(R.id.et2Verify);
        et3 = view.findViewById(R.id.et3Verify);
        et4 = view.findViewById(R.id.et4Verify);
        tvSecondBottom = getActivity().findViewById(R.id.tvSecondBottom);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.ivCrossDialer) {
            if (crossFlag >= 0 && crossFlag <= 4)
                crossFlag--;
        }
        if (view.getId() == R.id.tv1Dialer) {
            setCircle(tv1);
            setText("1");
        } else if (view.getId() == R.id.tv2Dialer) {
            setCircle(tv2);
            setText("2");
        } else if (view.getId() == R.id.tv3Dialer) {
            setCircle(tv3);
            setText("3");
        } else if (view.getId() == R.id.tv4Dialer) {
            setCircle(tv4);
            setText("4");
        } else if (view.getId() == R.id.tv5Dialer) {
            setCircle(tv5);
            setText("5");
        } else if (view.getId() == R.id.tv6Dialer) {
            setCircle(tv6);
            setText("6");
        } else if (view.getId() == R.id.tv7Dialer) {
            setCircle(tv7);
            setText("7");
        } else if (view.getId() == R.id.tv8Dialer) {
            setCircle(tv8);
            setText("8");
        } else if (view.getId() == R.id.tv9Dialer) {
            setCircle(tv9);
            setText("9");
        } else if (view.getId() == R.id.tv0Dialer) {
            setCircle(tv0);
            setText("0");
        } else if (view.getId() == R.id.ivCrossDialer) {

            if (crossFlag == 0) {
                et4.setText("");
                tvSecondBottom.setBackgroundColor(Color.parseColor("#28C9C7C7"));
                crossFlag = 1;
            } else if (crossFlag == 1) {
                et3.setText("");
                crossFlag = 2;
            } else if (crossFlag == 2) {
                et2.setText("");
                crossFlag = 3;
            } else if (crossFlag == 3) {
                et1.setText("");
                crossFlag = 4;
            }
        }
    }

    private void setText(String text) {
        if (et1.getText().toString().equals("")) {
            et1.setText(text);
        } else if (et2.getText().toString().equals("")) {
            et2.setText(text);
        } else if (et3.getText().toString().equals("")) {
            et3.setText(text);
            tvSecondBottom.setBackgroundColor(Color.parseColor("#28C9C7C7"));
        } else if (et4.getText().toString().equals("")) {
            et4.setText(text);
            tvSecondBottom.setBackgroundColor(Color.parseColor("#FFC107"));
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
