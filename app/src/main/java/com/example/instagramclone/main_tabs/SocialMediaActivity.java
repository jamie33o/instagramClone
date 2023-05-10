package com.example.instagramclone.main_tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.instagramclone.R;
import com.example.instagramclone.settings.Settings;
import com.example.instagramclone.main_tabs.usertab_cardview_adapter.UsersTab;
import com.example.instagramclone.reusable_code.GetUserLocation;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SocialMediaActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Ignite");


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        ImageButton settingsBtn = toolbar.findViewById(R.id.settings);
        settingsBtn.setVisibility(View.VISIBLE);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocialMediaActivity.this,Settings.class));
            }
        });

        viewPager2 = findViewById(R.id.viewPager_main_tabs);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Users");
                    break;
                case 1:
                    tab.setText("Messages");
                    break;
                case 2:
                    tab.setText("Profile");
                    break;
            }
        });
        tabLayoutMediator.attach();



        // Retrieve the data from the Intent
        int data = getIntent().getIntExtra("data_key",0);

        //set which fragment to land on
      
            viewPager2.setCurrentItem(data);


        // Disable scrolling on specific page
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position <= 1) {
                    viewPager2.setUserInputEnabled(false);
                } else {
                    viewPager2.setUserInputEnabled(true);
                }
            }
        });        //Gets user location when they open the app
        new GetUserLocation(this, SocialMediaActivity.this);


    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public  void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        UsersTab.saveLikedProfileCards(this);
        super.onPause();
    }




}