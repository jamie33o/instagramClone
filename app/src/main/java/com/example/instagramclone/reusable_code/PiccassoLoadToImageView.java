package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.example.instagramclone.edit_profile_N_profile.RoundedCornersTransformation;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.parse.Parse;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PiccassoLoadToImageView {
    SharedPreferencesManager sharedPreferencesManager;
    public static boolean hasimage1,hasimage2,hasimage3;
    String file;

    public PiccassoLoadToImageView(Context context){

        sharedPreferencesManager = new SharedPreferencesManagerImpl(context,"Profile", Context.MODE_PRIVATE);

        hasimage1 = false;
        hasimage2 = false;
        hasimage3 = false;




    }

    public void getImageNloadIntoImageview(ImageView view, String fileName, int width, int height, int radius) {


        GradientDrawable gdDefault = new GradientDrawable();


        gdDefault.setCornerRadius(radius);
        gdDefault.setStroke(5, Color.GRAY, 10, 10);
        gdDefault.setColor(Color.parseColor("#EDEDF7"));
        gdDefault.setSize(width, height);


        if(fileName.equals("image1")||fileName.equals("image2")||fileName.equals("image3")) {
             file = sharedPreferencesManager.getString(fileName, "null");
        }else {
            file = fileName;
        }

        if(fileName.equals("image1") && !file.equals("null"))
            hasimage1 = true;
        if(fileName.equals("image2")&& !file.equals("null"))
            hasimage2 = true;
        if(fileName.equals("image3")&& !file.equals("null"))
            hasimage3 = true;

        File file1= new File(file);

            Picasso.get()
                    .load(file1)
                    .transform(new RoundedCornersTransformation(radius, 0))
                    .placeholder(gdDefault)
                    .resize(width, height)
                    .centerCrop()
                    .into(view);

    }


}
