package com.example.instagramclone.choices_tabs.tabs;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.ButtonTxtArraysSingleton;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.AddBtnTxtToArray;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.LightUpPreSelectedbtn;
import com.example.instagramclone.reusable_code.Dialogs;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SexualOrientation_tab extends Fragment implements View.OnClickListener {

    private String sexualOrientationString,oldSexualOrientationString;

    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private LightUpPreSelectedbtn lightUpPreSelectedbtn;

    private List<Button> btnList;

   private ViewPager2 viewPager;
    public SexualOrientation_tab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_choices_tab,//used to find view inside fragment
                container, false);
        viewPager = requireActivity().findViewById(R.id.viewPager_choices_tab);


        TextView txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Sexual Orientation");


        TableLayout tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());


        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons
        ButtonTxtArraysSingleton singletonInstance = ButtonTxtArraysSingleton.getInstance();

        btnList = new ArrayList<>();
        ButtonCreator buttonCreator = new ButtonCreator(getContext(), btnList);
        buttonCreator.buttonCreator(tableLayout, this,singletonInstance.sexualOrientation);

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    sexualOrientationString = parseModel.getSexualOrientation();

                    //checks the choices thar are stored and lights up the buttons accordingly and
                    // adds the to the selected button array
                    lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(sexualOrientationString, btnList);

                    oldSexualOrientationString = sexualOrientationString;
                }
            }
        });
        return view;//must return type view


    }






    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if(!(v instanceof ImageButton)) {

           sexualOrientationString = addBtnTxtToArray.addBtnClickedTxttoString(sexualOrientationString,btnList,v);

        }


        if (buttonId == R.id.savebtn) {
            if(sexualOrientationString != null && !sexualOrientationString.equals("") && !Objects.equals(sexualOrientationString, oldSexualOrientationString)) {
                saveToParse();
                viewPager.setCurrentItem(3);
            }else if(sexualOrientationString != null && !Objects.equals(sexualOrientationString, "")){
                viewPager.setCurrentItem(3);
            }
        }
    }

    public void saveToParse() {
// Set the value of a dynamic property using reflection
        if (sexualOrientationString != null) {
            ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
                @Override
                public void done(ParseModel parseModel, ParseException e) {
                    if (e == null) {

                        parseModel.setSexualOrientation(sexualOrientationString);

                        // Save the object locally
                        parseModel.pinInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Display a toast message to indicate that the object has been saved locally
                                    Dialogs.showSnackbar(view.getContext(), "Success!!!\n Selection's saved", 2000);
                                } else {
                                    Dialogs.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);


                                }
                            }
                        });
                    } else {
                        Dialogs.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);
                    }
                }
            });

        }
    }
    }
