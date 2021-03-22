package com.example.n_one.fragmentsAzk;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n_one.Activites.MainNavigationActivity;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.activitiesAzk.MyWalletActivity;
import com.example.n_one.activitiesAzk.WithdrawRequestActivity;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWalletFragment extends Fragment {

    private TextView mTextViewBalance;

    public MyWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pager3, container, false);

        TextView tv1 = v.findViewById(R.id.view_all);
        TextView tv2 = v.findViewById(R.id.withd);
        ImageView back = v.findViewById(R.id.ic_back);
        mTextViewBalance = v.findViewById(R.id.balance_text_view);

//        SeeyanaApp.getInstance().getProvider().setBalance(1325);
        double balance = SeeyanaApp.getInstance().getProvider().getBalance();

        String displayed = "EGP\n" + balance +"\n+" ;
        mTextViewBalance.setText(displayed);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTransactionFrag();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWithdrawActivity();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainNavigationActivity.class));
            }
        });

        return v;
    }

    public void openTransactionFrag() {
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
    }

    public void openWithdrawActivity() {
        startActivity(new Intent(getActivity(), WithdrawRequestActivity.class));
    }

    public void goBack() {

        getActivity().onBackPressed();
    }

}
