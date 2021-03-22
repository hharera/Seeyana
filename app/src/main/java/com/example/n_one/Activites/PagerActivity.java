package com.example.n_one.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.n_one.Adapter.MyViewPagerAdapter;
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

public class PagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    private TextView tvFirst, tvSecond, tvDescription1, tvDescription2, tvDescription3, textViewSkip;
    private ImageView imageViewNext;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pager);
        init();
        setPagerListener();
        setPageViewIndicator();
    }

    private void init() {
        mPosition = 0;
        mViewPager = findViewById(R.id.viewpager);

        viewPagerCountDots = findViewById(R.id.viewPagerCountDots);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);

        tvFirst = findViewById(R.id.tvFirstTitle);
        tvSecond = findViewById(R.id.tvSecondTitle);
        tvDescription1 = findViewById(R.id.tvDescription1);
        tvDescription2 = findViewById(R.id.tvDescription2);
        tvDescription3 = findViewById(R.id.tvDescription3);
        textViewSkip = findViewById(R.id.tvSkip);
        if (textViewSkip != null) {
            textViewSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    checkIfRegistered();

                    proceedToApp();

                }
            });
        }

        imageViewNext = findViewById(R.id.image_view_next);


            imageViewNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPosition == 3) {
                        proceedToApp();

//                        checkIfRegistered();
                    } else {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    }
                }
            });

    }

    private void proceedToApp() {
        Intent intent = new Intent(PagerActivity.this, MainNavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void checkIfRegistered() {

        if (SeeyanaApp.getInstance().isRegistered()) {
            Intent intent = new Intent(PagerActivity.this, MainNavigationActivity.class);
            startActivity(intent);
        }

        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        if (userId != null && !userId.isEmpty()) {


            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_USERS);
            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Provider provider = snapshot.getValue(Provider.class);

                    if (provider != null) {
                        SeeyanaApp.getInstance().setRegistered(true);
                        SeeyanaApp.getInstance().setProvider(provider);
                        Intent intent = new Intent(PagerActivity.this, MainNavigationActivity.class);
                        startActivity(intent);
                    } else {
                        checkIfPhoneVerfied();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            checkIfPhoneVerfied();
        }
    }

    private void checkIfPhoneVerfied() {
        String phoneNumber = QueryPreferences.getStoredPhoneNumber(getApplicationContext());
        Intent intent;
        if (phoneNumber != null
        && !phoneNumber.isEmpty()){
            intent = new Intent(PagerActivity.this, SecondProgressActivity.class);
        } else {
            intent = PhoneNumberActivity.newIntent(PagerActivity.this);

        }
        startActivity(intent);
    }


    private void setPagerListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                for (int i = 0; i < dotsCount; i++) {

                    if (i == position){
                        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                    }else {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                    }
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));



                if (position == 0) {
                    tvFirst.setText("Make");
                    tvSecond.setText("Money");
                    tvDescription1.setText("Earn a living with Seeyana by getting");
                    tvDescription2.setText("more job orders that match");
                    tvDescription3.setText("your skill and experience.");
                    tvDescription3.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    tvFirst.setText("Built for");
                    tvSecond.setText("You");
                    tvDescription1.setText("Choose your expertise and add your");
                    tvDescription2.setText("skills in a few simple steps to");
                    tvDescription3.setText("match you with the right jobs.");
                    tvDescription3.setVisibility(View.VISIBLE);

                } else if (position == 2) {
                    tvFirst.setText("At your");
                    tvSecond.setText("Convenience");
                    tvDescription1.setText("Get job alerts in your area and");
                    tvDescription2.setText("accept the ones you find more");
                    tvDescription3.setText("suitable to you.");
                    tvDescription3.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    tvFirst.setText(getString(R.string.earn_and_review));
                    tvSecond.setText("Review");
                    tvDescription1.setText("Get paid securely and leave reviews");
                    tvDescription2.setText("about the customer.");
                    tvDescription3.setText("");
                    tvDescription3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = 4;//mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    16,
                    16
            );

            params.setMargins(10, 0, 10, 0);

//            final int presentPosition = i;
//            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    mViewPager.setCurrentItem(presentPosition);
//                    return true;
//                }
//
//            });

            viewPagerCountDots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }
}
