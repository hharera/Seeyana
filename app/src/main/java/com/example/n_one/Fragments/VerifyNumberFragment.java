package com.example.n_one.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.n_one.Activites.MainNavigationActivity;
import com.example.n_one.Activites.PhoneNumberActivity;
import com.example.n_one.Activites.SecondProgressActivity;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.objects.Provider;
import com.example.n_one.utils.DatabaseUtil;
import com.example.n_one.utils.NetworkUtil;
import com.example.n_one.utils.QueryPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyNumberFragment extends Fragment {

    private static final boolean USE_TEST_PHONE_NUMBER = false;

    private static final String TAG = "VerifyNumberFragment";

    private static final String ARG_PHONE_NUMBER = "phoneNumber";
    private static final String ARG_SEND_CODE = "sendCode";


    private String phoneNumber;
    private FirebaseAuth mAuth;
    private String mVerificationId;

    private EditText mVerifyCodeEditText;
    private Button mLoginButton;


    public static VerifyNumberFragment newInstance(String phoneNumber, boolean sendCode) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHONE_NUMBER, phoneNumber);
        args.putBoolean(ARG_SEND_CODE, sendCode);
        VerifyNumberFragment fragment = new VerifyNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_verify_number, container, false);


        mVerifyCodeEditText = v.findViewById(R.id.edit_text_otp_code);

        mLoginButton = v.findViewById(R.id.verify_button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isInternetConnected(getContext())) {
                    boolean login = true;
//                    if (mVerifyCodeEditText.getText().toString().trim().equals("")) {
//                        EditTextUtil.editTextError(mVerifyCodeEditText, "Required Field");
//                        login = false;
//                    }
                    if (login) {
                        String code = mVerifyCodeEditText.getText().toString().trim();
                        verifyVerificationCode(code);
                    }
                } else {
                    View layout = v.findViewById(R.id.verify_number_layout);
                    NetworkUtil.noInternetSnackbar(v, getActivity());
                }
            }
        });


        boolean sendCode = this.getArguments().getBoolean(ARG_SEND_CODE);
        if (sendCode) {

            phoneNumber = this.getArguments().getString(ARG_PHONE_NUMBER);
            Log.e(TAG, phoneNumber);

            mAuth = FirebaseAuth.getInstance();
            sendVerificationCode(phoneNumber);
        }
        return v;
    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,
//                60,
//                TimeUnit.SECONDS,
//                TaskExecutors.MAIN_THREAD,
//                mCallbacks);



    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

//            verifyVerificationCode(code);

            if (code == null) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

//                signInWithoutCredentials();

//                if (DatabaseUtil.isAuthed()) {
//
//                    authenticateAndProceed();
//                } else {
//                    Toast.makeText(getActivity(), "Something is wrong, we will fix it soon...", Toast.LENGTH_SHORT).show();
//                }


                return;
            } else {

                if (!PhoneNumberFragment.TEST_MODE) {
                    mVerifyCodeEditText.setText(code);
                    verifyVerificationCode(code);
                }
            }

//            Toast.makeText(getActivity(), code, Toast.LENGTH_LONG).show();

//            if (!USE_TEST_PHONE_NUMBER) {
//                if (code != null) {
//                    mVerifyCodeEditText.setText(code);
//
//                    verifyVerificationCode(code);
//                }
//            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

//            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
//            Toast.makeText(e, "", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Sorry, we encountered a problem. please try again.", Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            startActivity(PhoneNumberActivity.newIntent(getActivity()));
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);


            mVerificationId = s;
//
            if (PhoneNumberFragment.TEST_MODE) {
                mVerifyCodeEditText.setText("123456");

                verifyVerificationCode(mVerifyCodeEditText.getText().toString());
            }

//            Toast.makeText(getActivity(), "Code sent", Toast.LENGTH_LONG).show();
//


        }
    };

    private void verifyVerificationCode(String code) {


            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);






    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        authOnComplete(task);
                    }
                });

    }

//    private void signInWithoutCredentials() {
//        mAuth.signInAnonymously().addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                authOnComplete(task);
//            }
//        });
//    }

    private void authOnComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {

            Log.e(TAG, "Login Successful!");
//                            Toast.makeText(getActivity(), phoneNumber, Toast.LENGTH_SHORT).show();


            Toast.makeText(getActivity(), "Your phone has been authed successfully.", Toast.LENGTH_LONG).show();
            QueryPreferences.setStoredPhoneNumber(getActivity(), phoneNumber);
            authenticateAndProceed();
        } else {
            String message = "Something is wrong, we will fix it soon...";
            Log.e(TAG, message);
            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                message = "Invalid code entered...";
                Log.e(TAG, message);
                mVerifyCodeEditText.setError("Enter valid code");
                mVerifyCodeEditText.requestFocus();
            }
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }



    private void authenticateAndProceed() {

//        String userId = DatabaseUtil.getUserId();
//        QueryPreferences.setStoredUserId(getActivity().getApplicationContext(), userId);

//        if (SeeyanaApp.getInstance().isRegistered()) {
//            Intent intent = new Intent(getActivity(), MainNavigationActivity.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(getActivity(), SecondProgressActivity.class);
//            startActivity(intent);
//        }

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_USERS);

        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iteratorUser = snapshot.getChildren().iterator();
                if (iteratorUser.hasNext()) {
                    DataSnapshot snapshotUser = iteratorUser.next();
                    String key = snapshotUser.getKey();
                    Provider provider = snapshotUser.getValue(Provider.class);
                    QueryPreferences.setStoredUserId(getActivity(), key);
                    SeeyanaApp.getInstance().setRegistered(true);
                    SeeyanaApp.getInstance().setProvider(provider);
                    String userName = provider.getFirstName() + " " + provider.getLastName();
                    Log.d(TAG, "User " + userName + " fetched based on phone number successfully");
                    Intent intent = new Intent(getActivity(), MainNavigationActivity.class);
                    startActivity(intent);
                } else {
                    SeeyanaApp.getInstance().setRegistered(false);
                    Intent intent = new Intent(getActivity(), SecondProgressActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Toast.makeText(getActivity(), "Phone number verified successfully", Toast.LENGTH_SHORT).show();
//        Intent intent = UserDetailsActivity.newIntent(getActivity(), null);
//        Intent intent = new Intent(getActivity(), SecondProgressActivity.class);

    }

}
