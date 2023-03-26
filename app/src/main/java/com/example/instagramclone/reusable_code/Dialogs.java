package com.example.instagramclone.reusable_code;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.instagramclone.R;
import com.shashank.sony.fancytoastlib.FancyToast;


public class Dialogs {
    ProgressDialog dialog;
public Dialogs(){


}



    public void showProgressDialog(Context context) {
        dialog = new ProgressDialog(context, R.style.TransparentProgressDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    public void dismissProgressDialog(Context context,String message,boolean type) {
        try {
            if (dialog != null && dialog.isShowing() && dialog.getWindow() != null&&type) {
                dialog.dismiss();
                FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

            }else if(dialog != null && dialog.isShowing() && dialog.getWindow() != null&&!type){
                FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();

            }
        } catch (IllegalArgumentException e) {
            // Handle the exception
        }
    }

}
