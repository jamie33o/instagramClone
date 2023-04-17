package com.example.instagramclone.choices_tabs.tabs;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.choices_tabs.Choices_tabs_MainPage;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.AddBtnTxtToArray;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.LightUpPreSelectedbtn;
import com.example.instagramclone.reusable_code.Snackbar_Dialog;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Interests_tab extends Fragment implements View.OnClickListener {
    private  ButtonCreator buttonCreator;


    TextView txtview;
    private List<String> interestsList;
    private TableLayout tableLayout;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;


    private String[] interests;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;
    Gson gson1 ;

    ViewPager2 viewPager;


    public Interests_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);

         viewPager = requireActivity().findViewById(R.id.viewPager_choices_tab);


        interests = new String[]{"Reading", "Writing", "Photography", "Traveling", "Cooking", "Hiking", "Painting", "Gaming", "Music", "Dancing", "Sports", "Fitness", "Meditation", "Coding", "Gardening", "Fishing", "Gym", "Surfing", "Netflix"};

        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Interests");

        tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());

        gson1 = new Gson(); // Create a Gson instance



        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons

        List<Button> btnList = new ArrayList<>();
        buttonCreator = new ButtonCreator(getContext(),btnList);
        buttonCreator.buttonCreator(tableLayout, this, "",interests);
        interestsList = new ArrayList<>();

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if(parseModel.getInterests()!=null) {
                        interestsList = parseModel.getInterests();
                        //checks the choices thar are stored and lights up the buttons accordingly and
                        // adds the to the selected button array
                        lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
                        lightUpPreSelectedbtn.lightUpPreSelectBtn(interestsList, btnList);

                    }

                }
            }
        });
        return view;//must return type view


    }






    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if(!(v instanceof ImageButton)) {

              addBtnTxtToArray.addBtnClickedTxtToArray(interestsList,v,4);

        }
        if (buttonId == R.id.savebtn) {


            saveToParse();
            //used to change fragments when button clicked
            viewPager.setCurrentItem(1);
        }
    }


    public void saveToParse() {
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    parseModel.setInterests(interestsList);
                    // Save the object locally
                    parseModel.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Display a toast message to indicate that the object has been saved locally
                                Snackbar_Dialog.showSnackbar(view.getContext(), "Success!!!\n Selection's saved", 2000);
                            } else {
                                Snackbar_Dialog.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);


                            }
                        }
                    });
                } else {
                    Snackbar_Dialog.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);
                }
            }
        });

    }
    }
