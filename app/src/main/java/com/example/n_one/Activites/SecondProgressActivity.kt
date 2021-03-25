package com.example.n_one.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.n_one.Activites.SecondProgressActivity
import com.example.n_one.FragmentProgressTwo.*
import com.example.n_one.R
import com.example.n_one.SeeyanaApp
import com.example.n_one.UtilityClass
import com.example.n_one.objects.Provider
import com.example.n_one.utils.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.FileNotFoundException
import java.io.IOException

class SecondProgressActivity : AppCompatActivity() {
    private val TAG = "SecondProgressActivity"
    private var tvFirst: TextView? = null
    private var tvSecond: TextView? = null
    private var tvNext: TextView? = null
    private var tvComplete: TextView? = null
    private var laoyutFrame: LinearLayout? = null
    private var layoutSteps: LinearLayout? = null
    private lateinit var progressBar: ProgressBar
    private var viewBottom: View? = null
    private var viewMiddle: View? = null
    private var ivBack: ImageView? = null
    private var ivCYP: ImageView? = null
    private lateinit var firstnameEditText: EditText
    private var lastnameEditText: EditText? = null
    private lateinit var mFragmentUsername: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_second_progress)
        init()
        mFragmentUsername = FragFirstProTwo()
        replaceFragment(mFragmentUsername)
        setListener()
    }

    fun initUsernameFragment() {
        firstnameEditText = mFragmentUsername.requireView().findViewById(R.id.firstname_edit_text)
        lastnameEditText = mFragmentUsername.requireView().findViewById(R.id.lastname_edit_text)
    }

    override fun onStart() {
        Log.d(TAG, "SecondProgressActivity is starting")
        super.onStart()
    }

    private fun setListener() {
        UtilityClass.buttonEffect(tvNext) { if (flag < 9) setValues(++flag, true) }
    }

    fun setValues(flag: Int, replace: Boolean) {
        var flag = flag
        if (flag == 0) {
            tvFirst!!.text = "Step 1"
            tvSecond!!.text = "Enter your name below"
            tvComplete!!.text = "Complete your profile"
            tvNext!!.visibility = View.VISIBLE
            layoutSteps!!.visibility = View.VISIBLE
        }
        if (flag == 1) {
//            flag = 2;
            val firstname = firstnameEditText!!.text.toString().trim { it <= ' ' }
            val lastname = lastnameEditText!!.text.toString().trim { it <= ' ' }
            var proceed = true
            // if the user didn't enter firstname or lastname don't proceed to the next page
            // and prompt the user with an error to enter them.
            if (firstname.isEmpty()) {
                EditTextUtil.editTextError(firstnameEditText, "First Name can't be empty")
                flag = 0
                proceed = false
                Companion.flag = flag
            }
            if (lastname.isEmpty()) {
                EditTextUtil.editTextError(lastnameEditText, "Last Name can't be empty")
                flag = 0
                proceed = false
                Companion.flag = flag
            }
            if (proceed) {
                var provider = SeeyanaApp.getInstance().provider
                if (provider == null) {
                    provider = Provider()
                    SeeyanaApp.getInstance().provider = provider
                }
                provider.firstName = firstname
                provider.lastName = lastname
                tvFirst!!.text = "Step 2"
                tvSecond!!.text = "Select your main expertise"
                tvComplete!!.text = "Complete your profile"
                tvNext!!.visibility = View.VISIBLE
                layoutSteps!!.visibility = View.VISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.VISIBLE
                if (replace) replaceFragment(FragSecondProTwo())
            }
        } else if (flag == 2) {
//            flag = 3;
            var proceed = true
            // if the user didn't choose expertise the app doesn't proceed to the next page
            if (SeeyanaApp.getInstance().provider.mainExpertises.size == 0) {
                Toast.makeText(
                    applicationContext,
                    "You must choose your main expertise!",
                    Toast.LENGTH_LONG
                ).show()
                flag = 1
                Companion.flag = flag
                proceed = false
            }
            if (proceed) {
                tvFirst!!.text = "Step 3"
                tvSecond!!.text = "Choose your skills"
                tvComplete!!.text = "Complete your profile"
                tvNext!!.visibility = View.VISIBLE
                layoutSteps!!.visibility = View.VISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.VISIBLE
                if (replace) replaceFragment(FragThirdProTwo())
            }
        } else if (flag == 3) {
//            flag = 4;
            val skills = SeeyanaApp.getInstance().provider.skills
            var proceed = true
            if (skills.size == 0) {
                Toast.makeText(this, "You must choose one skill at least", Toast.LENGTH_LONG).show()
                proceed = false
                flag = 2
                Companion.flag = 2
            }
            if (proceed) {
                tvFirst!!.text = "Step 4"
                tvSecond!!.text = "Add photos to your portfolio (optional)"
                tvComplete!!.text = "Complete your profile"
                tvNext!!.visibility = View.VISIBLE
                layoutSteps!!.visibility = View.VISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.VISIBLE
                if (replace) replaceFragment(FragFourthProTwo())
            }
        } else if (flag == 4) {

//            flag = 5;
            var proceed = true
            val albumName = SeeyanaApp.getInstance().albumName
            val imageUris = SeeyanaApp.getInstance().imageUris
            // if user chose images for portfolio album and didn't enter album name
            // app doesn't proceed
            if (imageUris.size > 0 &&
                albumName.isEmpty()
            ) {
                Toast.makeText(
                    this@SecondProgressActivity,
                    "You must enter album name",
                    Toast.LENGTH_SHORT
                ).show()
                proceed = false
                flag = 3
                Companion.flag = 3
            }
            if (proceed) {
                Log.d(TAG, "Album Name: " + SeeyanaApp.getInstance().albumName)
                Log.d(TAG, "Number of Photos: " + SeeyanaApp.getInstance().imageUris.size)
                tvFirst!!.text = "Step 5"
                tvSecond!!.text = "Choose your location"
                tvComplete!!.text = "Complete your profile"
                tvNext!!.visibility = View.VISIBLE
                layoutSteps!!.visibility = View.VISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.VISIBLE
                if (replace) replaceFragment(FragFifthProTwo())
            }
        } else if (flag == 5) {
//            flag = 6;

            // if user didn't choose country app won't proceed
            var process = true
            val country = SeeyanaApp.getInstance().provider.country
            if (country != null && country.isEmpty()) {
                Toast.makeText(
                    this@SecondProgressActivity,
                    "You must choose your country",
                    Toast.LENGTH_SHORT
                ).show()
                process = false
                flag = 4
                Companion.flag = flag
            }
            if (process) {
                val provider = SeeyanaApp.getInstance().provider
                Log.d(TAG, "User country: " + provider.country)
                Log.d(TAG, "User city: " + provider.city)
                Log.d(TAG, "User can travel: " + provider.isWillingTravel)
                tvFirst!!.text = "Step 6"
                tvSecond!!.text = "Describe your skills and experience"
                tvComplete!!.text = "Complete your profile"
                tvNext!!.visibility = View.VISIBLE
                layoutSteps!!.visibility = View.VISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.VISIBLE
                if (replace) replaceFragment(FragSixthProTwo())
            }
        } else if (flag == 6) {
//            flag = 7;
            tvFirst!!.text = "Step 7"
            tvSecond!!.text = "Add your profile photo"
            tvComplete!!.text = "Complete your profile"
            tvNext!!.visibility = View.VISIBLE
            layoutSteps!!.visibility = View.VISIBLE
            viewBottom!!.visibility = View.VISIBLE
            viewMiddle!!.visibility = View.VISIBLE
            if (replace) replaceFragment(FragSeventhProTwo())
        } else if (flag == 7) {
//            flag = 9;
            // If the user chose a profile picture the app will display it.
            val uriProfilePic = SeeyanaApp.getInstance().uriProfilePicture
            if (uriProfilePic != null) {
                tvFirst!!.text = "Step 7"
                tvSecond!!.text = "Add your profile photo"
                tvComplete!!.text = "Complete your profile"
                tvNext!!.visibility = View.VISIBLE
                layoutSteps!!.visibility = View.VISIBLE
                ivBack!!.visibility = View.VISIBLE
                ivCYP!!.visibility = View.VISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.VISIBLE
                if (replace) replaceFragment(FragNinthProTwo())
            } else {
                setValues(8, replace)
            }
        } else if (flag == 8) {
//            flag = 10;

            // after user finish data entry register him to the database
            saveUserToFirebase()
        } else if (flag == 9) {
            Toast.makeText(this@SecondProgressActivity, "Next", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserToFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_USERS)
        val key = databaseReference.push().key
        // each time the user proceeds a page in the registration process.
        // the app saves the data to a universal provider variable
        // then here gets it to save the data to firebase database
        val provider = SeeyanaApp.getInstance().provider
        val phoneNumber = QueryPreferences.getStoredPhoneNumber(applicationContext)
        provider.phoneNumber = phoneNumber
        databaseReference.child(key!!).setValue(provider) { error, ref ->
            if (error == null) {
                Log.d(TAG, "User signed up successfully")
                QueryPreferences.setStoredUserId(applicationContext, key)
                Toast.makeText(
                    this@SecondProgressActivity,
                    "You have signed up successfully",
                    Toast.LENGTH_SHORT
                ).show()
                SeeyanaApp.getInstance().isRegistered = true
                tvFirst!!.text = ""
                tvSecond!!.text = ""
                ivBack!!.visibility = View.GONE
                ivCYP!!.visibility = View.GONE
                tvComplete!!.text = "Profile complete!"
                tvNext!!.visibility = View.INVISIBLE
                layoutSteps!!.visibility = View.INVISIBLE
                viewBottom!!.visibility = View.VISIBLE
                viewMiddle!!.visibility = View.INVISIBLE
                replaceFragment(FragTenthProTwo())

                // if the user chose portfolio images upload them to firebase
                if (SeeyanaApp.getInstance().imageUris != null &&
                    SeeyanaApp.getInstance().imageUris.size > 0
                ) {
                    uploadPortfolioAlbumPhotos()
                }
                // if the user chose a profile picture upload it to firebase
                if (SeeyanaApp.getInstance().uriProfilePicture != null) {
                    uploadProfilePicture()
                }
            } else {
                Log.e(TAG, "Failed to sign up user")
                Log.e(TAG, error.message)
                QueryPreferences.setStoredUserId(applicationContext, null)
                Toast.makeText(
                    this@SecondProgressActivity,
                    "We have encountered a problem, please try again later.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun addAlbumPhotoNameToFirebase(fileName: String) {
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference(DatabaseUtil.REF_PORTFOLIO_PHOTO_NAMES)
        val userId = QueryPreferences.getStoredUserId(applicationContext)
        val key = databaseReference.push().key
        databaseReference.child(userId).child(key!!).setValue(fileName)
    }

    private fun addProfilePictureNameToFirebase(fileName: String) {
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference(DatabaseUtil.REF_PROFILE_PICTURE_NAMES)
        val userId = QueryPreferences.getStoredUserId(applicationContext)
        val key = databaseReference.push().key
        databaseReference.child(userId).child(key!!).setValue(fileName)
    }

    private fun uploadPortfolioAlbumPhotos() {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference
        val id = QueryPreferences.getStoredUserId(applicationContext)
        val directory = DatabaseUtil.DIR_PORTFOLIO_PICTURES + "/" + id + "/"
        for (uri in SeeyanaApp.getInstance().imageUris) {
            try {
                val fileName = FileUtils.getFilenameFromURI(this@SecondProgressActivity, uri)
                val ref = storageReference.child(directory + fileName)
                Log.d(TAG, "Upload $ref")
                val bitmap = ImageUtil.getBitmapFromUri(this@SecondProgressActivity, uri)
                val data = ImageUtil.getByteArrayFromBitmap(bitmap)
                val uploadTask = ref.putBytes(data)
                uploadTask.addOnFailureListener { e ->
                    Log.d(TAG, "Failed to upload $fileName photo")
                    Log.e(TAG, e.message!!)
                }.addOnSuccessListener {
                    Log.d(TAG, "album photo $fileName uploaded successfully")
                    // adding the picture image name to the database because that helps when retrieving it by name
                    addAlbumPhotoNameToFirebase(fileName)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadProfilePicture() {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference
        val id = QueryPreferences.getStoredUserId(applicationContext)
        val directory = DatabaseUtil.DIR_PROFILE_PICTURES + "/" + id + "/"
        val uriProfilePic = SeeyanaApp.getInstance().uriProfilePicture
        try {
            val fileName = FileUtils.getFilenameFromURI(this@SecondProgressActivity, uriProfilePic)
            val ref = storageReference.child(directory + fileName)
            Log.d(TAG, "Upload profile pic: $ref")
            val bitmap = ImageUtil.getBitmapFromUri(this@SecondProgressActivity, uriProfilePic)
            val data = ImageUtil.getByteArrayFromBitmap(bitmap)
            val uploadTask = ref.putBytes(data)
            uploadTask.addOnFailureListener { e ->
                Log.d(TAG, "Failed to upload $fileName as profile picture")
                Log.e(TAG, e.message!!)
            }.addOnSuccessListener {
                Log.d(TAG, "Profile Picture $fileName uploaded successfully")
                // adding the picture file name in a table in the database make it easy to retrieve it by name
                addProfilePictureNameToFirebase(fileName)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment is FragFirstProTwo) {
            supportFragmentManager.beginTransaction().replace(laoyutFrame!!.id, fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(laoyutFrame!!.id, fragment)
                .addToBackStack(null).commit()
        }
        progressBar!!.progress = progressBar!!.progress + 10
    }

    private fun init() {
        ivBack = findViewById(R.id.ic_back)
        ivCYP = findViewById(R.id.ic_cyp)
        tvFirst = findViewById(R.id.tvFirstProSecond)
        tvSecond = findViewById(R.id.tvSecondProSecond)
        laoyutFrame = findViewById(R.id.layoutFrame)
        layoutSteps = findViewById(R.id.layoutSteps)
        tvNext = findViewById(R.id.tvNextBottom)
        tvComplete = findViewById(R.id.tvCompleteText)
        progressBar = findViewById(R.id.progressBarTwo)
        progressBar.setProgress(10)
        tvFirst!!.text = "Step 1"
        tvSecond!!.text = "Enter your name below"
        tvComplete!!.text = "Complete your profile"
        tvNext!!.visibility = View.VISIBLE
        layoutSteps!!.setVisibility(View.VISIBLE)
        viewBottom = findViewById(R.id.viewBottom)
        viewMiddle = findViewById(R.id.viewMiddle)

//        replaceFragment(mFragmentUsername);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        progressBar!!.progress = progressBar!!.progress - 10
        when (flag) {
            1 -> SeeyanaApp.getInstance().provider.mainExpertises.clear()
            2 -> {
                SeeyanaApp.getInstance().provider.skills.clear()
                SeeyanaApp.getInstance().provider.mainExpertises.clear()
            }
            3 -> SeeyanaApp.getInstance().provider.skills.clear()
            4 -> //                SeeyanaApp.getInstance().setAlbumName("");
                SeeyanaApp.getInstance().imageUris.clear()
            5 -> //                SeeyanaApp.getInstance().setAlbumName("");
                SeeyanaApp.getInstance().imageUris.clear()
        }
        setValues(--flag, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            visibleFragment!!.onActivityResult(requestCode, resultCode, data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val visibleFragment: Fragment?
        get() {
            val fragmentManager = this@SecondProgressActivity.supportFragmentManager
            val fragments = fragmentManager.fragments
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible) return fragment
            }
            return null
        }

    override fun onStop() {
        Log.d(TAG, "SecondProgressActivity is stopping")
        super.onStop()
    }

    companion object {
        var flag = 0
    }
}