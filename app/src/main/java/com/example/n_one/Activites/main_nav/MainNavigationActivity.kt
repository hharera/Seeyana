package com.example.n_one.Activites.main_nav

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.n_one.Activites.PhoneNumberActivity
import com.example.n_one.Activites.SplashScreen
import com.example.n_one.FragmentsPart3.*
import com.example.n_one.R
import com.example.n_one.SeeyanaApp
import com.example.n_one.activitiesAzk.MyWalletActivity
import com.example.n_one.activitiesAzk.OfferAcceptedActivity
import com.example.n_one.fragmentsAzk.TransactionsFragment
import com.example.n_one.utils.DatabaseUtil
import com.example.n_one.utils.FetchLocationHelper
import com.example.n_one.utils.QueryPreferences
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.irfaan008.irbottomnavigation.SpaceItem
import com.irfaan008.irbottomnavigation.SpaceNavigationView
import com.irfaan008.irbottomnavigation.SpaceOnClickListener
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import de.hdodenhof.circleimageview.CircleImageView

class MainNavigationActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    @JvmField
    var back: ImageView? = null
    @JvmField
    var back2: ImageView? = null
    var icon_txt: ImageView? = null
    var bot_menu: RelativeLayout? = null
    @JvmField
    var frameLayoutNotif: FrameLayout? = null
    var fl_menu: FrameLayout? = null
    @JvmField
    var layoutTitle2: LinearLayout? = null
    @JvmField
    var tvTitleMain: TextView? = null
    var mTextViewEarningAmount: TextView? = null
    var mTextViewRating: TextView? = null
    private var ivDrawer: ImageView? = null
    var drawer: DrawerLayout? = null
    private var layoutBottomMessage: LinearLayout? = null
    private var layoutBottomMyJobs: LinearLayout? = null
    private var layoutBottomBrowseJobs: LinearLayout? = null
    private var layoutBottomJobInvites: LinearLayout? = null
    private var layoutBottomProfile: LinearLayout? = null
    @JvmField
    var layoutBottomNavigation: LinearLayout? = null

//    private var ivBottom1: ImageView? = null
    private var ivBottom2: ImageView? = null
    private var ivBottom3: ImageView? = null
    private var ivBottom4: ImageView? = null
    private var ivBottom5: ImageView? = null
    private var viewBottomMain: View? = null
    private var frameLayoutContainer: FrameLayout? = null
    private var frameLayoutMapContainer: FrameLayout? = null
    private lateinit var bottom_navigation: SpaceNavigationView
    private var mRatingBar: RatingBar? = null
    var layoutMenuProfile: RoundKornerRelativeLayout? = null
    private var menuProfileImageView: CircleImageView? = null
    private var menuUsernameTextView: TextView? = null
    private var menuEarningRatingLayout: LinearLayout? = null
    private var signInOutTextView: TextView? = null
    var btnMenuFindWork: RelativeLayout? = null
    var btnMenuBrowseJobs: RelativeLayout? = null
    var btnMenuMyJobs: RelativeLayout? = null
    var btnMenuJobInvites: RelativeLayout? = null
    var btnMenuMessage: RelativeLayout? = null
    var btnMenuWallet: RelativeLayout? = null
    var btnMenuNotifs: RelativeLayout? = null
    var btnMenuProfile: RelativeLayout? = null
    var btnMenuSign: RelativeLayout? = null

    lateinit var viewModel: MainNavigationViewModel
    lateinit var bind:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_main_navigation)
        viewModel = ViewModelProvider(this).get(MainNavigationViewModel::class.java)
        bind.viewModel = viewModel

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        Log.d(TAG, SeeyanaApp.getInstance().chosenLanguage().toString())
        init(savedInstanceState)

        // Disable or enable = show or hide buttons based on if the user is logged in or not
        signInMode()
        setListener()
        Dexter.withActivity(this@MainNavigationActivity).withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    if (SeeyanaApp.getInstance().currentLocation == null) {
                    }
                    QueryPreferences.setStoredLocationGranted(applicationContext, true)
                    tvTitleMain!!.text = "Find Work"
                    icon_txt!!.visibility = View.GONE
                    val withdrawListAdmin = FragmentHome()
                    val bundle = Bundle()
                    bundle.putString("type", "home")
                    withdrawListAdmin.arguments = bundle
                    replaceMapFragment(withdrawListAdmin)
                } else {
                    QueryPreferences.setStoredLocationGranted(applicationContext, false)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>,
                token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        }).check()
    }

    private fun fetchLocation() {
        if (QueryPreferences.isLocationPermisstionGranted(applicationContext)) {
            FetchLocationHelper(this@MainNavigationActivity) { }
        }
    }

    private fun locationReceived() {
        tvTitleMain!!.text = "Find Work"
        icon_txt!!.visibility = View.GONE
        val withdrawListAdmin = FragmentHome()
        val bundle = Bundle()
        bundle.putString("type", "home")
        withdrawListAdmin.arguments = bundle
        replaceMapFragment(withdrawListAdmin)
    }

    private fun setListener() {
        ivDrawer!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            } else {
                drawer!!.openDrawer(Gravity.LEFT)
            }
        }
        layoutBottomMessage!!.setOnClickListener { bottomNavigation(0) }
        layoutBottomMyJobs!!.setOnClickListener { bottomNavigation(1) }
        layoutBottomBrowseJobs!!.setOnClickListener { bottomNavigation(5) }
        layoutBottomJobInvites!!.setOnClickListener { bottomNavigation(2) }
        layoutBottomProfile!!.setOnClickListener { bottomNavigation(3) }
        btnMenuFindWork!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            tvTitleMain!!.text = "Find Work"
            tvTitleMain!!.setTextColor(resources.getColor(R.color.white))
            val withdrawListAdmin = FragmentHome()
            val bundle = Bundle()
            bundle.putString("type", "home")
            withdrawListAdmin.arguments = bundle
            replaceMapFragment(withdrawListAdmin)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.VISIBLE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.GONE
            ivBottom1!!.setImageResource(R.drawable.messages)
            ivBottom2!!.setImageResource(R.drawable.jobs)
            ivBottom3!!.setImageResource(R.drawable.circle_grey)
            ivBottom4!!.setImageResource(R.drawable.bottom_icon_4)
            ivBottom5!!.setImageResource(R.drawable.bottom_icon_5)
        }
        btnMenuBrowseJobs!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            bottomNavigation(5)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.VISIBLE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.GONE
        }
        btnMenuMyJobs!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            bottomNavigation(1)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.VISIBLE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.GONE
        }
        btnMenuJobInvites!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            bottomNavigation(2)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.VISIBLE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.GONE
        }
        btnMenuMessage!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            bottomNavigation(0)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.VISIBLE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.GONE
        }
        btnMenuWallet!!.setOnClickListener {
            val intent = Intent(this@MainNavigationActivity, MyWalletActivity::class.java)
            startActivityForResult(intent, RQ_TRANS)
            //                if (drawer.isDrawerOpen(Gravity.LEFT)) {
//                    drawer.closeDrawer(Gravity.LEFT);
//                }
//                layoutBottomNavigation.setVisibility(View.VISIBLE);
//                tvTitleMain.setVisibility(View.VISIBLE);
//                frameLayoutNotif.setVisibility(View.VISIBLE);
//                layoutTitle2.setVisibility(View.GONE);
//                back2.setVisibility(View.GONE);
        }
        btnMenuNotifs!!.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            tvTitleMain!!.text = "Notifications"
            icon_txt!!.visibility = View.VISIBLE
            icon_txt!!.setBackgroundResource(R.drawable.blue_bottom_icon_4)
            val withdrawListAdmin = notification_fragment()
            replaceFragment(withdrawListAdmin)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.VISIBLE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.GONE
            ivBottom1!!.setImageResource(R.drawable.messages)
            ivBottom2!!.setImageResource(R.drawable.jobs)
            ivBottom3!!.setImageResource(R.drawable.circle_grey)
            ivBottom4!!.setImageResource(R.drawable.bottom_icon_4)
            ivBottom5!!.setImageResource(R.drawable.bottom_icon_5)
        }
        btnMenuProfile!!.setOnClickListener {
            val intent = Intent(this@MainNavigationActivity, my_profile::class.java)
            startActivity(intent)
        }
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
        frameLayoutNotif!!.setOnClickListener {
            startActivity(
                Intent(
                    this@MainNavigationActivity,
                    OfferAcceptedActivity::class.java
                )
            )
        }
    }

    private fun signInMode() {
        val app = SeeyanaApp.getInstance()
        val userId = QueryPreferences.getStoredUserId(applicationContext)
        // if there isn't a stored user id then the user is signed out
        if (userId == null ||
            userId != null && userId.isEmpty()
        ) {
            app.isRegistered = false
        }
        if (app.isRegistered) {

            // fetch user's profile picture into navigation menu
            fetchProfilePicture()
            menuEarningRatingLayout!!.visibility = View.VISIBLE
            menuProfileImageView!!.visibility = View.VISIBLE
            menuUsernameTextView!!.visibility = View.VISIBLE
            val provider = SeeyanaApp.getInstance().provider
            //            provider.setRating(4.5f);
//            provider.setEarnedMoney(242);
            val userName = SeeyanaApp.getInstance().provider.firstName + " " +
                    SeeyanaApp.getInstance().provider.lastName
            menuUsernameTextView!!.text = userName
            mTextViewEarningAmount!!.text = String.format("%d $", provider.earnedMoney)
            mRatingBar!!.rating = provider.rating
            mTextViewRating!!.text = provider.rating.toString()
            btnMenuMyJobs!!.visibility = View.VISIBLE
            btnMenuJobInvites!!.visibility = View.VISIBLE
            btnMenuMessage!!.visibility = View.VISIBLE
            btnMenuWallet!!.visibility = View.VISIBLE
            btnMenuNotifs!!.visibility = View.VISIBLE
            btnMenuProfile!!.visibility = View.VISIBLE
            signInOutTextView!!.setText(R.string.sign_out)
            // If the user is logged in then sign in button will log the user out
            btnMenuSign!!.setOnClickListener {
                QueryPreferences.setStoredUserId(applicationContext, null)
                QueryPreferences.setStoredPhoneNumber(applicationContext, null)
                FirebaseAuth.getInstance().signOut()
                SeeyanaApp.getInstance().isRegistered = false
                SeeyanaApp.getInstance().provider = null
                val intent = Intent(this@MainNavigationActivity, SplashScreen::class.java)
                startActivity(intent)
            }
            layoutBottomMessage!!.isEnabled = true
            layoutBottomMyJobs!!.isEnabled = true
            layoutBottomJobInvites!!.isEnabled = true
            layoutBottomProfile!!.isEnabled = true
        } else {
            menuEarningRatingLayout!!.visibility = View.GONE
            menuProfileImageView!!.visibility = View.GONE
            menuUsernameTextView!!.visibility = View.GONE
            btnMenuMyJobs!!.visibility = View.GONE
            btnMenuJobInvites!!.visibility = View.GONE
            btnMenuMessage!!.visibility = View.GONE
            btnMenuWallet!!.visibility = View.GONE
            btnMenuNotifs!!.visibility = View.GONE
            btnMenuProfile!!.visibility = View.GONE
            layoutBottomMessage!!.isEnabled = false
            layoutBottomMyJobs!!.isEnabled = false
            layoutBottomJobInvites!!.isEnabled = false
            layoutBottomProfile!!.isEnabled = false

            // if user is signed out then sign in button sign him in
            signInOutTextView!!.setText(R.string.sign_in)
            btnMenuSign!!.setOnClickListener {
                startActivity(
                    PhoneNumberActivity.newIntent(
                        this@MainNavigationActivity
                    )
                )
            }
        }
    }

    private fun fetchProfilePicture() {
        val userId = QueryPreferences.getStoredUserId(applicationContext)
        Log.d(TAG, "User id: $userId")
        val ref =
            FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_PROFILE_PICTURE_NAMES)
        ref.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val iterator: Iterator<DataSnapshot> = snapshot.children.iterator()
                if (iterator.hasNext()) {
                    val profilePicName = iterator.next().value.toString()
                    if (profilePicName != null) {
                        val storage = FirebaseStorage.getInstance()
                        val so =
                            storage.reference.child(DatabaseUtil.DIR_PROFILE_PICTURES + userId + "/" + profilePicName)
                        GlideApp.with(this@MainNavigationActivity)
                            .load(so)
                            .into(menuProfileImageView!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun init(savedInstanceState: Bundle?) {
        fl_menu = findViewById(R.id.fl_menu)
        fl_menu!!.bringToFront()
        frameLayoutMapContainer = findViewById(R.id.fragment_map_container)
        frameLayoutContainer = findViewById(R.id.fragment_container)
        ivDrawer = findViewById(R.id.ivDrawer)
        drawer = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()
        bottom_navigation = findViewById(R.id.bottom_navigation)
        bottom_navigation.initWithSaveInstanceState(savedInstanceState)
        bottom_navigation.changeCenterButtonIcon(R.drawable.circle_grey)
        bottom_navigation.addSpaceItem(
            SpaceItem(
                resources.getString(R.string.home),
                R.drawable.messages
            )
        )
        bottom_navigation.addSpaceItem(
            SpaceItem(
                resources.getString(R.string.reward),
                R.drawable.jobs
            )
        )
        bottom_navigation.addSpaceItem(
            SpaceItem(
                resources.getString(R.string.favorites),
                R.drawable.bottom_icon_4
            )
        )
        bottom_navigation.addSpaceItem(
            SpaceItem(
                resources.getString(R.string.profile),
                R.drawable.bottom_icon_5
            )
        )
        bottom_navigation.setCentreButtonColor(resources.getColor(R.color.yellow))
        layoutBottomMessage = findViewById(R.id.layout_bottom_message)
//        layoutBottomMyJobs = findViewById(R.id.layout_bottom_my_jobs)
        layoutBottomBrowseJobs = findViewById(R.id.layout_bottom_browse_jobs)
        layoutBottomJobInvites = findViewById(R.id.layout_bottom_job_invites)
        layoutBottomProfile = findViewById(R.id.layout_bottom_profile)
        ivBottom1 = findViewById(R.id.ivBottom1)
//        ivBottom2 = findViewById(R.id.ivBottom2)
        ivBottom3 = findViewById(R.id.ivBottom3)
        ivBottom4 = findViewById(R.id.ivBottom4)
        ivBottom5 = findViewById(R.id.ivBottom5)
        icon_txt = findViewById(R.id.icon_txt)
        layoutMenuProfile = findViewById(R.id.menu_profile_layout)
        layoutMenuProfile!!.bringToFront()
        //        layoutMenuEarningRating = findViewById(R.id.menu_ear_rat_layout);
        back = findViewById(R.id.back)
        back2 = findViewById(R.id.back2)
        frameLayoutNotif = findViewById(R.id.fLayoutNotif)
        layoutTitle2 = findViewById(R.id.lLayoutTitle2)
        tvTitleMain = findViewById(R.id.tvTitleMain)
        bot_menu = findViewById(R.id.bot_menu)
        btnMenuFindWork = findViewById(R.id.menu_btn_find_work)
        btnMenuBrowseJobs = findViewById(R.id.menu_btn_browse_jobs)
        btnMenuMyJobs = findViewById(R.id.menu_btn_my_jobs)
        btnMenuJobInvites = findViewById(R.id.menu_btn_job_invites)
        btnMenuMessage = findViewById(R.id.menu_btn_message)
        btnMenuWallet = findViewById(R.id.menu_btn_wallet)
        btnMenuNotifs = findViewById(R.id.menu_btn_notifs)
        btnMenuProfile = findViewById(R.id.menu_btn_profile)
        btnMenuSign = findViewById(R.id.menu_btn_sign)
//        layoutTitle2.setVisibility(View.GONE)
//        tvTitleMain.setVisibility(View.VISIBLE)
//        frameLayoutNotif.setVisibility(View.VISIBLE)
        layoutBottomNavigation = findViewById(R.id.layoutBottomNavigation)
        viewBottomMain = findViewById(R.id.viewBottomMain)
        menuProfileImageView = findViewById(R.id.menu_profile_picture)
        menuUsernameTextView = findViewById(R.id.menu_user_name)
        menuEarningRatingLayout = findViewById(R.id.menu_ear_rat_layout)
        signInOutTextView = findViewById(R.id.sign_in_out_textview)
        mTextViewEarningAmount = findViewById(R.id.text_view_menu_earning_amount)
        mRatingBar = findViewById(R.id.rating_bar)
        mTextViewRating = findViewById(R.id.text_view_rating_value)
        bottom_navigation.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                bottomNavigation(5)

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

            override fun onItemClick(itemIndex: Int, itemName: String) {
                bottomNavigation(itemIndex)
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                bottomNavigation(itemIndex)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_TRANS && resultCode == RESULT_OK) {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer!!.closeDrawer(Gravity.LEFT)
            }
            tvTitleMain!!.text = "Transactions"
            icon_txt!!.visibility = View.VISIBLE
            icon_txt!!.setBackgroundResource(R.drawable.ic_wallet)
            tvTitleMain!!.setTextColor(resources.getColor(R.color.txt_header))
            val transactionsFragment = TransactionsFragment()
            replaceFragment(transactionsFragment)
            layoutBottomNavigation!!.visibility = View.VISIBLE
            tvTitleMain!!.visibility = View.VISIBLE
            frameLayoutNotif!!.visibility = View.GONE
            layoutTitle2!!.visibility = View.GONE
            back2!!.visibility = View.VISIBLE
            back2!!.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainNavigationActivity,
                        MyWalletActivity::class.java
                    )
                )
            }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    private fun replaceFragment(fragment: Fragment) {
        fl_menu!!.setBackgroundResource(R.color.blue_menu)
        frameLayoutMapContainer!!.visibility = View.GONE
        frameLayoutContainer!!.visibility = View.VISIBLE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun replaceMapFragment(fragment: Fragment) {
        icon_txt!!.visibility = View.GONE
        fl_menu!!.setBackgroundResource(android.R.color.transparent)
        frameLayoutMapContainer!!.visibility = View.VISIBLE
        frameLayoutContainer!!.visibility = View.GONE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_map_container, fragment)
        fragmentTransaction.commit()
    }

    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
            drawer!!.closeDrawer(Gravity.LEFT)
        } else {
            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
                finishAffinity()
                return
            }
            doubleBackToExitPressedOnce = true
            //            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    fun bottomNavigation(itemIndex: Int) {

//        unCheck();
        when (itemIndex) {
            0 -> {
                ivBottom1!!.setImageResource(R.drawable.yellow_bottom_icon_1)
                ivBottom2!!.setImageResource(R.drawable.jobs)
                ivBottom3!!.setImageResource(R.drawable.circle_grey)
                ivBottom4!!.setImageResource(R.drawable.bottom_icon_4)
                ivBottom5!!.setImageResource(R.drawable.bottom_icon_5)
                tvTitleMain!!.text = "Message"
                tvTitleMain!!.setTextColor(resources.getColor(R.color.txt_header))
                icon_txt!!.setBackgroundResource(R.drawable.blue_bottom_icon_1)
                icon_txt!!.visibility = View.VISIBLE
                frameLayoutNotif!!.visibility = View.VISIBLE
                layoutTitle2!!.visibility = View.GONE
                val frag1 = Message_fragment()
                replaceFragment(frag1)
            }
            1 -> {
                ivBottom1!!.setImageResource(R.drawable.messages)
                ivBottom2!!.setImageResource(R.drawable.yellow_bottom_icon_2)
                ivBottom3!!.setImageResource(R.drawable.circle_grey)
                ivBottom4!!.setImageResource(R.drawable.bottom_icon_4)
                ivBottom5!!.setImageResource(R.drawable.bottom_icon_5)
                tvTitleMain!!.text = "My Jobs"
                tvTitleMain!!.setTextColor(resources.getColor(R.color.txt_header))
                icon_txt!!.visibility = View.VISIBLE
                frameLayoutNotif!!.visibility = View.VISIBLE
                layoutTitle2!!.visibility = View.GONE
                icon_txt!!.setBackgroundResource(R.drawable.blue_bottom_icon_2)
                val frag2 = Myjob_Fragment()
                replaceFragment(frag2)
            }
            2 -> {
                ivBottom1!!.setImageResource(R.drawable.messages)
                ivBottom2!!.setImageResource(R.drawable.jobs)
                ivBottom3!!.setImageResource(R.drawable.circle_grey)
                ivBottom4!!.setImageResource(R.drawable.yellow_bottom_icon_4)
                ivBottom5!!.setImageResource(R.drawable.bottom_icon_5)
                tvTitleMain!!.text = "My Jobs Invites"
                tvTitleMain!!.setTextColor(resources.getColor(R.color.txt_header))
                icon_txt!!.visibility = View.VISIBLE
                frameLayoutNotif!!.visibility = View.VISIBLE
                layoutTitle2!!.visibility = View.GONE
                icon_txt!!.setBackgroundResource(R.drawable.blue_bottom_icon_3)
                val frag3 = Myjobs_invites()
                replaceFragment(frag3)
            }
            3 -> {
                ivBottom1!!.setImageResource(R.drawable.messages)
                ivBottom2!!.setImageResource(R.drawable.jobs)
                ivBottom3!!.setImageResource(R.drawable.circle_grey)
                ivBottom4!!.setImageResource(R.drawable.bottom_icon_4)
                ivBottom5!!.setImageResource(R.drawable.yellow_bottom_icon_5)
                val intent = Intent(this@MainNavigationActivity, my_profile::class.java)
                startActivity(intent)
            }
            5 -> {
                ivBottom1!!.setImageResource(R.drawable.messages)
                ivBottom2!!.setImageResource(R.drawable.jobs)
                ivBottom3!!.setImageResource(R.drawable.yellow_bottom_icon_3)
                ivBottom4!!.setImageResource(R.drawable.bottom_icon_4)
                ivBottom5!!.setImageResource(R.drawable.bottom_icon_5)
                tvTitleMain!!.text = "All Jobs"
                tvTitleMain!!.setTextColor(resources.getColor(R.color.txt_header))
                icon_txt!!.visibility = View.GONE
                frameLayoutNotif!!.visibility = View.VISIBLE
                layoutTitle2!!.visibility = View.GONE
                val pag2 = FragmentBrowseJobs()
                val bundle2 = Bundle()
                bundle2.putString("type", "job")
                pag2.arguments = bundle2
                replaceFragment(pag2)
                tvTitleMain!!.visibility = View.VISIBLE
                frameLayoutNotif!!.visibility = View.VISIBLE
                layoutTitle2!!.visibility = View.GONE
                back2!!.visibility = View.VISIBLE
            }
            6 -> {
                tvTitleMain!!.text = "Messages"
                tvTitleMain!!.setTextColor(resources.getColor(R.color.txt_header))
                icon_txt!!.setBackgroundResource(R.drawable.blue_bottom_icon_1)
                icon_txt!!.visibility = View.VISIBLE
                val pag6 = chat_fragment()
                replaceFragment(pag6)
            }
        }
    }

    companion object {
        private const val TAG = "MainNavigationActivity"
        private const val RQ_TRANS = 1111
    }
}