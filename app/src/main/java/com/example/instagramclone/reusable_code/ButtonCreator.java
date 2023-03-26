package com.example.instagramclone.reusable_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.instagramclone.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class ButtonCreator implements View.OnClickListener{
    int counter;
    List<Button> btnList;
    Drawable drawable;
    Context context;

    //tablelayout = where u want the buttons---array = the array of button names
    //context = xml file where your puttin buttons
    public ButtonCreator( Context context,List<Button> btnList) {
        this.context = context;
        this.btnList=btnList;



    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void buttonCreator(TableLayout tableLayout, View.OnClickListener listener,String btnName, String... array) {
        counter = 0;
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View childView = tableLayout.getChildAt(i);
            if (childView instanceof FlexboxLayout) {
                ((FlexboxLayout) childView).removeAllViews();
                // If the child view is a TableRow, remove all its child views except for the TextView
                FlexboxLayout row = (FlexboxLayout) childView;
                for (int j = 0; j < row.getChildCount(); j++) {
                    View buttonView = row.getChildAt(j);
                    if (buttonView instanceof Button) {
                        row.removeView(buttonView);

                }
            }
            }
        }


        FlexboxLayout flexboxLayout = new FlexboxLayout(context, null, 0);
        flexboxLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);
        flexboxLayout.setJustifyContent(JustifyContent.CENTER);
       // flexboxLayout.setAlignItems(AlignItems.STRETCH);

        for (String s : array) {
            Button button = new Button(context);
            String n = btnName+s;
            button.setText(n);
            button.setId(View.generateViewId());
            button.setTag(button.getText());
            button.setOnClickListener(listener);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            switch (btnName){
                case "I speak":
                    drawable = context.getResources().getDrawable(R.drawable.languages);
                    break;
                case "I am a":
                    drawable = context.getResources().getDrawable(R.drawable.gender);
                    break;
                case "I like":
                    drawable = context.getResources().getDrawable(R.drawable.interests);
                    break;
                case "I live in":
                    drawable = context.getResources().getDrawable(R.drawable.whereilive);
                break;
                default:
                    drawable = null;


            }
            if(drawable!=null) {
                drawable.setBounds(-20, 0, (int) (drawable.getIntrinsicWidth() * 0.04), (int) (drawable.getIntrinsicHeight() * 0.04));


                button.setCompoundDrawables(drawable, null, null, null);
            }

            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setCornerRadius(100);
            gdDefault.setStroke(2, context.getResources().getColor(R.color.buttonText));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                gdDefault.setPadding(40, 20, 20, 20);
            }
            button.setBackground(gdDefault);
            button.setTextColor(context.getResources().getColor(R.color.buttonText));


            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    90
            );
            layoutParams.setMargins(15, 20, 15, 20);
            btnList.add(button);

            flexboxLayout.addView(button, layoutParams);
        }

        tableLayout.addView(flexboxLayout);
    }





    @Override
    public void onClick(View v) {

    }
}
