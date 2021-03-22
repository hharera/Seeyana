package com.example.n_one.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class JobCard extends RelativeLayout {

    private CircleImageView mImageViewProfilePicture;
    private TextView mTextViewCustomerName;
    private TextView mTextViewPrice;
    private TextView mTextViewExpertise;
    private TextView mTextViewCity;

    public JobCard(Context context) {
        super(context);


    }
}
