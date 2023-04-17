package com.example.instagramclone.main_tabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.instagramclone.R;
import com.example.instagramclone.login_signup.SignUp;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;
import com.example.instagramclone.main_tabs.usertab_cardview_adapter.UsersTab;
import com.example.instagramclone.reusable_code.GetUserLocation;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.parse.ParseUser;

public class SocialMediaActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Ignite");





        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        viewPager2 = findViewById(R.id.viewPager_main_tabs);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.tabLayout);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);//inflates menu xml

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.postImageItem) {// checks if user has clicked camera image

            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    checkSelfPermission(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        3000);


            } else {
                captureImage();
            }


        } else if (item.getItemId() == R.id.logoutUserItem) {

            ParseUser.logOut();
            finish();
            Intent intent = new Intent(SocialMediaActivity.this, SignUp.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 3000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }

    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4000 && resultCode == RESULT_OK && data != null) {
        }
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



    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

}