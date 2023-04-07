package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.instagramclone.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class LightUpPreSelectedbtn {


    Context context;
   public LightUpPreSelectedbtn(Context context){
       this.context = context;


   }

    public void lightUpPreSelectBtn(String prefList,List<Button> buttonArray) {



        if(prefList != null) {
            for (Button btn : buttonArray) {
                String btnText = btn.getText().toString();
                if (prefList.contains(btnText)) {
                    GradientDrawable drawable = (GradientDrawable) btn.getBackground();
                    drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                    btn.setTextColor(context.getResources().getColor(R.color.selected_button));
                }
            }
        }


    }

    public void lightUpPreSelectBtn(List<String> prefList,List<Button> buttonArray) {

       if(prefList != null) {
           for (Button btn : buttonArray) {
               String btnText = btn.getText().toString();
               if (prefList.contains(btnText)) {
                   GradientDrawable drawable = (GradientDrawable) btn.getBackground();
                   drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                   btn.setTextColor(context.getResources().getColor(R.color.selected_button));
               }
           }
       }


    }
}
