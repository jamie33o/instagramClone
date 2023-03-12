package com.example.instagramclone;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;

public class ButtonCreator implements View.OnClickListener{
    int counter;
    GradientDrawable gdDefault;
    Context context;
    View.OnClickListener listener;

    //tablelayout = where u want the buttons---array = the array of button names
    //context = xml file where your puttin buttons
    public ButtonCreator( Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;

         gdDefault = new GradientDrawable();

    }

    public void buttonCreator(TableLayout tableLayout, String... array){
        counter =0;
        int numRows = (int)Math.ceil((double)array.length / 4.0);////divides by colum amount
        int numCols = Math.min(array.length, 4);;////checks if array less than 4 then sets to array lenght
        Button[][] buttonArray = new Button[numRows][numCols];
        TableLayout table = new TableLayout(context);
        for(int row = 0;row<numRows;row++){
            TableRow currentRow = new TableRow(context);
            if(row == numRows-1 && array.length%4 >= 1)
                numCols = array.length%4;

            for (int button = 0; button < numCols; button++) {

                Button countiesBtn = new Button(context);
                countiesBtn.setText(array[counter]);
                countiesBtn.setId(View.generateViewId());//

                countiesBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);




                gdDefault.setColor(Color.parseColor("#fff2f4"));
                gdDefault.setCornerRadius(50);



                gdDefault.setStroke(3, Color.BLACK);
                countiesBtn.setBackground(gdDefault);
                countiesBtn.setTextColor(Color.BLACK);
                TableRow.LayoutParams params = new TableRow.LayoutParams(250, 93);


                int marginSize = 8; // Replace with your desired margin size in pixels
                params.setMargins(marginSize, marginSize, marginSize, marginSize);
                countiesBtn.setLayoutParams(params);

                counter++;
                countiesBtn.setOnClickListener(listener);

                buttonArray[row][button] = countiesBtn;
                currentRow.addView(countiesBtn);
            }
            // a new row has been constructed -> add to countiesTable
            table.addView(currentRow);
        }
// and finally takes that new countiesTable and add it to your layout.
        tableLayout.addView(table);

    }


    @Override
    public void onClick(View v) {

    }
}
