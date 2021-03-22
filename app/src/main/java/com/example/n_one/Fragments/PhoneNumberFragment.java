package com.example.n_one.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.n_one.Activites.VerifyNumberActivity;
import com.example.n_one.R;
import com.example.n_one.utils.NetworkUtil;
import com.example.n_one.utils.QueryPreferences;
import com.hbb20.CountryCodePicker;


public class PhoneNumberFragment extends Fragment {
private static final String TAG = "PhoneNumberFragment";

    public static final boolean TEST_MODE =true;

private Button mPhoneAuthButton;
private EditText mPhoneNumberEditText;
private CountryCodePicker mCodePicker;

    public static PhoneNumberFragment newInstance() {
        return new PhoneNumberFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_phone_number, container, false);
        final View layout = v.findViewById(R.id.phone_number_layout);
        mPhoneAuthButton = v.findViewById(R.id.button_auth_number);

        mCodePicker = v.findViewById(R.id.country_code_picker);


        mPhoneNumberEditText = v.findViewById(R.id.phone_number_edit_text);

        if (TEST_MODE) {
            mCodePicker.setCountryForPhoneCode(20);
            mPhoneNumberEditText.setText("1220774891");
        }

        mPhoneAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isInternetConnected(getContext())) {
                    String selectedCountryCode = mCodePicker.getSelectedCountryCode();
                    String phoneNumber = "+" + selectedCountryCode + mPhoneNumberEditText.getText().toString();
                    Log.d(TAG, "Entered phone number: " + phoneNumber);
                    if (isValidNumber(phoneNumber)) {
                        QueryPreferences.setStoredPhoneNumber(getContext(), phoneNumber);
                        Intent intent = VerifyNumberActivity.newIntent(getActivity(), phoneNumber, true);
                        startActivity(intent);
                    }
                } else {
                    NetworkUtil.noInternetSnackbar(layout, getActivity());
                }
            }
        });
        return v;
        }

        private boolean isValidNumber(String number) {
        if (number.isEmpty() || number.length() < 10) {
            mPhoneNumberEditText.setError("Enter a valid mobile number");
            mPhoneNumberEditText.requestFocus();
            return false;
        }
        return true;
        }
}