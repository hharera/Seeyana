package com.example.n_one.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.n_one.ClickEvent;
import com.example.n_one.FragmentProgressTwo.FragFifthProTwo;
import com.example.n_one.FragmentProgressTwo.FragFirstProTwo;
import com.example.n_one.FragmentProgressTwo.FragFourthProTwo;
import com.example.n_one.FragmentProgressTwo.FragNinthProTwo;
import com.example.n_one.FragmentProgressTwo.FragSecondProTwo;
import com.example.n_one.FragmentProgressTwo.FragSeventhProTwo;
import com.example.n_one.FragmentProgressTwo.FragSixthProTwo;
import com.example.n_one.FragmentProgressTwo.FragTenthProTwo;
import com.example.n_one.FragmentProgressTwo.FragThirdProTwo;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.UtilityClass;
import com.example.n_one.objects.Provider;
import com.example.n_one.utils.DatabaseUtil;
import com.example.n_one.utils.EditTextUtil;
import com.example.n_one.utils.FileUtils;
import com.example.n_one.utils.ImageUtil;
import com.example.n_one.utils.QueryPreferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class SecondProgressActivity extends AppCompatActivity {
    private final String TAG = "SecondProgressActivity";
    private TextView tvFirst, tvSecond, tvNext, tvComplete;
    private LinearLayout laoyutFrame, layoutSteps;
    private ProgressBar progressBar;
    public static int flag = 0;
    private View viewBottom, viewMiddle;
    private ImageView ivBack, ivCYP;
    private EditText firstnameEditText, lastnameEditText;
    private Fragment mFragmentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second_progress);


        init();
        mFragmentUsername = new FragFirstProTwo();
        replaceFragment(mFragmentUsername);
        setListener();

    }

    public void initUsernameFragment() {
        firstnameEditText = mFragmentUsername.getView().findViewById(R.id.firstname_edit_text);
        lastnameEditText = mFragmentUsername.getView().findViewById(R.id.lastname_edit_text);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "SecondProgressActivity is starting");

        super.onStart();
    }

    private void setListener() {
        UtilityClass.buttonEffect(tvNext, new ClickEvent() {
            @Override
            public void getActionUpClicked(View view) {
                if (flag < 9)
                    setValues(++flag, true);
            }
        });
    }

    public void setValues(int flag, boolean replace) {
        if (flag == 0){
            tvFirst.setText("Step 1");
            tvSecond.setText("Enter your name below");
            tvComplete.setText("Complete your profile");
            tvNext.setVisibility(View.VISIBLE);
            layoutSteps.setVisibility(View.VISIBLE);
        }
        if (flag == 1) {
//            flag = 2;
            String firstname = firstnameEditText.getText().toString().trim();
            String lastname = lastnameEditText.getText().toString().trim();
            boolean proceed = true;
            // if the user didn't enter firstname or lastname don't proceed to the next page
            // and prompt the user with an error to enter them.
            if (firstname.isEmpty()) {
                EditTextUtil.editTextError(firstnameEditText, "First Name can't be empty");
                flag = 0;
                proceed = false;
                SecondProgressActivity.flag = flag;
            }
            if (lastname.isEmpty()) {
                EditTextUtil.editTextError(lastnameEditText, "Last Name can't be empty");
                flag = 0;
                proceed = false;
                SecondProgressActivity.flag = flag;
            }

            if (proceed) {
                Provider provider = SeeyanaApp.getInstance().getProvider();
                if (provider == null) {
                    provider = new Provider();
                    SeeyanaApp.getInstance().setProvider(provider);
                }
                provider.setFirstName(firstname);
                provider.setLastName(lastname);
                tvFirst.setText("Step 2");
                tvSecond.setText("Select your main expertise");
                tvComplete.setText("Complete your profile");
                tvNext.setVisibility(View.VISIBLE);
                layoutSteps.setVisibility(View.VISIBLE);
                viewBottom.setVisibility(View.VISIBLE);
                viewMiddle.setVisibility(View.VISIBLE);
                if (replace)
                    replaceFragment(new FragSecondProTwo());
            }

        } else if (flag == 2) {
//            flag = 3;
            boolean proceed = true;
            // if the user didn't choose expertise the app doesn't proceed to the next page
            if (SeeyanaApp.getInstance().getProvider().getMainExpertises().size() == 0) {
                Toast.makeText(getApplicationContext(), "You must choose your main expertise!", Toast.LENGTH_LONG).show();
                flag = 1;
                SecondProgressActivity.flag = flag;
                proceed = false;
            }
            if (proceed) {
                tvFirst.setText("Step 3");
                tvSecond.setText("Choose your skills");
                tvComplete.setText("Complete your profile");
                tvNext.setVisibility(View.VISIBLE);
                layoutSteps.setVisibility(View.VISIBLE);
                viewBottom.setVisibility(View.VISIBLE);
                viewMiddle.setVisibility(View.VISIBLE);
                if (replace)
                    replaceFragment(new FragThirdProTwo());
            }
        } else if (flag == 3) {
//            flag = 4;
            List<String> skills = SeeyanaApp.getInstance().getProvider().getSkills();
            boolean proceed = true;
            if (skills.size() == 0) {
                Toast.makeText(this, "You must choose one skill at least", Toast.LENGTH_LONG).show();
                proceed = false;
                flag = 2;
                SecondProgressActivity.flag = 2;
            }
            if (proceed) {
                tvFirst.setText("Step 4");
                tvSecond.setText("Add photos to your portfolio (optional)");
                tvComplete.setText("Complete your profile");
                tvNext.setVisibility(View.VISIBLE);
                layoutSteps.setVisibility(View.VISIBLE);
                viewBottom.setVisibility(View.VISIBLE);
                viewMiddle.setVisibility(View.VISIBLE);
                if (replace)
                    replaceFragment(new FragFourthProTwo());
            }
        } else if (flag == 4) {

//            flag = 5;
            boolean proceed = true;
            String albumName = SeeyanaApp.getInstance().getAlbumName();
            List<Uri> imageUris = SeeyanaApp.getInstance().getImageUris();
            // if user chose images for portfolio album and didn't enter album name
            // app doesn't proceed
            if (imageUris.size() > 0 &&
                    albumName.isEmpty()) {
                Toast.makeText(SecondProgressActivity.this, "You must enter album name", Toast.LENGTH_SHORT).show();
                proceed = false;
                flag = 3;
                SecondProgressActivity.flag = 3;
            }

            if (proceed) {
                Log.d(TAG, "Album Name: " + SeeyanaApp.getInstance().getAlbumName());
                Log.d(TAG, "Number of Photos: " + SeeyanaApp.getInstance().getImageUris().size());
                tvFirst.setText("Step 5");
                tvSecond.setText("Choose your location");
                tvComplete.setText("Complete your profile");
                tvNext.setVisibility(View.VISIBLE);
                layoutSteps.setVisibility(View.VISIBLE);
                viewBottom.setVisibility(View.VISIBLE);
                viewMiddle.setVisibility(View.VISIBLE);
                if (replace)
                    replaceFragment(new FragFifthProTwo());
            }
        } else if (flag == 5) {
//            flag = 6;

            // if user didn't choose country app won't proceed
        boolean process = true;
        String country = SeeyanaApp.getInstance().getProvider().getCountry();
        if (country == null && country.isEmpty()) {
            Toast.makeText(SecondProgressActivity.this, "You must choose your country", Toast.LENGTH_SHORT).show();
            process = false;
            flag = 4;
            SecondProgressActivity.flag = flag;
        }

        if (process) {
            Provider provider = SeeyanaApp.getInstance().getProvider();
            Log.d(TAG, "User country: " + provider.getCountry());
            Log.d(TAG, "User city: " + provider.getCity());
            Log.d(TAG, "User can travel: " + provider.isWillingTravel());

            tvFirst.setText("Step 6");
            tvSecond.setText("Describe your skills and experience");
            tvComplete.setText("Complete your profile");
            tvNext.setVisibility(View.VISIBLE);
            layoutSteps.setVisibility(View.VISIBLE);
            viewBottom.setVisibility(View.VISIBLE);
            viewMiddle.setVisibility(View.VISIBLE);
            if (replace)
                replaceFragment(new FragSixthProTwo());
        }
        } else if (flag == 6) {
//            flag = 7;
            tvFirst.setText("Step 7");
            tvSecond.setText("Add your profile photo");
            tvComplete.setText("Complete your profile");
            tvNext.setVisibility(View.VISIBLE);
            layoutSteps.setVisibility(View.VISIBLE);
            viewBottom.setVisibility(View.VISIBLE);
            viewMiddle.setVisibility(View.VISIBLE);
            if (replace)
                replaceFragment(new FragSeventhProTwo());
        } else if (flag == 7) {
//            flag = 9;
            // If the user chose a profile picture the app will display it.
            Uri uriProfilePic = SeeyanaApp.getInstance().getUriProfilePicture();
            if (uriProfilePic != null) {
                tvFirst.setText("Step 7");
                tvSecond.setText("Add your profile photo");
                tvComplete.setText("Complete your profile");
                tvNext.setVisibility(View.VISIBLE);
                layoutSteps.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                ivCYP.setVisibility(View.VISIBLE);
                viewBottom.setVisibility(View.VISIBLE);
                viewMiddle.setVisibility(View.VISIBLE);
                if (replace)
                    replaceFragment(new FragNinthProTwo());
            } else {
                setValues(8, replace);
            }
        } else if (flag == 8) {
//            flag = 10;

            // after user finish data entry register him to the database
            saveUserToFirebase();
        } else if (flag == 9) {
            Toast.makeText(SecondProgressActivity.this, "Next", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserToFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(DatabaseUtil.REF_USERS);
        final String key = databaseReference.push().getKey();
        // each time the user proceeds a page in the registration process.
        // the app saves the data to a universal provider variable
        // then here gets it to save the data to firebase database
        Provider provider = SeeyanaApp.getInstance().getProvider();
        String phoneNumber = QueryPreferences.getStoredPhoneNumber(getApplicationContext());
        provider.setPhoneNumber(phoneNumber);
        databaseReference.child(key).setValue(provider, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Log.d(TAG, "User signed up successfully");
                    QueryPreferences.setStoredUserId(getApplicationContext(), key);
                    Toast.makeText(SecondProgressActivity.this, "You have signed up successfully", Toast.LENGTH_SHORT).show();
                    SeeyanaApp.getInstance().setRegistered(true);

                    tvFirst.setText("");
                    tvSecond.setText("");
                    ivBack.setVisibility(View.GONE);
                    ivCYP.setVisibility(View.GONE);
                    tvComplete.setText("Profile complete!");
                    tvNext.setVisibility(View.INVISIBLE);
                    layoutSteps.setVisibility(View.INVISIBLE);
                    viewBottom.setVisibility(View.VISIBLE);
                    viewMiddle.setVisibility(View.INVISIBLE);
                    replaceFragment(new FragTenthProTwo());

                    // if the user chose portfolio images upload them to firebase
                    if (SeeyanaApp.getInstance().getImageUris() != null &&
                    SeeyanaApp.getInstance().getImageUris().size() > 0) {
                        uploadPortfolioAlbumPhotos();
                    }
                    // if the user chose a profile picture upload it to firebase
                    if (SeeyanaApp.getInstance().getUriProfilePicture() != null) {
                        uploadProfilePicture();
                    }
                } else {
                    Log.e(TAG, "Failed to sign up user");
                    Log.e(TAG, error.getMessage());
                    QueryPreferences.setStoredUserId(getApplicationContext(), null);
                    Toast.makeText(SecondProgressActivity.this, "We have encountered a problem, please try again later.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addAlbumPhotoNameToFirebase(String fileName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(DatabaseUtil.REF_PORTFOLIO_PHOTO_NAMES);
        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        String key = databaseReference.push().getKey();
        databaseReference.child(userId).child(key).setValue(fileName);
    }

    private void addProfilePictureNameToFirebase(String fileName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(DatabaseUtil.REF_PROFILE_PICTURE_NAMES);
        String userId = QueryPreferences.getStoredUserId(getApplicationContext());
        String key = databaseReference.push().getKey();
        databaseReference.child(userId).child(key).setValue(fileName);
    }

    private void uploadPortfolioAlbumPhotos() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        String id = QueryPreferences.getStoredUserId(getApplicationContext());
        String directory = DatabaseUtil.DIR_PORTFOLIO_PICTURES + "/" + id + "/";

        for (Uri uri: SeeyanaApp.getInstance().getImageUris()) {
            try {
                String fileName  = FileUtils.getFilenameFromURI(SecondProgressActivity.this, uri);
                StorageReference ref = storageReference.child(directory + fileName);
                Log.d(TAG, "Upload " + ref.toString());

                Bitmap bitmap = ImageUtil.getBitmapFromUri(SecondProgressActivity.this, uri);
                byte[] data = ImageUtil.getByteArrayFromBitmap(bitmap);

                UploadTask uploadTask = ref.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failed to upload " + fileName + " photo");
                        Log.e(TAG, e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "album photo " + fileName + " uploaded successfully");
                        // adding the picture image name to the database because that helps when retrieving it by name
                        addAlbumPhotoNameToFirebase(fileName);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void uploadProfilePicture() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        String id = QueryPreferences.getStoredUserId(getApplicationContext());
        String directory = DatabaseUtil.DIR_PROFILE_PICTURES + "/" + id + "/";
        Uri uriProfilePic = SeeyanaApp.getInstance().getUriProfilePicture();
        try {
            String fileName = FileUtils.getFilenameFromURI(SecondProgressActivity.this, uriProfilePic);
            StorageReference ref = storageReference.child(directory + fileName);
            Log.d(TAG, "Upload profile pic: " + ref.toString());

            Bitmap bitmap = ImageUtil.getBitmapFromUri(SecondProgressActivity.this, uriProfilePic);
            byte[] data = ImageUtil.getByteArrayFromBitmap(bitmap);

            UploadTask uploadTask = ref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Failed to upload " + fileName + " as profile picture");
                    Log.e(TAG, e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "Profile Picture " + fileName + " uploaded successfully");
                    // adding the picture file name in a table in the database make it easy to retrieve it by name
                    addProfilePictureNameToFirebase(fileName);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment instanceof FragFirstProTwo) {
            getSupportFragmentManager().beginTransaction().replace(laoyutFrame.getId(), fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(laoyutFrame.getId(), fragment).addToBackStack(null).commit();
        }
        progressBar.setProgress(progressBar.getProgress() + 10);
    }

    private void init() {
        ivBack = findViewById(R.id.ic_back);
        ivCYP = findViewById(R.id.ic_cyp);
        tvFirst = findViewById(R.id.tvFirstProSecond);
        tvSecond = findViewById(R.id.tvSecondProSecond);
        laoyutFrame = findViewById(R.id.layoutFrame);
        layoutSteps = findViewById(R.id.layoutSteps);
        tvNext = findViewById(R.id.tvNextBottom);
        tvComplete = findViewById(R.id.tvCompleteText);
        progressBar = findViewById(R.id.progressBarTwo);
        progressBar.setProgress(10);
        tvFirst.setText("Step 1");
        tvSecond.setText("Enter your name below");
        tvComplete.setText("Complete your profile");
        tvNext.setVisibility(View.VISIBLE);
        layoutSteps.setVisibility(View.VISIBLE);
        viewBottom = findViewById(R.id.viewBottom);
        viewMiddle = findViewById(R.id.viewMiddle);

//        replaceFragment(mFragmentUsername);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressBar.setProgress(progressBar.getProgress() - 10);
        // clears a page data when user clics back
        switch (flag) {
            case 1:
                SeeyanaApp.getInstance().getProvider().getMainExpertises().clear();
                break;

            case 2:
                SeeyanaApp.getInstance().getProvider().getSkills().clear();
                SeeyanaApp.getInstance().getProvider().getMainExpertises().clear();
                break;

            case 3:
                SeeyanaApp.getInstance().getProvider().getSkills().clear();
                break;

            case 4:
//                SeeyanaApp.getInstance().setAlbumName("");
                SeeyanaApp.getInstance().getImageUris().clear();
                break;

            case 5:
//                SeeyanaApp.getInstance().setAlbumName("");
                SeeyanaApp.getInstance().getImageUris().clear();
                break;
        }

            setValues((--flag), false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            getVisibleFragment().onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = SecondProgressActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "SecondProgressActivity is stopping");
        super.onStop();
    }
}
