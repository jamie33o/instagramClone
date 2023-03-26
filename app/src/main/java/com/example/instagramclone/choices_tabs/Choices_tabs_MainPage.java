package com.example.instagramclone.choices_tabs;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.TabAdapter;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.google.android.material.tabs.TabLayout;

public class Choices_tabs_MainPage extends AppCompatActivity implements View.OnClickListener{
    private ViewPager viewPager;
    private Choices_TabAdapter choices_tabAdapter;
    String tableClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choices_tabs_mainpage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.viewPager_choices_tab);
        choices_tabAdapter = new Choices_TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(choices_tabAdapter);
        tableClicked = getIntent().getStringExtra("com.example.instagram.layoutthatwasclicked");



        choices_tabAdapter.getItem(getPos());

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(10); // set progress to 50%
        choices_tabAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                int currentFragmentIndex = choices_tabAdapter.getCurrent_tab_count();
                System.out.println(currentFragmentIndex);
                progressBar.setProgress(currentFragmentIndex * 100 / choices_tabAdapter.getCount());
            }
        });

    }

    public int getPos(){

        switch (tableClicked){
            case "choseninterestsLayout":
                return  0;
            case "where_i_live":
               return  1;

            case "sexualOrientation":
               return  2;

            case "languages":
                return  3;

            case "my_basics":
               return  4;

            case "my_lifestyle":
                return  5;
            default:
              return  0;

        }


    }

    @Override
    public void onClick(View v) {

    }
}