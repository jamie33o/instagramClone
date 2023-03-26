package com.example.instagramclone.edit_profile_N_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener, DataBaseUtils {

   private ImageView backbtn,profileImg;
   private SharedPreferencesManager sharedPreferencesManager;
   private TextView tvName,tvAge,tvBio;
    private TableLayout interestsLayoutProfile,languagesLayoutProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        sharedPreferencesManager = new SharedPreferencesManagerImpl(this,"Profile",Context.MODE_PRIVATE);
        ButtonCreator buttonCreator = new ButtonCreator(this,new ArrayList<>());
        backbtn = findViewById(R.id.btnBackProfile);
        backbtn.setOnClickListener(this);
        profileImg = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        tvAge = findViewById(R.id.tvAge);
        tvBio = findViewById(R.id.tvBio);
        languagesLayoutProfile = findViewById(R.id.languagesLayoutProfile);
        interestsLayoutProfile = findViewById(R.id.interestsLayoutProfile);

        String savedJsoninterest = sharedPreferencesManager.getString("choseninterests", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson2 = new Gson(); // Create a Gson instance
        String[] chosenInterests = gson2.fromJson(savedJsoninterest, String[].class);



        String savedlanguages = sharedPreferencesManager.getString("languages", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson3 = new Gson();
        String[] languages = gson3.fromJson(savedlanguages, String[].class);



        buttonCreator.buttonCreator(interestsLayoutProfile,this,"interests",chosenInterests);

        buttonCreator.buttonCreator(languagesLayoutProfile,this,"languages",languages);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        sharedPreferencesManager = new SharedPreferencesManagerImpl(this, "Profile",Context.MODE_PRIVATE);
        PiccassoLoadToImageView piccassoLoadToImageView = new PiccassoLoadToImageView(this);

        piccassoLoadToImageView.getImageNloadIntoImageview(profileImg,"image1",width,1100,0);

        tvName.setText(sharedPreferencesManager.getString("name","Update your profile.")+", ");

        tvAge.setText(sharedPreferencesManager.getString("age","Add your Age"));

        tvBio.setText(sharedPreferencesManager.getString("bio","Add your Bio"));

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btnBackProfile){
            finish();
        }

    }


    @Override
    public void deleteFileFromDatabase(String columnName) {

    }

    @Override
    public void uploadToDataBase(String columnKey, Object columnValue, Context context) {

    }
}