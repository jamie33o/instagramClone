package com.example.instagramclone.reusable_code;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.instagramclone.R;


public class Dialogs {
    public Dialogs(){


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

    public static void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener yesClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.personal_details_dialog);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("YES", yesClickListener)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
