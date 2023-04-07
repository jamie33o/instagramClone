package com.example.instagramclone.choices_tabs;

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

import com.example.instagramclone.R;
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.reusable_code.AddBtnTxtToArray;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.LightUpPreSelectedbtn;
import com.example.instagramclone.reusable_code.Snackbar_Dialog;
import com.example.instagramclone.reusable_database_queries.UtilsClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class My_Lifestyle_tab extends Fragment implements View.OnClickListener {

    List<String> workoutList;
    List<Button> kidsbtnList,smokingbtnList,workoutbtnList,dietarybtnList,socialmediabtnList,petsbtnList,drinkingbtnList;
    TableLayout tableLayout,tableLayout1,table_layout_2,table_layout_3,table_layout_4,table_layout_5,table_layout_6,table_layout_7;
    ButtonCreator buttonCreatorSmoking,buttonCreatorPets,buttonCreatorWorkout,buttonCreatorDrinking,buttonCreatorDietary,buttonCreatorKids,buttonCreatorsocialMedia;

    String petsString,smokingString,drinkingString,dietryString,socialMediaString,kidsString;

    TextView txtview;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray,addBtnTxtToArray1,addBtnTxtToArray2,addBtnTxtToArray3,addBtnTxtToArray4,addBtnTxtToArray5,addBtnTxtToArray6;

    private LightUpPreSelectedbtn lightUpPreSelectedbtn;
    private Realm realm;
    private RealmModel results;
    RealmList<String> realmList;

    String[] petsArray,smokingArray,kidsArray,drinkingArray,dietaryArray,workoutArray,socialmediaArray;
    public My_Lifestyle_tab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);

        //get instance of realm
        realm = RealmManager.getRealmInstance();

        // Get the object you want to update
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();

        smokingArray = new String[]{"Casual", "When Stressed", "Sometimes", "Only when drinking"};
        petsArray = new String[]{"Like them have none", "Have a dog", "Have a cat", "Love them", "Not an animal lover", "Have loads"};
        workoutArray = new String[]{"Bodybuilder", "Physique model", "Daily", "when i can", "Running", "Play sports", "Cardio", "Strength training", "HIIT", "Yoga", "Pilates", "CrossFit", "Cycling", "Swimming", "Rowing", "Boxing", "Martial arts", "Dance", "Barre", "Bodyweight training", "Resistance band training", "TRX training", "Calisthenics"};
        drinkingArray = new String[]{"Weekends", "Daily", "Special occasion", "Never"};
        socialmediaArray = new String[]{"Passive scroller", "Addicted", "Rare", "Casual"};
        kidsArray = new String[]{"One child", "two kids", "Three kids", "Four kids", "Five kids", "Love kids have none", "Never", "Maybe", "Love too", "Someday", "Definetly someday"};
        dietaryArray = new String[]{"Vegetarian", "Vegan", "Ketogenic", "Paleo", "Gluten-free", "Low-carb", "Mediterranean", "DASH", "Flexitarian", "Raw food", "Intermittent fasting", "Whole30", "Atkins", "South Beach", "Weight Watchers", "Zone"};


        view.findViewById(R.id.savebtn).setOnClickListener(this);


        //set the text views
        txtview = view.findViewById(R.id.txtview_title);
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
        tableLayout1 = view.findViewById(R.id.table_layout);
        table_layout_2 = view.findViewById(R.id.table_layout_2);
        table_layout_3 = view.findViewById(R.id.table_layout_3);
        table_layout_4 = view.findViewById(R.id.table_layout_4);
        table_layout_5 = view.findViewById(R.id.table_layout_5);
        table_layout_6 = view.findViewById(R.id.table_layout_6);
        table_layout_7 = view.findViewById(R.id.table_layout_7);

        workoutList = new ArrayList<>();

        //button array list
        petsbtnList = new ArrayList<>();
        smokingbtnList = new ArrayList<>();
        workoutbtnList = new ArrayList<>();
        dietarybtnList = new ArrayList<>();
        socialmediabtnList = new ArrayList<>();
        kidsbtnList = new ArrayList<>();
        drinkingbtnList = new ArrayList<>();


        //initialize and create the buttons
        buttonCreatorPets = new ButtonCreator(view.getContext(), petsbtnList);
        buttonCreatorPets.buttonCreator(tableLayout1, this, "", petsArray);

        buttonCreatorSmoking = new ButtonCreator(view.getContext(), smokingbtnList);
        buttonCreatorSmoking.buttonCreator(table_layout_2, this, "", smokingArray);

        buttonCreatorWorkout = new ButtonCreator(view.getContext(), workoutbtnList);
        buttonCreatorWorkout.buttonCreator(table_layout_3, this, "", workoutArray);

        buttonCreatorDrinking = new ButtonCreator(view.getContext(), drinkingbtnList);
        buttonCreatorDrinking.buttonCreator(table_layout_4, this, "", drinkingArray);

        buttonCreatorDietary = new ButtonCreator(view.getContext(), dietarybtnList);
        buttonCreatorDietary.buttonCreator(table_layout_5, this, "", dietaryArray);

        buttonCreatorKids = new ButtonCreator(view.getContext(), kidsbtnList);
        buttonCreatorKids.buttonCreator(table_layout_7, this, "", kidsArray);

        buttonCreatorsocialMedia = new ButtonCreator(view.getContext(), socialmediabtnList);
        buttonCreatorsocialMedia.buttonCreator(table_layout_6, this, "", socialmediaArray);


        addBtnTxtToArray5 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray1 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray2 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray3 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray4 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray6 = new AddBtnTxtToArray(view.getContext());


        realm.beginTransaction();
        realmList = results.getWorkout();
        workoutList = new ArrayList<>(realmList);
        smokingString = results.getSmoking();
        drinkingString = results.getDrinking();
        socialMediaString = results.getSocialMedia();
        dietryString = results.getDietary();
        kidsString = results.getKids();
        petsString = results.getPets();

        realm.commitTransaction();

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



        return view;//must return type view



    }

    @Override
    public void onClick(View v) {


        int buttonId = v.getId();


        if (!(v instanceof ImageButton)) {

        Button btn = view.findViewById(buttonId);
        String btnTxt = btn.getText().toString();



            if (Arrays.asList(kidsArray).contains(btnTxt))
                kidsString = addBtnTxtToArray1.addBtnClickedTxttoString(kidsString, kidsbtnList, v);
            if (Arrays.asList(smokingArray).contains(btnTxt))
                smokingString = addBtnTxtToArray2.addBtnClickedTxttoString(smokingString, smokingbtnList, v);
            if (Arrays.asList(socialmediaArray).contains(btnTxt))
                socialMediaString = addBtnTxtToArray3.addBtnClickedTxttoString(socialMediaString, socialmediabtnList, v);
            if (Arrays.asList(drinkingArray).contains(btnTxt))
                drinkingString = addBtnTxtToArray4.addBtnClickedTxttoString(drinkingString, drinkingbtnList, v);
            if (Arrays.asList(petsArray).contains(btnTxt))
                petsString = addBtnTxtToArray6.addBtnClickedTxttoString(petsString, petsbtnList, v);
            if (Arrays.asList(dietaryArray).contains(btnTxt))
                dietryString = addBtnTxtToArray5.addBtnClickedTxttoString(dietryString, dietarybtnList, v);
            if(Arrays.asList(workoutArray).contains(btnTxt))
                addBtnTxtToArray.addBtnClickedTxtToArray(workoutList, v, 3);




        }
        if (v.getId() == R.id.savebtn) {


            uploadToRealm();
            //todo go back to editprofile page mite need to use intent on activity results to update info???
            android.os.Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requireActivity().onBackPressed();

                }


            }, 2000);
        }
    }

    public void uploadToRealm() {
// convert back to realm list
        realmList = new RealmList<>();
        realmList.addAll(workoutList);


        if (results != null) {
            realm.beginTransaction();

            results.setPets(petsString);
            results.setSmoking(smokingString);
            results.setWorkout(realmList);
            results.setDrinking(drinkingString);
            results.setDietary(dietryString);
            results.setSocialMedia(socialMediaString);
            results.setKids(kidsString);
            realm.commitTransaction();

            Snackbar_Dialog.showSnackbar(view.getContext(), "Success!!!", 2000);

        }else {
            Snackbar_Dialog.showSnackbar(view.getContext(), "Error saving selection!!", 2000);

        }


    }



}