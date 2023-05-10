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

import com.example.instagramclone.ButtonTxtArraysSingleton;
import com.example.instagramclone.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;

import java.util.Arrays;
import java.util.List;

public class ButtonCreator implements View.OnClickListener{
    int counter;
    private final List<Button> btnList;
    private Drawable drawable;
    private final Context context;

    int arrayIndex;
    private final ButtonTxtArraysSingleton singletonInstance;
    private final SizeBasedOnDensity sizeBasedOnDensity;
    //btnlist = list of buttons so u can get the id's and more for pre lighting chosen buttons
    //context = xml file where your puttin buttons
    public ButtonCreator(Context context, List<Button> btnList) {
        this.context = context;
        this.btnList=btnList;


        singletonInstance = ButtonTxtArraysSingleton.getInstance();
        //for resizing button based on screen density
        sizeBasedOnDensity = new SizeBasedOnDensity(context);


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void buttonCreator(TableLayout tableLayout, View.OnClickListener listener,String... arrays) {
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


        for (String s : arrays) {

            arrayIndex =0;

            Button button = new Button(context);

            button.setText(s);
            button.setId(View.generateViewId());
            button.setTag(button.getText());
            button.setOnClickListener(listener);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            for (String[] arr : singletonInstance.arrays) {

                List<String> list = Arrays.asList(arr);
                if (list.contains(s)) {
                    switch (arrayIndex){
                        case 0:
                            drawable = context.getResources().getDrawable(R.drawable.languages);
                            break;
                        case 1:
                            drawable = context.getResources().getDrawable(R.drawable.gender);
                            break;
                        case 2:
                            drawable = context.getResources().getDrawable(R.drawable.interests);
                            break;
                        case 3:
                            drawable = context.getResources().getDrawable(R.drawable.whereilive);
                            break;
                        case 4:
                            drawable = context.getResources().getDrawable(R.drawable.smoking);
                            break;
                        case 5:
                            drawable = context.getResources().getDrawable(R.drawable.pets);
                            break;
                        case 6:
                            drawable = context.getResources().getDrawable(R.drawable.gym);
                            break;
                        case 7:
                            drawable = context.getResources().getDrawable(R.drawable.drinking);
                            break;
                        case 8:
                            drawable = context.getResources().getDrawable(R.drawable.social);
                            break;
                        case 9:
                            drawable = context.getResources().getDrawable(R.drawable.babybottle);
                            break;
                        case 10:
                            drawable = context.getResources().getDrawable(R.drawable.diet);
                            break;
                        case 11:
                            drawable = context.getResources().getDrawable(R.drawable.pronoun);
                            break;
                        case 12:
                            drawable = context.getResources().getDrawable(R.drawable.starsign);
                            break;
                        case 13:
                            drawable = context.getResources().getDrawable(R.drawable.religion);
                            break;
                        case 14:
                            drawable = context.getResources().getDrawable(R.drawable.relationship);
                            break;
                        case 15:
                            drawable = context.getResources().getDrawable(R.drawable.sexualorientation);
                            break;
                        case 16:
                            drawable = context.getResources().getDrawable(R.drawable.ruler);
                            break;
                        default:
                            drawable = null;
                }
            }
                arrayIndex++;
            }
            if(drawable!=null) {
                drawable.setBounds(-20, 1, sizeBasedOnDensity.widthRatio(20), sizeBasedOnDensity.heightRatio(20));


                button.setCompoundDrawables(drawable, null, null, null);
            }

            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setCornerRadius(100);
            gdDefault.setStroke(2, context.getResources().getColor(R.color.buttonText));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                gdDefault.setPadding(40, 0, 20, 0);
            }
            button.setBackground(gdDefault);
            button.setTextColor(context.getResources().getColor(R.color.buttonText));


            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    sizeBasedOnDensity.heightRatio(30)
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
