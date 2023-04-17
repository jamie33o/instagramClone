package com.example.instagramclone.choices_tabs;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.R;

public class Choices_tabs_MainPage extends AppCompatActivity implements View.OnClickListener{
    public static ViewPager2 viewPager;
     Choices_TabAdapter choices_tabAdapter;
    int tableClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choices_tabs_mainpage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.viewPager_choices_tab);
        choices_tabAdapter = new Choices_TabAdapter(this);
        viewPager.setAdapter(choices_tabAdapter);
        tableClicked = getIntent().getIntExtra("tableclicked",0);
        viewPager.setCurrentItem(tableClicked);

        int totalItems = choices_tabAdapter.getItemCount();

        int progessAmount = tableClicked == 0 ? 20:(int) ((tableClicked + 1) / (float) totalItems * 100);

        //
        ProgressBar progressBar = findViewById(R.id.choice_tabs_progressBar);
        progressBar.setProgress(progessAmount);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int progress = (int) ((position + 1) / (float) totalItems * 100);
                float f = totalItems*100;
                progressBar.setProgress(progress);
            }


        });


    }


    @Override
    public void onClick(View v) {

    }

}