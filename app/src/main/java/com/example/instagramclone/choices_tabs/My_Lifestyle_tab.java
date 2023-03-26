package com.example.instagramclone.choices_tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;

import java.util.ArrayList;

public class My_Lifestyle_tab extends Fragment {

    private ButtonCreator buttonCreator;


    private SharedPreferencesManager sharedPreferencesManager;
    TextView txtview;
    private ArrayList<String> chosenInterestsList, selectedButtonsList;
    private TableLayout tableLayout;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private String[] interests;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;


    public My_Lifestyle_tab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
        container, false);
        sharedPreferencesManager = new SharedPreferencesManagerImpl(view.getContext(), "Profile", Context.MODE_PRIVATE);


        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("My Lifestyle");




        return view;//must return type view



    }

}