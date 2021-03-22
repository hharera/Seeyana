package com.example.n_one.FragmentsPart3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.n_one.GlideApp;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.objects.Provider;
import com.example.n_one.utils.DatabaseUtil;
import com.example.n_one.utils.QueryPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class my_profile extends AppCompatActivity {

    private static final String TAG = "my_profile";

    RecyclerView recyclerView;
    LinearLayout skil_ex_lay,portfolio_ex_lay,work_ex_lay, mLayoutPortfolioAlbum,
    mLayoutSkills;
    ImageView ex_skill_btn,ex_portfolio_btn,ex_overview_btn,work_btn_ex,back,
    mImageViewProfilePic;
    TextView overview_lay_expnd, mTextViewUsername, mTextViewShortDesc,
    mTextViewExpertise, mTextViewCity, mTextViewRating, mTextViewEarnings,
    mTextViewOverview;
    boolean overview=false,work=false,portfolio=false,skil=false;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_navigation);
        setContentView(R.layout.activity_my_profile);

        skil_ex_lay=findViewById(R.id.skil_ex_lay);
        ex_skill_btn=findViewById(R.id.ex_skil_btn);
        portfolio_ex_lay=findViewById(R.id.ex_lay_portfilio);
        ex_portfolio_btn=findViewById(R.id.ex_portfolio_btn);
        overview_lay_expnd=findViewById(R.id.user_detail_textview);
        ex_overview_btn=findViewById(R.id.ex_overview_btn);
        work_btn_ex=findViewById(R.id.work_ex_btn);
        work_ex_lay=findViewById(R.id.work_lay_ex);
        back=findViewById(R.id.back);
        ratingBar = (RatingBar) findViewById(R.id.profile_rating);
        mImageViewProfilePic = findViewById(R.id.profile_img);
        mTextViewUsername = findViewById(R.id.user_name);
        mTextViewShortDesc = findViewById(R.id.user_work);
        mTextViewCity = findViewById(R.id.user_city_textview);
        mTextViewRating = findViewById(R.id.user_rating_textview);
        mTextViewExpertise = findViewById(R.id.user_expertise_textview);
        mTextViewEarnings = findViewById(R.id.provider_earnings_textview);
        mTextViewOverview = findViewById(R.id.user_detail_textview);
        mLayoutPortfolioAlbum = findViewById(R.id.portfolio_album1);
        mLayoutPortfolioAlbum.removeAllViews();
        mLayoutSkills = findViewById(R.id.required_skills_layout);


        if (SeeyanaApp.getInstance().isRegistered()) {
            fetchProfilePicture();
        fetchPortfolioPhotos();
            Provider provider = SeeyanaApp.getInstance().getProvider();
            mTextViewUsername.setText(provider.getFirstName() + " " + provider.getLastName());
            mTextViewShortDesc.setText(provider.getShortDescription());
            mTextViewCity.setText(provider.getCity() + ", " + provider.getCountry());
            mTextViewRating.setText(String.valueOf(provider.getRating()));
            ratingBar.setRating(provider.getRating());
            List<String> expertises = new ArrayList<>();
            expertises.add("Plumber");
            expertises.add("Mechanic");
            provider.setMainExpertises(expertises);
            String expertisesStr = "";

            for (String expertise : provider.getMainExpertises()) {
                expertisesStr += expertise + ", ";
            }
            expertisesStr = expertisesStr.trim();
            expertisesStr = expertisesStr.substring(0, expertisesStr.length() - 1);
//        expertisesStr = expertisesStr.trim().substring(0, expertisesStr.length()-1);
            mTextViewExpertise.setText(expertisesStr);
            mTextViewEarnings.setText(String.valueOf(provider.getEarnedMoney()));

            mTextViewOverview.setText(provider.getOverview());
            mLayoutSkills.removeAllViews();
            for (String skill : provider.getSkills()) {
                addSkillTextView(skill);
            }

        }
        ex_overview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!overview)
                {
                    ex_overview_btn.setBackgroundResource(R.drawable.ic_down_arrow);
                    overview_lay_expnd.setVisibility(View.VISIBLE);
                  overview=true;
                }else
                {
                    ex_overview_btn.setBackgroundResource(R.drawable.ic_right_arrow2);
                    overview_lay_expnd.setVisibility(View.GONE);
                    overview=false;
                }
            }
        });
        work_btn_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!work)
                {
                    work_btn_ex.setBackgroundResource(R.drawable.ic_down_arrow);
                    work_ex_lay.setVisibility(View.VISIBLE);
                    work=true;
                }else
                {
                    work_btn_ex.setBackgroundResource(R.drawable.ic_right_arrow2);
                    work_ex_lay.setVisibility(View.GONE);
                    work=false;
                }
            }
        });
        ex_portfolio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!portfolio)
                {
                    ex_portfolio_btn.setBackgroundResource(R.drawable.ic_down_arrow);
                    portfolio_ex_lay.setVisibility(View.VISIBLE);
                    portfolio=true;
                }else
                {
                    ex_portfolio_btn.setBackgroundResource(R.drawable.ic_right_arrow2);
                    portfolio_ex_lay.setVisibility(View.GONE);
                    portfolio=false;
                }
            }
        });
        ex_skill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!skil)
                {
                    ex_skill_btn.setBackgroundResource(R.drawable.ic_down_arrow);
                    skil_ex_lay.setVisibility(View.VISIBLE);
                    skil=true;
                }else
                {
                    ex_skill_btn.setBackgroundResource(R.drawable.ic_right_arrow2);
                    skil_ex_lay.setVisibility(View.GONE);
                    skil=false;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void fetchProfilePicture() {
//        QueryPreferences.setStoredUserId(getApplicationContext(), "MVPw6R_Z9nY1bBCeIjI");
        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        Log.d(TAG, "User id: " + userId);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_PROFILE_PICTURE_NAMES);
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
//                String profilePicName = iterator.next().getValue().toString();

                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                if (iterator.hasNext()) {
                    String profilePicName = String.valueOf(iterator.next().getValue());
                    if (profilePicName != null) {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference so = storage.getReference().child(DatabaseUtil.DIR_PROFILE_PICTURES + userId + "/" + profilePicName);

                        GlideApp.with(my_profile.this)
                                .load(so)
                                .into(mImageViewProfilePic);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchPortfolioPhotos(){
        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_PORTFOLIO_PHOTO_NAMES);
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                List<String> portfolioPhotoNames = new ArrayList<>();
                while (iterator.hasNext()) {
//                    portfolioPhotoNames.add(String.valueOf(iterator.next().getValue()));

                    String portfolioPhotoName = iterator.next().getValue().toString();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference so = storage.getReference().child(DatabaseUtil.DIR_PORTFOLIO_PICTURES + userId + "/" + portfolioPhotoName);

                    ImageView imageView = new ImageView(my_profile.this);


                    mLayoutPortfolioAlbum.addView(imageView);
                    mLayoutPortfolioAlbum.setMinimumHeight(300);
                    GlideApp.with(my_profile.this)
                            .load(so)
                            .into(imageView);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(20,0,0,0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(layoutParams);


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void addSkillTextView(String skill) {
//        Toast.makeText(getActivity(), "Data Changed", Toast.LENGTH_SHORT).show();
        TextView textViewSkill = new TextView(my_profile.this);
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
        mLayoutSkills.addView(textViewSkill);

    }
}
