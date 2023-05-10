package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.ProfileTab.RoundedCornersTransformation;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

public class PiccassoLoadToImageView {


    File file;
    Context context;
    public PiccassoLoadToImageView(Context context){

        this.context=context;

    }

    public void getImageNloadIntoImageview(String fileName, ImageView view, int width, int height, int radius) {
        GradientDrawable gdDefault = new GradientDrawable();




        gdDefault.setCornerRadius(radius);
        gdDefault.setStroke(5, Color.GRAY, 10, 10);
        gdDefault.setColor(Color.parseColor("#EDEDF7"));
        gdDefault.setSize(width, height);


        file = new File(Objects.requireNonNullElse(fileName, ""));

        Picasso.get()
                .load(file)
                .transform(new RoundedCornersTransformation(radius, 0))
                .placeholder(gdDefault)
                .resize(width, height)
                .centerCrop()
                .into(view);
    }

    public void getImageNloadIntoImageview(String fileName, ImageView view) {

        file = new File(Objects.requireNonNullElse(fileName, ""));

        Picasso.get()
                .load(file)
                .placeholder(R.drawable.instagramlogo)
                .centerCrop()
                .fit()
                .into(view);
    }


}
