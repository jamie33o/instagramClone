package com.example.instagramclone.choices_tabs;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class Choices_tabs_Activity extends AppCompatActivity implements View.OnClickListener{
    public static ViewPager2 viewPager;
     Choices_TabAdapter choices_tabAdapter;
    int tableClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices_tabs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.viewPager_choices_tab);
        viewPager.setUserInputEnabled(false);

        choices_tabAdapter = new Choices_TabAdapter(this);
        viewPager.setAdapter(choices_tabAdapter);
        tableClicked = getIntent().getIntExtra("tableclicked",0);

        View dot1 = findViewById(R.id.dot1);
        View dot2 = findViewById(R.id.dot2);
        View dot3 = findViewById(R.id.dot3);
        View dot4 = findViewById(R.id.dot4);
        View dot5 = findViewById(R.id.dot5);
        View dot6 = findViewById(R.id.dot6);
        List<View> dots = new ArrayList<>();
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        dots.add(dot5);
        dots.add(dot6);

       new DotsIndicator(dots,this,viewPager);




        if(tableClicked!=0){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(tableClicked);
                }
            }, 1000); // Delay in milliseconds
        }

    }


    @Override
    public void onClick(View v) {

    }

}