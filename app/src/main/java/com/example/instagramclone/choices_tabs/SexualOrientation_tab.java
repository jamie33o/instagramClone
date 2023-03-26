package com.example.instagramclone.choices_tabs;

import androidx.fragment.app.Fragment;

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
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SexualOrientation_tab extends Fragment implements View.OnClickListener {

    private ButtonCreator buttonCreator;


    private SharedPreferencesManager sharedPreferencesManager;
    TextView txtview;
    private ArrayList<String> chosenInterestsList, selectedButtonsList;
    private TableLayout tableLayout;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private String[] sexualOrientation;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;





    public SexualOrientation_tab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);
        sexualOrientation = new String[]{"Heterosexual", "Homosexual", "Bisexual", "Pansexual", "Asexual", "Demisexual", "Graysexual", "Queer", "Questioning"};

        sharedPreferencesManager = new SharedPreferencesManagerImpl(view.getContext(), "Profile", Context.MODE_PRIVATE);


        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Sexual Orientation");


        tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());


        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons

        List<Button> btnList = new ArrayList<>();
        buttonCreator = new ButtonCreator(getContext(),btnList);
        buttonCreator.buttonCreator(tableLayout, this,"", sexualOrientation);

        // chosenInterestsList = new ArrayList<>();
        selectedButtonsList = new ArrayList<>();


        String savedJsoninterest = sharedPreferencesManager.getString("sexualOrientation", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson2 = new Gson(); // Create a Gson instance
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> chosenInterests = gson2.fromJson(savedJsoninterest, type);


        //checks the choices thar are stored and lights up the buttons accordingly and
        // adds the to the selected button array
        lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtn.lightUpPreSelectBtn(chosenInterests,btnList);
        return view;//must return type view


    }






    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if(!(v instanceof ImageButton)) {




            addBtnTxtToArray.addBtnClickedTxtToArray(selectedButtonsList,null,v,4);

        }


        if (buttonId == R.id.savebtn) {


            DataBaseUtils queryDatabase = new ReusableQueries(view.getContext());

            queryDatabase.uploadToDataBase("sexualOrientation", selectedButtonsList, view.getContext());

            Gson gson1 = new Gson(); // Create a Gson instance
            String jsoninterests = gson1.toJson(selectedButtonsList); // Convert the array of strings to a JSON string using Gson
            if (selectedButtonsList.size() > 0) { // Check if the array contains any elements
                sharedPreferencesManager.saveString("sexualOrientation", jsoninterests); // Store the JSON string in shared preferences for the "choseninterests" key
            }


        }
    }


}
