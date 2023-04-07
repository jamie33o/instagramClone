package com.example.instagramclone.main_tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.reusable_code.SizeBasedOnDensity;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import com.example.instagramclone.edit_profile_N_profile.ProfilePage;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.edit_profile_N_profile.EditProfile;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_database_queries.UtilsClass;

import java.util.ArrayList;

import io.realm.Realm;

public class ProfileTab extends Fragment implements View.OnClickListener {

    ButtonCreator buttonCreator;

    public ImageView profileImgView,editProfilebtn;

    String image1;
    private LinearLayout dotsLayout;
    private ImageView image, image2, image3;
    private View dot1, dot2, dot3;

    private int currentImage = 1;
    Handler handler1;
    Runnable runnable;
    Realm realm;
    RealmModel results;

    public ProfileTab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                container, false);


        realm = Realm.getDefaultInstance();

        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();

        buttonCreator = new ButtonCreator(getContext(),new ArrayList<>());

        SizeBasedOnDensity sizeBasedOnDensity = new SizeBasedOnDensity(view.getContext());
        editProfilebtn = view.findViewById(R.id.editProfileBtn);
        editProfilebtn.setOnClickListener(this);


        profileImgView = view.findViewById(R.id.profileImgView);

        profileImgView.setOnClickListener(this);


        realm.beginTransaction();

        if(results!= null)
         image1 = results.getImage1Name();

        PiccassoLoadToImageView piccassoLoadToImageView = new PiccassoLoadToImageView(getContext());
        piccassoLoadToImageView.getImageNloadIntoImageview(profileImgView,image1,"",sizeBasedOnDensity.widthRatio(220),sizeBasedOnDensity.heightRatio(220),300);

        realm.commitTransaction();

        dotsLayout = view.findViewById(R.id.dotsLayout);
        image =  view.findViewById(R.id.image1);
        image2 =  view.findViewById(R.id.image2);
        image3 =  view.findViewById(R.id.image3);
        dot1 =  view.findViewById(R.id.dot1);
        dot2 =  view.findViewById(R.id.dot2);
        dot3 =  view.findViewById(R.id.dot3);

        // Set initial state
        image.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
        image3.setVisibility(View.GONE);
           dot1.setBackgroundColor(getResources().getColor(R.color.purple_200));;
           dot2.setBackgroundColor(getResources().getColor(R.color.purple_200));
           dot3.setBackgroundColor(getResources().getColor(R.color.purple_200));





        // Start automatic image switching
         handler1 = new Handler();

       runnable = new Runnable() {
            public void run() {
                if(isAdded())
                    switchImage();

                handler1.postDelayed(this, 3000); // Switch image every 2 seconds
            }
        };


        handler1.removeCallbacksAndMessages(null);
        handler1.postDelayed(runnable, 3000);

        return view;//must return type view

    }


    private void switchImage() {

        switch (currentImage) {
            case 1:
                image.setVisibility(View.GONE);
                image2.setVisibility(View.VISIBLE);
                dot1.setBackgroundColor(getResources().getColor(R.color.purple_200));
                dot2.setBackgroundColor(getResources().getColor(R.color.white));
                currentImage = 2;
                break;
            case 2:
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.VISIBLE);
                dot2.setBackgroundColor(getResources().getColor(R.color.purple_200));
                dot3.setBackgroundColor(getResources().getColor(R.color.white));
                currentImage = 3;
                break;
            case 3:
                image3.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                dot3.setBackgroundColor(getResources().getColor(R.color.purple_200));
                dot1.setBackgroundColor(getResources().getColor(R.color.white));
                currentImage = 1;
                break;
        }



    }



    @Override
    public void onClick(View view) {//for uploading image


        int viewId = view.getId();

        if (viewId == R.id.editProfileBtn ) {
            Intent intent = new Intent(getContext(), EditProfile.class);
            startActivity(intent);

        }

        if (viewId == R.id.profileImgView) {

            Intent intent = new Intent(getContext(), ProfilePage.class);
            startActivity(intent);
        }


    }

}