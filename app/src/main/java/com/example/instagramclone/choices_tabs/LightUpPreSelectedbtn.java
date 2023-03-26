package com.example.instagramclone.choices_tabs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.instagramclone.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;
import java.util.List;

public class LightUpPreSelectedbtn {


    Context context;
   public LightUpPreSelectedbtn(Context context){
       this.context = context;


   }


    public void lightUpPreSelectBtn(List<String> prefList,List<Button> buttonArray) {

       System.out.println(Arrays.asList(prefList));
       if(prefList != null) {
           for (Button btn : buttonArray) {
               String btnText = btn.getText().toString();
               if (prefList.contains(btnText)) {
                   GradientDrawable drawable = (GradientDrawable) btn.getBackground();
                   drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                   btn.setTextColor(context.getResources().getColor(R.color.selected_button));
                  // selectedButton.add(btnText);
               }
           }
       }


    }
}
  /*  if (prefList != null ) {
            for (int i = 0; i < tb.getChildCount(); i++) {
                View childView = tb.getChildAt(i);
                if (childView instanceof FlexboxLayout) {
                    FlexboxLayout row = (FlexboxLayout) childView;
                    for (int j = 0; j < row.getChildCount(); j++) {
                        View buttonView = row.getChildAt(j);
                        if (buttonView instanceof Button) {
                            int id = buttonView.getId();
                            Button v = view.findViewById(id);
                            String btnTxt = v.getText().toString();
                            if (prefList.contains(btnTxt)) {
                                GradientDrawable drawable = (GradientDrawable) v.getBackground();
                                drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                                v.setTextColor(context.getResources().getColor(R.color.selected_button));
                                selectedButton.add(btnTxt);
                            }
                        }
                    }
                }
            }
        }
*/