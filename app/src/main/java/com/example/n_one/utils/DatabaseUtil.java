package com.example.n_one.utils;

import android.util.Log;

import com.example.n_one.SeeyanaApp;
import com.example.n_one.objects.Job;
import com.example.n_one.objects.Price;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

    public static final String REF_JOB_POSTS = "jobPosts";
    private static final String TAG = "DatabaseUtil";

    public static final String REF_USERS = "users";
    public static final String REF_PORTFOLIO_PHOTO_NAMES = "portfolioPhotoNames";
    public static final String REF_PROFILE_PICTURE_NAMES = "profilePictureNames";


    public static final String DIR_IMAGES = "images/";
    public static final String DIR_PROFILE_PICTURES = DIR_IMAGES + "profile_pictures/";
    public static final String DIR_PORTFOLIO_PICTURES = DIR_IMAGES + "portfolio/";



    public static String getUserId() {
     if (isAuthed()) {
         return getCurrentAuthedUser().getUid();
     } else {
         return null;
     }
    }

    public static FirebaseUser getCurrentAuthedUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static boolean isAuthed() {
        return getCurrentAuthedUser() != null;
    }

    public void fetchMessages() {

    }

    public void fetchJob(){

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


    public static void sendLog(String tag, String message) {

        Log.d("SeeyanaAppLog", tag + ": " + message);
//        if (UpHeaveApplication.getInstance().isSendUsage()) {
//            String sessionId = removeProhibitedCharacters(UpHeaveApplication.getInstance().getLogSessionId());
////            String sessionId = ((UpHeaveApplication) application).getLogSessionId();
//
//            String id = String.valueOf(System.nanoTime());
//            String deviceName = removeProhibitedCharacters(DeviceUtil.getDeviceName());
//
//            Log.d("UpHeaveLog", tag + ": " + message);
//
//
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(REF_LOGGING).child(deviceName).child(sessionId).child(id);
//
//            ref.child(CHILD_TAG).setValue(tag);
//            ref.child(CHILD_MESSAGE).setValue(message);
//
//        }


    }

}