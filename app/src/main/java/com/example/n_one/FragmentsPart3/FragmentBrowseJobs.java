package com.example.n_one.FragmentsPart3;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.TouchDetectableScrollView;
import com.example.n_one.objects.Job;
import com.example.n_one.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBrowseJobs extends Fragment {
    private static final String TAG = "FragmentBrowseJobs";
    private View view;
    private LinearLayout layoutJobs;
    private TouchDetectableScrollView scrollView;
    private LinearLayout bottomNav;


    private CircleImageView mImageViewProfilePicture;
    private TextView mTextViewCustomerName;
    private TextView mTextViewPrice;
    private TextView mTextViewExpertise;
    private TextView mTextViewCity;
    private TextView mTextViewTitle;

    public FragmentBrowseJobs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_browse_jobs, container, false);
        layoutJobs = view.findViewById(R.id.layoutJobs);
        layoutJobs.removeAllViews();
        scrollView = view.findViewById(R.id.scroll_view);
        bottomNav = getActivity().findViewById(R.id.layoutBottomNavigation);

        (new DatabaseUtil()).fetchJob();
        List<Job> jobs = new ArrayList<>();
        jobs.add(SeeyanaApp.getInstance().getJob());
        try {
            jobs.add((Job) SeeyanaApp.getInstance().getJob().clone());
            jobs.add((Job) SeeyanaApp.getInstance().getJob().clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        int i = 1;
        for (Job job : jobs) {
            job.setTitle(job.getTitle() + i);
            i++;
            Log.d(TAG, "Job: Title: " + job.getTitle());
            LinearLayout layoutJobCard = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_job_card, null);
            mImageViewProfilePicture = layoutJobCard.findViewById(R.id.card_job_customer_photo);
            mTextViewCustomerName = layoutJobCard.findViewById(R.id.card_job_customer_name);
            mTextViewPrice = layoutJobCard.findViewById(R.id.card_job_price);
            mTextViewExpertise = layoutJobCard.findViewById(R.id.card_job_expertise);
            mTextViewCity = layoutJobCard.findViewById(R.id.card_job_city);
            mTextViewTitle = layoutJobCard.findViewById(R.id.card_job_title);

            mTextViewCustomerName.setText(job.getCustomerKey());
            mTextViewPrice.setText(String.valueOf(job.getPrice()));
            mTextViewExpertise.setText(job.getRequiredExpertise());
            mTextViewCity.setText(job.getCity());
            mTextViewTitle.setText(job.getTitle());

            layoutJobCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = layoutJobs.indexOfChild(v);
                    Log.d(TAG, "User clicked job number: " + (i+1));
                    SeeyanaApp.getInstance().setJob(jobs.get(i));
                    replaceFragment(new FragmentBrowseDetail());
                    TextView textViewJobTitle = getActivity().findViewById(R.id.job_title_text_view);
//                    (new DatabaseUtil()).fetchJob();
//                if (jobPost != null) {
                    textViewJobTitle.setText(SeeyanaApp.getInstance().getJob().getTitle());
                }
            });

            layoutJobs.addView(layoutJobCard);
        }
//layoutJobs.addView(layoutJobCard);




        scrollView.setMyScrollChangeListener(new TouchDetectableScrollView.OnMyScrollChangeListener() {
            @Override
            public void onScrollUp() {
                bottomNav.setVisibility(View.GONE);
            }

            @Override
            public void onScrollDown() {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
        layoutJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                replaceFragment(new FragmentBrowseDetail());
//                TextView textViewJobTitle = getActivity().findViewById(R.id.job_title_text_view);
//                (new DatabaseUtil()).fetchJob();
////                if (jobPost != null) {
//                    textViewJobTitle.setText(SeeyanaApp.getInstance().getJobPost().getTitle());
//                }
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
