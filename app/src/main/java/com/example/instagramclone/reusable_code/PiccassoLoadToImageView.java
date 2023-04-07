package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.edit_profile_N_profile.RoundedCornersTransformation;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;

public class PiccassoLoadToImageView {
    public static boolean hasimage1,hasimage2,hasimage3;

    File file;
    Realm realm;
    RealmModel results;

Context context;
    public PiccassoLoadToImageView(Context context){

        this.context=context;

        hasimage1 = false;
        hasimage2 = false;
        hasimage3 = false;

        realm = RealmManager.getRealmInstance();
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();





    }

    public void getImageNloadIntoImageview(ImageView view,String fileName,String imagename, int width, int height, int radius) {
        GradientDrawable gdDefault = new GradientDrawable();


        gdDefault.setCornerRadius(radius);
        gdDefault.setStroke(5, Color.GRAY, 10, 10);
        gdDefault.setColor(Color.parseColor("#EDEDF7"));
        gdDefault.setSize(width, height);



        if(fileName!=null&&!fileName.equals("")&&imagename.equals("image1"))
            hasimage1 = true;
        if(fileName!=null&&!fileName.equals("")&&imagename.equals("image2"))
            hasimage2 = true;
        if(fileName!=null&&!fileName.equals("")&&imagename.equals("image3"))
            hasimage3 = true;


        if(fileName!=null) {
            file = new File(fileName);
        }else {
            file = new File("");
        }
            Picasso.get()
                    .load(file)
                    .transform(new RoundedCornersTransformation(radius, 0))
                    .placeholder(gdDefault)
                    .resize(width, height)
                    .centerCrop()
                    .into(view);
        }



}
