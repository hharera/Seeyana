package com.example.n_one.FragmentsPart3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n_one.Activites.main_nav.MainNavigationActivity;
import com.example.n_one.Activites.PhoneNumberActivity;
import com.example.n_one.Activites.SecondProgressActivity;
import com.example.n_one.Adapter.ImageAdapter;
import com.example.n_one.BackPress.BackPressListener;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.TouchDetectableScrollView;
import com.example.n_one.objects.Job;
import com.example.n_one.utils.DatabaseUtil;
import com.example.n_one.utils.QueryPreferences;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBrowseDetail extends Fragment implements BackPressListener {

    public static final String TAG = "FragmentBrowseDetail";

    private View view;
    private Button mButtonApplyForJob, btnPlaceMyOffer;

    private CardView cardViewBid;
    private LinearLayout layoutBid, layoutFeatured, layoutAddedOffer, layoutGetFeatured, mLayoutReqSkills,
    mLayoutReqSkills2;
    private TextView titleBid, mTextViewPrice, mTextViewExpertise, mTextViewCity, mTextViewDetails;
    private DiscreteScrollView scrollView;
    private TouchDetectableScrollView mainScrollView;
    private ArrayList<Integer> list;

    private MainNavigationActivity baseActivity;
    private TextView tv1;
    boolean expand = false;

    private Job mJob;

    public FragmentBrowseDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_browse_detail, container, false);
        init();
        populateView();
        setListener();
        return view;
    }

    private void populateView() {
        String price = mJob.getPrice().getLowerLimit() + " - " + mJob.getPrice().getUpperLimit();
        mTextViewPrice.setText(price);
        mTextViewExpertise.setText(mJob.getRequiredExpertise());
        mTextViewCity.setText(mJob.getCity());
        mTextViewDetails.setText(mJob.getDetails());

        for (String skill: mJob.getRequiredSkills()) {
            addSkillTextView(skill);
        }
    }

    private void setListener() {
        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!expand) {
                    tv1.setMaxLines(Integer.MAX_VALUE);
                    expand = true;
                } else {
                    tv1.setMaxLines(3);
                    expand = false;
                }

            }
        });
        mButtonApplyForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SeeyanaApp.getInstance().isRegistered()) {
                    showPopMain();
                } else {

                    String phoneNumber = QueryPreferences.getStoredPhoneNumber(getActivity());
                    if (phoneNumber != null && !phoneNumber.isEmpty()) {
                        Intent intent = new Intent(getActivity(), SecondProgressActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = PhoneNumberActivity.newIntent(getActivity());
                        startActivity(intent);
                    }

                }
            }
        });

        btnPlaceMyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlaceOffer();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        showPopMain();
                    }
                }, 2000);
            }
        });

        layoutGetFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showPlaceOffer();
                showFeaturedSuccess();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        showPopMain();
                    }
                }, 2000);
            }
        });
        baseActivity.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.bottomNavigation(5);
                baseActivity.layoutBottomNavigation.setVisibility(View.VISIBLE);
            }
        });

        mainScrollView.setMyScrollChangeListener(new TouchDetectableScrollView.OnMyScrollChangeListener() {
            @Override
            public void onScrollUp() {
                mButtonApplyForJob.setVisibility(View.VISIBLE);
                slideToTop(mButtonApplyForJob);
            }

            @Override
            public void onScrollDown() {
                mButtonApplyForJob.setVisibility(View.GONE);
            }
        });
    }

    private void showFeaturedSuccess() {
        layoutBid.setVisibility(View.GONE);
        layoutFeatured.setVisibility(View.VISIBLE);
        titleBid.setText("Featured Success");
        btnPlaceMyOffer.setVisibility(View.GONE);
//        if (layoutGetFeatured.isShown()) {
//            layoutGetFeatured.setVisibility(View.GONE);
//        } else {
//            layoutBid.setVisibility(View.GONE);
//            layoutGetFeatured.setVisibility(View.VISIBLE);
//            btnPlaceMyOffer.setVisibility(View.GONE);
//            titleBid.setText("Featured Success");
//        }
    }

    private void showPlaceOffer() {
        layoutBid.setVisibility(View.GONE);
        layoutAddedOffer.setVisibility(View.VISIBLE);
        btnPlaceMyOffer.setVisibility(View.GONE);
        titleBid.setText("Success");
//        if (layoutAddedOffer.isShown())
//            layoutAddedOffer.setVisibility(View.GONE);
//        else {
//            layoutBid.setVisibility(View.GONE);
//            layoutAddedOffer.setVisibility(View.VISIBLE);
//            btnPlaceMyOffer.setVisibility(View.GONE);
//            titleBid.setText("Success");
//        }
    }

    private void slideToTop(View view){
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom_up);
        view.startAnimation(bottomUp);
    }

    private void showPopMain() {

        if (cardViewBid.isShown()) {
            cardViewBid.setVisibility(View.GONE);
        } else {
            cardViewBid.setVisibility(View.VISIBLE);
            titleBid.setText("Bid Proposal");
            layoutBid.setVisibility(View.VISIBLE);
            layoutAddedOffer.setVisibility(View.GONE);
            layoutFeatured.setVisibility(View.GONE);
            btnPlaceMyOffer.setVisibility(View.VISIBLE);
            slideToTop(cardViewBid);
        }
    }

    private void init() {
        list = new ArrayList<>();
        baseActivity.tvTitleMain.setVisibility(View.GONE);
        baseActivity.frameLayoutNotif.setVisibility(View.GONE);
        baseActivity.layoutTitle2.setVisibility(View.VISIBLE);
        baseActivity.layoutBottomNavigation.setVisibility(View.GONE);

        mainScrollView = view.findViewById(R.id.scroll_view);
        mButtonApplyForJob = view.findViewById(R.id.btn_apply_for_job);
        cardViewBid = view.findViewById(R.id.cardMainBid);
        btnPlaceMyOffer = view.findViewById(R.id.btnPlaceMyOffer);
        layoutAddedOffer = view.findViewById(R.id.layoutOfferAdded);
        layoutFeatured = view.findViewById(R.id.layoutFeatured);
        layoutGetFeatured = view.findViewById(R.id.layoutGetFeatured);
        layoutBid = view.findViewById(R.id.layoutBid);
        titleBid = view.findViewById(R.id.tvTitleBid);
        scrollView = view.findViewById(R.id.picker);
        tv1 = view.findViewById(R.id.tv1);
        scrollView.setAdapter(new ImageAdapter());
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());

        titleBid.setText("Bid Proposal");
        layoutBid.setVisibility(View.VISIBLE);
        layoutAddedOffer.setVisibility(View.GONE);
        layoutFeatured.setVisibility(View.GONE);
        btnPlaceMyOffer.setVisibility(View.VISIBLE);

        mTextViewPrice = view.findViewById(R.id.job_price_text_view);
        mTextViewCity = view.findViewById(R.id.job_city_text_view);
        mTextViewExpertise = view.findViewById(R.id.required_expertise_text_view);
        mTextViewDetails = view.findViewById(R.id.job_details_text_view);
        mLayoutReqSkills = view.findViewById(R.id.required_skills_layout);
        mLayoutReqSkills.removeAllViews();

        mLayoutReqSkills2 = view.findViewById(R.id.required_skills_layout2);
        mLayoutReqSkills2.setVisibility(View.GONE);

        mJob = SeeyanaApp.getInstance().getJob();
        if (mJob == null) {
            (new DatabaseUtil()).fetchJob();
            mJob = SeeyanaApp.getInstance().getJob();
        }
    }

    @Override
    public void backPress() {
//        if (cardViewBid.isShown()){
        layoutBid.setVisibility(View.GONE);
        layoutGetFeatured.setVisibility(View.GONE);
        layoutAddedOffer.setVisibility(View.GONE);
        cardViewBid.setVisibility(View.GONE);
//        }
//        else {
//            getFragmentManager().popBackStack();
//            baseActivity.layoutBottomNavigation.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (MainNavigationActivity) activity;
    }

    private void addSkillTextView(String skill) {
//        Toast.makeText(getActivity(), "Data Changed", Toast.LENGTH_SHORT).show();
        TextView textViewSkill = new TextView(getActivity());
        textViewSkill.setBackground(getResources().getDrawable(R.drawable.rect_background_skill_dark));
//        textViewSkill.setHeight((int) getResources().getDimension(R.dimen.skill_text_view_height));

        int padding = (int) getResources().getDimension(R.dimen.skill_text_view_padding);
        Log.d(TAG, "padding: " + padding);
        textViewSkill.setPadding(padding, textViewSkill.getPaddingTop(),
                padding, textViewSkill.getPaddingBottom());


        textViewSkill.setTextColor(getResources().getColor(android.R.color.white));
        textViewSkill.setTextSize(getResources().getDimension(R.dimen.skill_text_view_text_size));
        textViewSkill.setText(skill);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20,10,0,0);
        textViewSkill.setLayoutParams(params);
        mLayoutReqSkills.addView(textViewSkill);

        }

}
