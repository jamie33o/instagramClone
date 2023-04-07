package com.example.instagramclone.choices_tabs;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
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
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SexualOrientation_tab extends Fragment implements View.OnClickListener {

    private ButtonCreator buttonCreator;

    String sexualOrientationString;

    private SharedPreferencesManager sharedPreferencesManager;
    TextView txtview;
    private TableLayout tableLayout;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private String[] sexualOrientation;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;

    List<Button> btnList;

    ViewPager viewPager;

    private Realm realm;
    private RealmModel results;
    public SexualOrientation_tab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);

        viewPager = ((Choices_tabs_MainPage) requireActivity()).getViewPager();

        //get instance of realm
        realm = RealmManager.getRealmInstance();

        // Get the object you want to update
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();


        sexualOrientation = new String[]{"Straight","Lesbian", "Gay", "Bisexual", "Pansexual", "Questioning"};

        sharedPreferencesManager = new SharedPreferencesManagerImpl(view.getContext(), "Profile", Context.MODE_PRIVATE);


        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Sexual Orientation");


        tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());


        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons

        btnList = new ArrayList<>();
        buttonCreator = new ButtonCreator(getContext(),btnList);
        buttonCreator.buttonCreator(tableLayout, this,"", sexualOrientation);

        realm.beginTransaction();
        sexualOrientationString = results.getSexualOrientaion();
        realm.commitTransaction();

        //checks the choices thar are stored and lights up the buttons accordingly and
        // adds the to the selected button array
        lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtn.lightUpPreSelectBtn(sexualOrientationString,btnList);
        return view;//must return type view


    }






    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if(!(v instanceof ImageButton)) {

           sexualOrientationString = addBtnTxtToArray.addBtnClickedTxttoString(sexualOrientationString,btnList,v);

        }


        if (buttonId == R.id.savebtn) {

               uploadToRealm();
        viewPager.setCurrentItem(3);
        }
    }

    public void uploadToRealm() {
// Set the value of a dynamic property using reflection
        if(results!=null) {
        realm.beginTransaction();
            results.setSexualOrientaion(sexualOrientationString);
        realm.commitTransaction();
        Snackbar_Dialog.showSnackbar(view.getContext(), "Success!!!", 2000);

    }else {
        Snackbar_Dialog.showSnackbar(view.getContext(), "Error saving selection!!", 2000);

    }
    }
}
