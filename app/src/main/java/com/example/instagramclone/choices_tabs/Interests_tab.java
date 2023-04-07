package com.example.instagramclone.choices_tabs;
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

import com.example.instagramclone.R;
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.reusable_code.AddBtnTxtToArray;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.LightUpPreSelectedbtn;
import com.example.instagramclone.reusable_code.Snackbar_Dialog;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

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

    ViewPager viewPager;

    RealmList<String> realmList;

    private Realm realm;
    private RealmModel results;

    public Interests_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);

        //used to change fragments when button clicked
         viewPager = ((Choices_tabs_MainPage) requireActivity()).getViewPager();


        interests = new String[]{"Reading", "Writing", "Photography", "Traveling", "Cooking", "Hiking", "Yoga", "Painting", "Gaming", "Music", "Dancing", "Sports", "Fitness", "Meditation", "Coding", "Gardening", "Fishing", "Gym", "Surfing", "Netflix"};

        //get instance of realm
        realm = RealmManager.getRealmInstance();


        // Get the object you want to update
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();

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

        realm.beginTransaction();
        realmList = results.getInterests();
        interestsList = new ArrayList<>(realmList);
        realm.commitTransaction();
        //checks the choices thar are stored and lights up the buttons accordingly and
        // adds the to the selected button array
      lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
      lightUpPreSelectedbtn.lightUpPreSelectBtn(interestsList,btnList);
        return view;//must return type view


    }






    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if(!(v instanceof ImageButton)) {

            addBtnTxtToArray.addBtnClickedTxtToArray(interestsList,v,4);

        }
        if (buttonId == R.id.savebtn) {


            uploadToRealm();
            //used to change fragments when button clicked
            viewPager.setCurrentItem(1);
        }
    }
    public void uploadToRealm() {

        if (results != null) {
            realmList = new RealmList<>();
            realmList.addAll(interestsList);
            realm.beginTransaction();
            results.setInterests(realmList);
            realm.commitTransaction();

            Snackbar_Dialog.showSnackbar(view.getContext(), "Success!!!", 2000);

        }else {
            Snackbar_Dialog.showSnackbar(view.getContext(), "Error saving selection!!", 2000);

        }
    }





}
