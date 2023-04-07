package com.example.instagramclone.reusable_code;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.instagramclone.R;
import com.google.android.material.snackbar.Snackbar;


public class Snackbar_Dialog {
    public Snackbar_Dialog(){


    };



    public static void showSnackbar(Context context, String message, int duration) {
        SizeBasedOnDensity size = new SizeBasedOnDensity(context);
        View rootView = ((Activity) context).findViewById(android.R.id.content);

        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setCornerRadius(20);
        gdDefault.setStroke(4, Color.BLACK,10,7);
        gdDefault.setSize(size.widthRatio(500),size.heightRatio(50));
        gdDefault.setColor(Color.parseColor("#B44166AE"));

        TSnackbar snackbar = TSnackbar.make(rootView, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackground(gdDefault);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.leftMargin=15;
        params.rightMargin=15;
        params.topMargin=50;
        snackbarView.setLayoutParams(params);
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(20);
        snackbar.show();



    }


}
