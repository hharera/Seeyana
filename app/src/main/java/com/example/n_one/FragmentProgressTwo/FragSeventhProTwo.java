package com.example.n_one.FragmentProgressTwo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSeventhProTwo extends Fragment {

    private View view;
    private ImageView ivPick;
    public static Uri imageUri = null;


    public FragSeventhProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_frag_seventh_pro_two, container, false);
        init();
        setListener();
        return  view;
    }

    private void setListener() {
        ivPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallary();
            }
        });
    }

    private void init() {
        ivPick = view.findViewById(R.id.select_profile_picture_image_view);
    }

    private void pickImageFromGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        String[] mimeTypes = {"image/png", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                FragSeventhProTwo.imageUri = data.getData();
                SeeyanaApp.getInstance().setUriProfilePicture(imageUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}
