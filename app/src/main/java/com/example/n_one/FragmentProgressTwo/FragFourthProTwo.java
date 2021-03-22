package com.example.n_one.FragmentProgressTwo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;
import com.example.n_one.utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragFourthProTwo extends Fragment {

    private static final String TAG = "FragFourthProTwo";

    private View view;
    private ImageView ivPicAdd;
    private LinearLayout layoutImages;
    private EditText mEditTextAlbumName;

    public FragFourthProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_fourth_pro_two, container, false);
        init();

        setListener();
        return view;
    }

    private void setListener() {
        ivPicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickImageFromGallary();

//                Dexter.withActivity(getActivity())
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener()
//                                                                                                                          {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        pickImageFromGallary();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        // check for permanent denial of permission
//                        if (response.isPermanentlyDenied()) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }}
//                ).check();

            }
        });
    }

    private void init() {
        ivPicAdd = view.findViewById(R.id.ivAddPicture);
        layoutImages = view.findViewById(R.id.layoutImages);
        mEditTextAlbumName = view.findViewById(R.id.album_name_edit_text);
        mEditTextAlbumName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SeeyanaApp.getInstance().setAlbumName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void pickImageFromGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);

//        Intent intent = new Intent();
        intent.setType("image/*");
        String[] mimeTypes = {"image/png", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
//                Map<String, List<Uri>> album = SeeyanaApp.getInstance().getAlbum();
//                if (album == null) {
//                    album = new HashMap<>();
//                }
//                String albumName = mEditTextAlbumName.getText().toString().trim();


                List<Uri> imageUris = SeeyanaApp.getInstance().getImageUris();
                if (imageUris == null) {
                    imageUris = new ArrayList<>();
                    SeeyanaApp.getInstance().setImageUris(imageUris);
                }

                if (imageUris.size() <= 4) {
                    try {
                        Log.d(TAG, "Uri file name: " + FileUtils.getFilenameFromURI(getActivity(), uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUris.add(uri);
                    Log.d(TAG, "User choose image: " + uri);
                    ImageView imageView = new ImageView(getActivity());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(20,0,0,0);
//                layoutImages.setPadding(20,0,0,0);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    layoutImages.addView(imageView);
                    imageView.setImageURI(uri);

//                ivPicAdd.setImageURI(uri);
                } else {

                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        super.onStart();
        String albumName = mEditTextAlbumName.getText().toString().trim();
        SeeyanaApp.getInstance().setAlbumName(albumName);
    }
}
