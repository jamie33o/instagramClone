package com.example.instagramclone.choices_tabs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.instagramclone.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;


public class AddBtnTxtToArray {
    Button button1;
    int btnClickedCounter;
    Context context;
    public AddBtnTxtToArray(Context context){
        this.context=context;


    }

    public void addBtnClickedTxtToArray(List<String> selectedButtonsList,List<Button> buttonList, View v, int amountAllowed) {
        int buttonId = v.getId();
        Button btn = v.findViewById(buttonId);
        String buttonText = btn.getText().toString();

        if(amountAllowed ==1)
            for (Button b : buttonList){
                String btnt = b.getText().toString();
                if(selectedButtonsList.contains(btnt)){
                    button1 = b;
                }
            }

        btnClickedCounter=selectedButtonsList.size();

        GradientDrawable drawable = (GradientDrawable) btn.getBackground();
        if (selectedButtonsList.contains(buttonText)) {//used to add and remove button text from array

            selectedButtonsList.remove(buttonText);
            btnClickedCounter--;
            drawable.setStroke(2, context.getResources().getColor(R.color.buttonText));
            btn.setTextColor(context.getResources().getColor(R.color.buttonText));

        } else {
            if (amountAllowed==1 && selectedButtonsList.size() >0 && button1 != null) {
                String btn1Text = button1.getText().toString();
                selectedButtonsList.remove(btn1Text);//removes the previously selected button text from the array
                GradientDrawable drawable1 = (GradientDrawable) button1.getBackground();
                drawable1.setStroke(2, context.getResources().getColor(R.color.buttonText));
                button1.setTextColor(context.getResources().getColor(R.color.buttonText));

            }
                if(btnClickedCounter == amountAllowed&& amountAllowed!=1){

                    FancyToast.makeText(context, amountAllowed + " is the maximum amount allowed", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                }else {

                    selectedButtonsList.add(buttonText);
                    btnClickedCounter++;
                    drawable.setStroke(2, context.getResources().getColor(R.color.selected_button));
                    btn.setTextColor(context.getResources().getColor(R.color.selected_button));
                }

            }




    }


}
