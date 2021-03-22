package com.example.n_one.activitiesAzk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_one.R;

public class WithdrawRequestActivity extends AppCompatActivity {

    private LinearLayout mLayoutTop, mLayoutWithdraw, mLayoutAcDetails, mLayoutConfirm;
    private RelativeLayout mLayoutComplete;
    private TextView tvTbTitle, tvAmountTitle, tvAmountDesc, tvMethodTitle, tvMethodDesc, tvLeftArrow;
    private ImageView ivAmount;
    private EditText etAmount;
    private static int FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_withdraw_request);

        initViews();

    }

    private void initViews() {

        mLayoutTop = findViewById(R.id.lay_top);
        mLayoutWithdraw = findViewById(R.id.layout_withdraw);
        mLayoutAcDetails = findViewById(R.id.layout_ac_details);
        mLayoutConfirm = findViewById(R.id.layout_confirm);
        mLayoutComplete = findViewById(R.id.layout_complete);

        tvTbTitle = findViewById(R.id.tv_title);
        etAmount = findViewById(R.id.et_amount);
        tvAmountTitle = findViewById(R.id.tv_amount_title);
        tvAmountDesc = findViewById(R.id.tv_amount_desc);
        ivAmount = findViewById(R.id.iv_amount);
        tvMethodTitle = findViewById(R.id.tv_method_title);
        tvMethodDesc = findViewById(R.id.tv_method_desc);
        tvLeftArrow = findViewById(R.id.tv_left_arrow);


        tvAmountTitle.setText("Available balance");
        tvAmountDesc.setText("This is the amount in your account.");
        ivAmount.setImageResource(R.drawable.img_balance_2000);
        tvMethodTitle.setText("Amount to withdraw");
        tvMethodDesc.setText("Enter the amount you would like to transfer to your account. Please allow 7 days for transfers.");
        tvLeftArrow.setText("EGP");
        tvLeftArrow.setBackground(null);
        etAmount.setHint("Enter withdrawal amount");

    }

    public void chooseToShow(View view) {

        if (FLAG==0) {
            tvAmountTitle.setText("Available to withdraw");
            tvAmountDesc.setText("This is the amount you want to withdraw.");
            ivAmount.setImageResource(R.drawable.img_amount_500);
            tvMethodTitle.setText("Method of payment");
            tvMethodDesc.setText("Choose how you would like to receive your payment.");
            tvLeftArrow.setText("");
            tvLeftArrow.setBackground(getResources().getDrawable(R.drawable.ic_down_arrow));
            etAmount.setHint("FAWRY");
            FLAG=1;
        } else {
            showAcDetails();
        }


    }

    public void showAcDetails() {
        tvTbTitle.setText("Withdraw to bank");
        mLayoutWithdraw.setVisibility(View.GONE);
        mLayoutAcDetails.setVisibility(View.VISIBLE);
        FLAG = 2;
    }

    public void showConfirmLayout(View view) {
        tvTbTitle.setText("Withdraw Request");
        mLayoutConfirm.setVisibility(View.VISIBLE);
        mLayoutAcDetails.setVisibility(View.GONE);
        FLAG = 3;
    }

    public void showCompleteLayout(View view) {
        mLayoutTop.setVisibility(View.GONE);
        mLayoutConfirm.setVisibility(View.GONE);
        mLayoutComplete.setVisibility(View.VISIBLE);
        FLAG = 4;

    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void returnHome(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {

        if (FLAG == 0) {
            super.onBackPressed();
        } else if (FLAG == 1) {
            tvAmountTitle.setText("Available balance");
            tvAmountDesc.setText("This is the amount in your account.");
            ivAmount.setImageResource(R.drawable.img_balance_2000);
            tvMethodTitle.setText("Amount to withdraw");
            tvMethodDesc.setText("Enter the amount you would like to transfer to your account. Please allow 7 days for transfers.");
            tvLeftArrow.setText("EGP");
            tvLeftArrow.setBackground(null);
            etAmount.setHint("Enter withdrawal amount");
            FLAG = 0;
        } else if (FLAG == 2) {
            tvTbTitle.setText("Withdraw Request");
            mLayoutWithdraw.setVisibility(View.VISIBLE);
            mLayoutAcDetails.setVisibility(View.GONE);
            FLAG = 1;
        } else if (FLAG == 3) {
            tvTbTitle.setText("Withdraw to bank");
            mLayoutConfirm.setVisibility(View.GONE);
            mLayoutAcDetails.setVisibility(View.VISIBLE);
            FLAG = 2;
        } else {
            mLayoutTop.setVisibility(View.VISIBLE);
            mLayoutConfirm.setVisibility(View.VISIBLE);
            mLayoutComplete.setVisibility(View.GONE);
            FLAG = 3;
        }

    }
}
