package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;

import com.example.instagramclone.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.Objects;


public class AddBtnTxtToArray {
    Button button1;
    String buttonClicked;
    int btnClickedCounter;
    Context context;
    public AddBtnTxtToArray(Context context){
        this.context=context;


    }

    public void addBtnClickedTxtToArray(List<String> selectedButtonsList, View v, int amountAllowed) {
        int buttonId = v.getId();
        Button btn = v.findViewById(buttonId);
        String buttonText = btn.getText().toString();


        if(!selectedButtonsList.isEmpty())
           btnClickedCounter=selectedButtonsList.size();

        GradientDrawable drawable = (GradientDrawable) btn.getBackground();
        if (selectedButtonsList.contains(buttonText)) {//used to add and remove button text from array

            selectedButtonsList.remove(buttonText);
            btnClickedCounter--;
            drawable.setStroke(2, context.getResources().getColor(R.color.buttonText));
            btn.setTextColor(context.getResources().getColor(R.color.buttonText));

        } else {

            if(btnClickedCounter == amountAllowed){

                FancyToast.makeText(context, amountAllowed + " is the maximum amount allowed", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

            }else {

                selectedButtonsList.add(buttonText);
                btnClickedCounter++;
                drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                btn.setTextColor(context.getResources().getColor(R.color.selected_button));
            }

        }




    }
    public String addBtnClickedTxttoString(String selectedButtonsString, List<Button> buttonList, View v) {
        int buttonId = v.getId();
        Button btn = v.findViewById(buttonId);
        String buttonText = btn.getText().toString();

        if(selectedButtonsString!=null&& !selectedButtonsString.equals(buttonText)) {
            for (Button b : buttonList) {
                String btnt = b.getText().toString();
                if (selectedButtonsString.equals(btnt)) {
                    button1 = b;
                }
            }
        }
            GradientDrawable drawable = (GradientDrawable) btn.getBackground();
            if (Objects.equals(selectedButtonsString, buttonText)) {//used to add and remove button text from array

                drawable.setStroke(2, context.getResources().getColor(R.color.buttonText));
                btn.setTextColor(context.getResources().getColor(R.color.buttonText));

              buttonClicked = "";
            } else {
                buttonClicked = buttonText;

                if(button1!=null) {
                    GradientDrawable drawable1 = (GradientDrawable) button1.getBackground();
                    drawable1.setStroke(2, context.getResources().getColor(R.color.buttonText));
                    button1.setTextColor(context.getResources().getColor(R.color.buttonText));
                }

                    drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                    btn.setTextColor(context.getResources().getColor(R.color.selected_button));


            }

            return buttonClicked;

        }


    }

