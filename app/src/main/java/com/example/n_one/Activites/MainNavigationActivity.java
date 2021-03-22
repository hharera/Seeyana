package com.example.n_one.Activites;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.n_one.FragmentsPart3.FragmentBrowseJobs;
import com.example.n_one.FragmentsPart3.FragmentHome;
import com.example.n_one.FragmentsPart3.Message_fragment;
import com.example.n_one.FragmentsPart3.Myjob_Fragment;
import com.example.n_one.FragmentsPart3.Myjobs_invites;
import com.example.n_one.FragmentsPart3.chat_fragment;
import com.example.n_one.FragmentsPart3.my_profile;
import com.example.n_one.FragmentsPart3.notification_fragment;
import com.example.n_one.GlideApp;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.activitiesAzk.MyWalletActivity;
import com.example.n_one.activitiesAzk.OfferAcceptedActivity;
import com.example.n_one.fragmentsAzk.TransactionsFragment;
import com.example.n_one.objects.Provider;
import com.example.n_one.utils.DatabaseUtil;
import com.example.n_one.utils.FetchLocationHelper;
import com.example.n_one.utils.LocationListener;
import com.example.n_one.utils.QueryPreferences;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.Iterator;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainNavigationActivity";

    public ImageView back, back2, icon_txt;
    public RelativeLayout bot_menu;
    public FrameLayout frameLayoutNotif, fl_menu;
    public LinearLayout layoutTitle2;
    public TextView tvTitleMain, mTextViewEarningAmount, mTextViewRating;
    private ImageView ivDrawer;
    DrawerLayout drawer;
    private LinearLayout layoutBottomMessage, layoutBottomMyJobs, layoutBottomBrowseJobs, layoutBottomJobInvites, layoutBottomProfile;
    public LinearLayout layoutBottomNavigation;
    private ImageView ivBottom1, ivBottom2, ivBottom3, ivBottom4, ivBottom5;
    private View viewBottomMain;
    private FrameLayout frameLayoutContainer, frameLayoutMapContainer;
    private SpaceNavigationView bottom_navigation;
    private RatingBar mRatingBar;
    private static final int RQ_TRANS = 1111;

    RoundKornerRelativeLayout layoutMenuProfile;

    private CircleImageView menuProfileImageView;
    private TextView menuUsernameTextView;
    private LinearLayout menuEarningRatingLayout;
    private TextView signInOutTextView;

    RelativeLayout btnMenuFindWork, btnMenuBrowseJobs, btnMenuMyJobs, btnMenuJobInvites, btnMenuMessage, btnMenuWallet, btnMenuNotifs, btnMenuProfile, btnMenuSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_navigation);

        Log.d(TAG, String.valueOf(SeeyanaApp.getInstance().chosenLanguage()));

        init(savedInstanceState);

        // Disable or enable = show or hide buttons based on if the user is logged in or not
        signInMode();

        setListener();

        Dexter.withActivity(MainNavigationActivity.this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    if (SeeyanaApp.getInstance().getCurrentLocation() == null) {

                    }
                    QueryPreferences.setStoredLocationGranted(getApplicationContext(), true);
                    tvTitleMain.setText("Find Work");
                    icon_txt.setVisibility(View.GONE);
                    FragmentHome withdrawListAdmin = new FragmentHome();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "home");
                    withdrawListAdmin.setArguments(bundle);
                    replaceMapFragment(withdrawListAdmin);
                } else {
                    QueryPreferences.setStoredLocationGranted(getApplicationContext(), false);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void fetchLocation() {
        if (QueryPreferences.isLocationPermisstionGranted(getApplicationContext())) {
            new FetchLocationHelper(MainNavigationActivity.this, new LocationListener() {
                @Override
                public void onLocationListener(Location location) {

                }
            });
        }
    }

    private void locationReceived() {
        tvTitleMain.setText("Find Work");
        icon_txt.setVisibility(View.GONE);
        FragmentHome withdrawListAdmin = new FragmentHome();
        Bundle bundle = new Bundle();
        bundle.putString("type", "home");
        withdrawListAdmin.setArguments(bundle);
        replaceMapFragment(withdrawListAdmin);
    }

    private void setListener() {
        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });


        layoutBottomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation(0);

            }
        });
        layoutBottomMyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation(1);

            }
        });
        layoutBottomBrowseJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation(5);
            }
        });
        layoutBottomJobInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation(2);
            }
        });
        layoutBottomProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation(3);
            }
        });


        btnMenuFindWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                tvTitleMain.setText("Find Work");
                tvTitleMain.setTextColor(getResources().getColor(R.color.white));
                FragmentHome withdrawListAdmin = new FragmentHome();
                Bundle bundle = new Bundle();
                bundle.putString("type", "home");
                withdrawListAdmin.setArguments(bundle);
                replaceMapFragment(withdrawListAdmin);
                layoutBottomNavigation.setVisibility(View.VISIBLE);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.GONE);
                ivBottom1.setImageResource(R.drawable.bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.circle_grey);
                ivBottom4.setImageResource(R.drawable.bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.bottom_icon_5);
            }
        });
        btnMenuBrowseJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                bottomNavigation(5);
                layoutBottomNavigation.setVisibility(View.VISIBLE);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.GONE);
            }
        });
        btnMenuMyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                bottomNavigation(1);
                layoutBottomNavigation.setVisibility(View.VISIBLE);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.GONE);
            }
        });
        btnMenuJobInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                bottomNavigation(2);
                layoutBottomNavigation.setVisibility(View.VISIBLE);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.GONE);
            }
        });
        btnMenuMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                bottomNavigation(0);
                layoutBottomNavigation.setVisibility(View.VISIBLE);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.GONE);
            }
        });
        btnMenuWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainNavigationActivity.this, MyWalletActivity.class);
                startActivityForResult(intent, RQ_TRANS);
//                if (drawer.isDrawerOpen(Gravity.LEFT)) {
//                    drawer.closeDrawer(Gravity.LEFT);
//                }
//                layoutBottomNavigation.setVisibility(View.VISIBLE);
//                tvTitleMain.setVisibility(View.VISIBLE);
//                frameLayoutNotif.setVisibility(View.VISIBLE);
//                layoutTitle2.setVisibility(View.GONE);
//                back2.setVisibility(View.GONE);
            }
        });
        btnMenuNotifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
                tvTitleMain.setText("Notifications");
                icon_txt.setVisibility(View.VISIBLE);
                icon_txt.setBackgroundResource(R.drawable.blue_bottom_icon_4);
                notification_fragment withdrawListAdmin = new notification_fragment();
                replaceFragment(withdrawListAdmin);
                layoutBottomNavigation.setVisibility(View.VISIBLE);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.GONE);
                ivBottom1.setImageResource(R.drawable.bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.circle_grey);
                ivBottom4.setImageResource(R.drawable.bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.bottom_icon_5);
            }
        });
        btnMenuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainNavigationActivity.this, my_profile.class);
                startActivity(intent);
            }
        });
//        btnMenuSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawer.isDrawerOpen(Gravity.LEFT)) {
//                    drawer.closeDrawer(Gravity.LEFT);
//                }
//                layoutBottomNavigation.setVisibility(View.VISIBLE);
//                tvTitleMain.setVisibility(View.VISIBLE);
//                frameLayoutNotif.setVisibility(View.VISIBLE);
//                layoutTitle2.setVisibility(View.GONE);
//                back2.setVisibility(View.GONE);
//            }
//        });

        frameLayoutNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainNavigationActivity.this, OfferAcceptedActivity.class));
            }
        });
    }

    private void signInMode() {
        SeeyanaApp app = SeeyanaApp.getInstance();
        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        // if there isn't a stored user id then the user is signed out
        if (userId == null ||
                (userId != null && userId.isEmpty())) {
            app.setRegistered(false);
        }

        if (app.isRegistered()) {

            // fetch user's profile picture into navigation menu
            fetchProfilePicture();

            menuEarningRatingLayout.setVisibility(View.VISIBLE);
            menuProfileImageView.setVisibility(View.VISIBLE);
            menuUsernameTextView.setVisibility(View.VISIBLE);
            Provider provider = SeeyanaApp.getInstance().getProvider();
//            provider.setRating(4.5f);
//            provider.setEarnedMoney(242);
            String userName = SeeyanaApp.getInstance().getProvider().getFirstName() + " " +
                    SeeyanaApp.getInstance().getProvider().getLastName();
            menuUsernameTextView.setText(userName);
            mTextViewEarningAmount.setText(String.format("%d $", provider.getEarnedMoney()));
            mRatingBar.setRating(provider.getRating());
            mTextViewRating.setText(String.valueOf(provider.getRating()));
            btnMenuMyJobs.setVisibility(View.VISIBLE);
            btnMenuJobInvites.setVisibility(View.VISIBLE);
            btnMenuMessage.setVisibility(View.VISIBLE);
            btnMenuWallet.setVisibility(View.VISIBLE);
            btnMenuNotifs.setVisibility(View.VISIBLE);
            btnMenuProfile.setVisibility(View.VISIBLE);
            signInOutTextView.setText(R.string.sign_out);
            // If the user is logged in then sign in button will log the user out
            btnMenuSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QueryPreferences.setStoredUserId(getApplicationContext(), null);
                    QueryPreferences.setStoredPhoneNumber(getApplicationContext(), null);
                    FirebaseAuth.getInstance().signOut();
                    SeeyanaApp.getInstance().setRegistered(false);
                    SeeyanaApp.getInstance().setProvider(null);
                    Intent intent = new Intent(MainNavigationActivity.this, SplashScreen.class);
                    startActivity(intent);
                }
            });

            layoutBottomMessage.setEnabled(true);
            layoutBottomMyJobs.setEnabled(true);
            layoutBottomJobInvites.setEnabled(true);
            layoutBottomProfile.setEnabled(true);


        } else {
            menuEarningRatingLayout.setVisibility(View.GONE);
            menuProfileImageView.setVisibility(View.GONE);
            menuUsernameTextView.setVisibility(View.GONE);
            btnMenuMyJobs.setVisibility(View.GONE);
            btnMenuJobInvites.setVisibility(View.GONE);
            btnMenuMessage.setVisibility(View.GONE);
            btnMenuWallet.setVisibility(View.GONE);
            btnMenuNotifs.setVisibility(View.GONE);
            btnMenuProfile.setVisibility(View.GONE);
            layoutBottomMessage.setEnabled(false);
            layoutBottomMyJobs.setEnabled(false);
            layoutBottomJobInvites.setEnabled(false);
            layoutBottomProfile.setEnabled(false);

            // if user is signed out then sign in button sign him in
            signInOutTextView.setText(R.string.sign_in);
            btnMenuSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(PhoneNumberActivity.newIntent(MainNavigationActivity.this));
                }
            });
        }
    }

    private void fetchProfilePicture() {
        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        Log.d(TAG, "User id: " + userId);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_PROFILE_PICTURE_NAMES);
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();

                if (iterator.hasNext()) {
                    String profilePicName = String.valueOf(iterator.next().getValue());
                    if (profilePicName != null) {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference so = storage.getReference().child(DatabaseUtil.DIR_PROFILE_PICTURES + userId + "/" + profilePicName);

                        GlideApp.with(MainNavigationActivity.this)
                                .load(so)
                                .into(menuProfileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init(Bundle savedInstanceState) {
        fl_menu = findViewById(R.id.fl_menu);
        fl_menu.bringToFront();
        frameLayoutMapContainer = findViewById(R.id.fragment_map_container);
        frameLayoutContainer = findViewById(R.id.fragment_container);
        ivDrawer = findViewById(R.id.ivDrawer);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.initWithSaveInstanceState(savedInstanceState);
        bottom_navigation.changeCenterButtonIcon(R.drawable.circle_grey);
        bottom_navigation.addSpaceItem(new SpaceItem(getResources().getString(R.string.home), R.drawable.bottom_icon_1));
        bottom_navigation.addSpaceItem(new SpaceItem(getResources().getString(R.string.reward), R.drawable.bottom_icon_2));
        bottom_navigation.addSpaceItem(new SpaceItem(getResources().getString(R.string.favorites), R.drawable.bottom_icon_4));
        bottom_navigation.addSpaceItem(new SpaceItem(getResources().getString(R.string.profile), R.drawable.bottom_icon_5));
        bottom_navigation.setCentreButtonColor(getResources().getColor(R.color.yellow));


        layoutBottomMessage = findViewById(R.id.layout_bottom_message);
        layoutBottomMyJobs = findViewById(R.id.layout_bottom_my_jobs);
        layoutBottomBrowseJobs = findViewById(R.id.layout_bottom_browse_jobs);
        layoutBottomJobInvites = findViewById(R.id.layout_bottom_job_invites);
        layoutBottomProfile = findViewById(R.id.layout_bottom_profile);

        ivBottom1 = findViewById(R.id.ivBottom1);
        ivBottom2 = findViewById(R.id.ivBottom2);
        ivBottom3 = findViewById(R.id.ivBottom3);
        ivBottom4 = findViewById(R.id.ivBottom4);
        ivBottom5 = findViewById(R.id.ivBottom5);

        icon_txt = findViewById(R.id.icon_txt);
        layoutMenuProfile = findViewById(R.id.menu_profile_layout);
        layoutMenuProfile.bringToFront();
//        layoutMenuEarningRating = findViewById(R.id.menu_ear_rat_layout);
        back = findViewById(R.id.back);
        back2 = findViewById(R.id.back2);
        frameLayoutNotif = findViewById(R.id.fLayoutNotif);
        layoutTitle2 = findViewById(R.id.lLayoutTitle2);
        tvTitleMain = findViewById(R.id.tvTitleMain);
        bot_menu = findViewById(R.id.bot_menu);

        btnMenuFindWork = findViewById(R.id.menu_btn_find_work);
        btnMenuBrowseJobs = findViewById(R.id.menu_btn_browse_jobs);
        btnMenuMyJobs = findViewById(R.id.menu_btn_my_jobs);
        btnMenuJobInvites = findViewById(R.id.menu_btn_job_invites);
        btnMenuMessage = findViewById(R.id.menu_btn_message);
        btnMenuWallet = findViewById(R.id.menu_btn_wallet);
        btnMenuNotifs = findViewById(R.id.menu_btn_notifs);
        btnMenuProfile = findViewById(R.id.menu_btn_profile);
        btnMenuSign = findViewById(R.id.menu_btn_sign);

        layoutTitle2.setVisibility(View.GONE);
        tvTitleMain.setVisibility(View.VISIBLE);
        frameLayoutNotif.setVisibility(View.VISIBLE);
        layoutBottomNavigation = findViewById(R.id.layoutBottomNavigation);
        viewBottomMain = findViewById(R.id.viewBottomMain);

        menuProfileImageView = findViewById(R.id.menu_profile_picture);
        menuUsernameTextView = findViewById(R.id.menu_user_name);

        menuEarningRatingLayout = findViewById(R.id.menu_ear_rat_layout);
        signInOutTextView = findViewById(R.id.sign_in_out_textview);

        mTextViewEarningAmount = findViewById(R.id.text_view_menu_earning_amount);
        mRatingBar = findViewById(R.id.rating_bar);
        mTextViewRating = findViewById(R.id.text_view_rating_value);


        bottom_navigation.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

                bottomNavigation(5);

//                stopPlaying();
//                if (Constant_Api.aboutUsList != null) {
//                    if (Constant_Api.aboutUsList.isVideo_add_status_ad()) {
//                        method.showVideoAd("upload");
//                    } else {
//                        startActivity(new Intent(MainActivity.this, UploadActivity.class));
//                    }
//                } else {
//                    startActivity(new Intent(MainActivity.this, UploadActivity.class));
//                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                bottomNavigation(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                bottomNavigation(itemIndex);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RQ_TRANS && resultCode == RESULT_OK) {
            if (drawer.isDrawerOpen(Gravity.LEFT)) {
                drawer.closeDrawer(Gravity.LEFT);
            }
            tvTitleMain.setText("Transactions");
            icon_txt.setVisibility(View.VISIBLE);
            icon_txt.setBackgroundResource(R.drawable.ic_wallet);
            tvTitleMain.setTextColor(getResources().getColor(R.color.txt_header));
            TransactionsFragment transactionsFragment = new TransactionsFragment();
            replaceFragment(transactionsFragment);
            layoutBottomNavigation.setVisibility(View.VISIBLE);
            tvTitleMain.setVisibility(View.VISIBLE);
            frameLayoutNotif.setVisibility(View.GONE);
            layoutTitle2.setVisibility(View.GONE);
            back2.setVisibility(View.VISIBLE);
            back2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainNavigationActivity.this, MyWalletActivity.class));
                }
            });
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private void replaceFragment(Fragment fragment) {
        fl_menu.setBackgroundResource(R.color.blue_menu);
        frameLayoutMapContainer.setVisibility(View.GONE);
        frameLayoutContainer.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void replaceMapFragment(Fragment fragment) {
        icon_txt.setVisibility(View.GONE);
        fl_menu.setBackgroundResource(android.R.color.transparent);
        frameLayoutMapContainer.setVisibility(View.VISIBLE);
        frameLayoutContainer.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_map_container, fragment);
        fragmentTransaction.commit();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
                finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    public void bottomNavigation(int itemIndex) {

//        unCheck();

        switch (itemIndex) {
            case 0:
                ivBottom1.setImageResource(R.drawable.yellow_bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.circle_grey);
                ivBottom4.setImageResource(R.drawable.bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.bottom_icon_5);
                tvTitleMain.setText("Message");
                tvTitleMain.setTextColor(getResources().getColor(R.color.txt_header));
                icon_txt.setBackgroundResource(R.drawable.blue_bottom_icon_1);
                icon_txt.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                Message_fragment frag1 = new Message_fragment();
                replaceFragment(frag1);
                break;
            case 1:
                ivBottom1.setImageResource(R.drawable.bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.yellow_bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.circle_grey);
                ivBottom4.setImageResource(R.drawable.bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.bottom_icon_5);
                tvTitleMain.setText("My Jobs");
                tvTitleMain.setTextColor(getResources().getColor(R.color.txt_header));
                icon_txt.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                icon_txt.setBackgroundResource(R.drawable.blue_bottom_icon_2);
                Myjob_Fragment frag2 = new Myjob_Fragment();
                replaceFragment(frag2);
                break;
            case 2:
                ivBottom1.setImageResource(R.drawable.bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.circle_grey);
                ivBottom4.setImageResource(R.drawable.yellow_bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.bottom_icon_5);
                tvTitleMain.setText("My Jobs Invites");
                tvTitleMain.setTextColor(getResources().getColor(R.color.txt_header));
                icon_txt.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                icon_txt.setBackgroundResource(R.drawable.blue_bottom_icon_3);
                Myjobs_invites frag3 = new Myjobs_invites();
                replaceFragment(frag3);
                break;
            case 3:
                ivBottom1.setImageResource(R.drawable.bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.circle_grey);
                ivBottom4.setImageResource(R.drawable.bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.yellow_bottom_icon_5);
                Intent intent = new Intent(MainNavigationActivity.this, my_profile.class);
                startActivity(intent);
                break;

            case 5:
                ivBottom1.setImageResource(R.drawable.bottom_icon_1);
                ivBottom2.setImageResource(R.drawable.bottom_icon_2);
                ivBottom3.setImageResource(R.drawable.yellow_bottom_icon_3);
                ivBottom4.setImageResource(R.drawable.bottom_icon_4);
                ivBottom5.setImageResource(R.drawable.bottom_icon_5);
                tvTitleMain.setText("All Jobs");
                tvTitleMain.setTextColor(getResources().getColor(R.color.txt_header));
                icon_txt.setVisibility(View.GONE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                FragmentBrowseJobs pag2 = new FragmentBrowseJobs();
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "job");
                pag2.setArguments(bundle2);
                replaceFragment(pag2);
                tvTitleMain.setVisibility(View.VISIBLE);
                frameLayoutNotif.setVisibility(View.VISIBLE);
                layoutTitle2.setVisibility(View.GONE);
                back2.setVisibility(View.VISIBLE);
                break;
            case 6:
                tvTitleMain.setText("Messages");
                tvTitleMain.setTextColor(getResources().getColor(R.color.txt_header));
                icon_txt.setBackgroundResource(R.drawable.blue_bottom_icon_1);
                icon_txt.setVisibility(View.VISIBLE);
                chat_fragment pag6 = new chat_fragment();
                replaceFragment(pag6);
                break;

        }

    }
}
