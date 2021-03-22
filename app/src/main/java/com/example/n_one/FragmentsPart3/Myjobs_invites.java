package com.example.n_one.FragmentsPart3;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.objects.Job;
import com.example.n_one.objects.Price;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Myjobs_invites extends Fragment {


    LinearLayout layoutJobs;

    public Myjobs_invites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myjobs_invites, container, false);

        hideNoti();
        layoutJobs=view.findViewById(R.id.layoutJobs);
        layoutJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchJob();
                replaceFragment(new FragmentBrowseDetail());
            }
        });


        return view;
    }

    private void fetchJob(){

        Job job = new Job();
        job.setCity("Alexandria");
        job.setDetails("Etiam molestie erat vel arcu vestibulum ornare. Nam laoreet lectus a posuere faucibus. Sed convallis molestie efficitur. Fusce gravida eu quam nec aliquet. Sed blandit et magna a iaculis. Vestibulum sit amet suscipit mi. Aliquam lectus risus, ultrices nec diam ac, scelerisque consectetur dui. Pellentesque elementum vehicula sodales.");
        job.setRequiredExpertise("Plumber");
        List<String> reqSkills = new ArrayList<>();
        reqSkills.add("skill 1");
        reqSkills.add("skill 5");
        reqSkills.add("skill 3");
        job.setRequiredSkills(reqSkills);
        job.setTitle("Fix my bathroom window");
        Price price = new Price();
        price.setLowerLimit(250);
        price.setUpperLimit(1000);
        job.setPrice(price);

        SeeyanaApp.getInstance().setJob(job);


//        String jobPostId = null;
//        DatabaseReference jobPostRef = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_JOB_POSTS);
//        jobPostRef.child(jobPostId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                JobPost jobPost = snapshot.getValue(JobPost.class);
//                if (jobPost != null) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void hideNoti() {
        FrameLayout frameLayout = getActivity().findViewById(R.id.fLayoutNotif);
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();

        FrameLayout frameLayout = getActivity().findViewById(R.id.fLayoutNotif);
        frameLayout.setVisibility(View.VISIBLE);

        ImageView icon_txt = getActivity().findViewById(R.id.icon_txt);
        icon_txt.setVisibility(View.GONE);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
