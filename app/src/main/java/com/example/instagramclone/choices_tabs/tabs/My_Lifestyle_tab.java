package com.example.instagramclone.choices_tabs.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.ButtonTxtArraysSingleton;
import com.example.instagramclone.main_tabs.ProfileTab.edit_profile.EditProfile;
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
import java.util.Arrays;
import java.util.List;

public class My_Lifestyle_tab extends Fragment implements View.OnClickListener {

    private boolean choiceIsChanged = false;
    private List<String> workoutList;
    private List<Button> kidsbtnList, smokingbtnList, workoutbtnList, dietarybtnList, socialmediabtnList, petsbtnList, drinkingbtnList;

    private String petsString, smokingString, drinkingString, dietryString, socialMediaString, kidsString;

    private View view;
    private ButtonTxtArraysSingleton singletonInstance;

    private AddBtnTxtToArray addBtnTxtToArray, addBtnTxtToArray1, addBtnTxtToArray2, addBtnTxtToArray3, addBtnTxtToArray4, addBtnTxtToArray5, addBtnTxtToArray6;

    private LightUpPreSelectedbtn lightUpPreSelectedbtn;

    private String[] petsArray, smokingArray, kidsArray, drinkingArray, dietaryArray, workoutArray, socialmediaArray;
   private String myVariable;
    public My_Lifestyle_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_choices_tab,//used to find view inside fragment
                container, false);

        view.findViewById(R.id.savebtn).setOnClickListener(this);


        //set the text views
        TextView txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("My Lifestyle");

        TextView textViewHeight = view.findViewById(R.id.txtview_choice0);
        view.findViewById(R.id.line4).setVisibility(View.VISIBLE);
        textViewHeight.setVisibility(View.VISIBLE);
        textViewHeight.setText("Pets");

        TextView txtV2 = view.findViewById(R.id.txtview_choice2);
        txtV2.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line5).setVisibility(View.VISIBLE);
        txtV2.setText("Smoking");

        TextView txtV3 = view.findViewById(R.id.txtview_choice3);
        txtV3.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line6).setVisibility(View.VISIBLE);
        txtV3.setText("Workout");

        TextView txtV4 = view.findViewById(R.id.txtview_choice4);
        txtV4.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line7).setVisibility(View.VISIBLE);
        txtV4.setText("Drinking");

        TextView txtV5 = view.findViewById(R.id.txtview_choice5);
        txtV5.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line8).setVisibility(View.VISIBLE);
        txtV5.setText("Dietary");

        TextView txtV6 = view.findViewById(R.id.txtview_choice6);
        txtV6.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line9).setVisibility(View.VISIBLE);
        txtV6.setText("Social media");

        TextView txtV7 = view.findViewById(R.id.txtview_choice7);
        txtV7.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line10).setVisibility(View.VISIBLE);
        txtV7.setText("Kids");

        //initialize table layouts
        // tableLayout = view.findViewById(R.id.table_layout_1);
        TableLayout tableLayout1 = view.findViewById(R.id.table_layout);
        TableLayout table_layout_2 = view.findViewById(R.id.table_layout_2);
        TableLayout table_layout_3 = view.findViewById(R.id.table_layout_3);
        TableLayout table_layout_4 = view.findViewById(R.id.table_layout_4);
        TableLayout table_layout_5 = view.findViewById(R.id.table_layout_5);
        TableLayout table_layout_6 = view.findViewById(R.id.table_layout_6);
        TableLayout table_layout_7 = view.findViewById(R.id.table_layout_7);

        workoutList = new ArrayList<>();

        //button array list
        petsbtnList = new ArrayList<>();
        smokingbtnList = new ArrayList<>();
        workoutbtnList = new ArrayList<>();
        dietarybtnList = new ArrayList<>();
        socialmediabtnList = new ArrayList<>();
        kidsbtnList = new ArrayList<>();
        drinkingbtnList = new ArrayList<>();

         singletonInstance = ButtonTxtArraysSingleton.getInstance();

        //initialize and create the buttons
        ButtonCreator buttonCreatorPets = new ButtonCreator(view.getContext(), petsbtnList);
        buttonCreatorPets.buttonCreator(tableLayout1, this, singletonInstance.petsArray);

        ButtonCreator buttonCreatorSmoking = new ButtonCreator(view.getContext(), smokingbtnList);
        buttonCreatorSmoking.buttonCreator(table_layout_2, this, singletonInstance.smokingArray);

        ButtonCreator buttonCreatorWorkout = new ButtonCreator(view.getContext(), workoutbtnList);
        buttonCreatorWorkout.buttonCreator(table_layout_3, this, singletonInstance.workoutArray);

        ButtonCreator buttonCreatorDrinking = new ButtonCreator(view.getContext(), drinkingbtnList);
        buttonCreatorDrinking.buttonCreator(table_layout_4, this, singletonInstance.drinkingArray);

        ButtonCreator buttonCreatorDietary = new ButtonCreator(view.getContext(), dietarybtnList);
        buttonCreatorDietary.buttonCreator(table_layout_5, this, singletonInstance.dietaryArray);

        ButtonCreator buttonCreatorKids = new ButtonCreator(view.getContext(), kidsbtnList);
        buttonCreatorKids.buttonCreator(table_layout_7, this, singletonInstance.kidsArray);

        ButtonCreator buttonCreatorsocialMedia = new ButtonCreator(view.getContext(), socialmediabtnList);
        buttonCreatorsocialMedia.buttonCreator(table_layout_6, this, singletonInstance.socialmediaArray);


        addBtnTxtToArray5 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray1 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray2 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray3 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray4 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray6 = new AddBtnTxtToArray(view.getContext());
        workoutList = new ArrayList<>();

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    if (parseModel.getWorkout()!=null)
                       workoutList = new ArrayList<>(parseModel.getWorkout());
                    smokingString = parseModel.getSmoking();
                    drinkingString = parseModel.getDrinking();
                    socialMediaString = parseModel.getSocialMedia();
                    dietryString = parseModel.getDietary();
                    kidsString = parseModel.getKids();
                    petsString = parseModel.getPets();

                    //checks the choices thar are stored and lights up the buttons accordingly and
                    // adds the to the selected button array
                    lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(workoutList, workoutbtnList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(smokingString, smokingbtnList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(socialMediaString, socialmediabtnList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(dietryString, dietarybtnList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(drinkingString, drinkingbtnList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(kidsString, kidsbtnList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(petsString, petsbtnList);
                }

            }
        });


        return view;//must return type view


    }

    @Override
    public void onClick(View v) {


        int buttonId = v.getId();


        if (!(v instanceof ImageButton)) {

            choiceIsChanged = true;
            Button btn = view.findViewById(buttonId);
            String btnTxt = btn.getText().toString();


            if (Arrays.asList(singletonInstance.kidsArray).contains(btnTxt))
                kidsString = addBtnTxtToArray1.addBtnClickedTxttoString(kidsString, kidsbtnList, v);
            if (Arrays.asList(singletonInstance.smokingArray).contains(btnTxt))
                smokingString = addBtnTxtToArray2.addBtnClickedTxttoString(smokingString, smokingbtnList, v);
            if (Arrays.asList(singletonInstance.socialmediaArray).contains(btnTxt))
                socialMediaString = addBtnTxtToArray3.addBtnClickedTxttoString(socialMediaString, socialmediabtnList, v);
            if (Arrays.asList(singletonInstance.drinkingArray).contains(btnTxt))
                drinkingString = addBtnTxtToArray4.addBtnClickedTxttoString(drinkingString, drinkingbtnList, v);
            if (Arrays.asList(singletonInstance.petsArray).contains(btnTxt))
                petsString = addBtnTxtToArray6.addBtnClickedTxttoString(petsString, petsbtnList, v);
            if (Arrays.asList(singletonInstance.dietaryArray).contains(btnTxt))
                dietryString = addBtnTxtToArray5.addBtnClickedTxttoString(dietryString, dietarybtnList, v);
            if (Arrays.asList(singletonInstance.workoutArray).contains(btnTxt))
                addBtnTxtToArray.addBtnClickedTxtToArray(workoutList, v, 3);


        }
        if (v.getId() == R.id.savebtn) {

            if(choiceIsChanged)
                saveToParse();
            //todo go back to editprofile page mite need to use intent on activity results to update info???
            android.os.Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(myVariable==null){
                        Intent intent = new Intent(view.getContext(), EditProfile.class);
                        startActivity(intent);
                    }else {
                        requireActivity().onBackPressed();
                    }
                }


            }, 2000);
        }
    }

    public void saveToParse() {
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if(petsString!=null)
                        parseModel.setPets(petsString);
                    if(smokingString!=null)
                        parseModel.setSmoking(smokingString);
                    if(workoutList!=null)
                      parseModel.setWorkout(workoutList);
                    if(drinkingString!=null)
                        parseModel.setDrinking(drinkingString);
                    if(dietryString!=null)
                        parseModel.setDietary(dietryString);
                    if(socialMediaString!=null)
                        parseModel.setSocialMedia(socialMediaString);
                    if(kidsString!=null)
                        parseModel.setKids(kidsString);

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