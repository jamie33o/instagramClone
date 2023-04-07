package com.example.instagramclone.choices_tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import java.util.List;

import io.realm.Realm;

public class Where_i_Live_tab extends Fragment implements View.OnClickListener {

    private ButtonCreator buttonCreator;
    String whereiliveString;

    TextView txtview;
    private ArrayList<String> chosenInterestsList, selectedButtonsList;
    private TableLayout tableLayout;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private String[] counties;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;
    ViewPager viewPager;

    List<Button> btnList;

    private Realm realm;
    private RealmModel results;

    public Where_i_Live_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);

        viewPager = ((Choices_tabs_MainPage) requireActivity()).getViewPager();

        counties = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};

        //get instance of realm
        realm = RealmManager.getRealmInstance();

        // Get the object you want to update
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();


        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Where i Live");

        tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());


        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons

        btnList = new ArrayList<>();
        buttonCreator = new ButtonCreator(getContext(), btnList);
        buttonCreator.buttonCreator(tableLayout, this, "", counties);

        // chosenInterestsList = new ArrayList<>();
        selectedButtonsList = new ArrayList<>();

        realm.beginTransaction();
        whereiliveString = results.getWhereIlive();
        realm.commitTransaction();

        //checks the choices thar are stored and lights up the buttons accordingly and
        // adds the to the selected button array
        lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtn.lightUpPreSelectBtn(whereiliveString, btnList);
        return view;//must return type view


    }


    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if (!(v instanceof ImageButton)) {
            whereiliveString = addBtnTxtToArray.addBtnClickedTxttoString(whereiliveString, btnList, v);
        }

        if (buttonId == R.id.savebtn) {

            uploadToRealm();
            viewPager.setCurrentItem(2);
        }
    }


    public void uploadToRealm() {
        if(results!=null) {
            realm.beginTransaction();

        results.setWhereIlive(whereiliveString);

        realm.commitTransaction();
            Snackbar_Dialog.showSnackbar(view.getContext(), "Success!!!", 2000);

        }else {
            Snackbar_Dialog.showSnackbar(view.getContext(), "Error saving selection!!", 2000);

        }

    }
}