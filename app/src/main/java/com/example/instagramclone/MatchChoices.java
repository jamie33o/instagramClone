package com.example.instagramclone;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.Gravity;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TableLayout;

        import com.example.instagramclone.edit_profile.EditProfile;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collections;
        import java.util.Objects;

public class MatchChoices extends AppCompatActivity implements View.OnClickListener {
    public static boolean yes, no;
    UsersTab usersTab;
    String[] interests,gender,counties;

    ButtonCreator buttonCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_choices);

        gender = new String[]{"Male","Female"};
        counties = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        interests = new String[]{"Reading", "Writing", "Photography", "Traveling", "Cooking", "Hiking", "Yoga", "Painting", "Gaming", "Music", "Dancing", "Sports", "Fitness", "Meditation", "Coding", "Gardening", "Fishing", "Gym", "Surfing", "Netflix"};


         usersTab = new UsersTab();



        findViewById(R.id.backbtn).setOnClickListener(this);
        findViewById(R.id.savebtn).setOnClickListener(this);




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();


        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        TableLayout genderLayout = findViewById(R.id.gender);
         buttonCreator = new ButtonCreator(this,this);
        buttonCreator.buttonCreator(genderLayout,gender);





        TableLayout interestsLayout = findViewById(R.id.interestsTablelayout);
        buttonCreator.buttonCreator(interestsLayout,interests);



        TableLayout countiesBtnLayout = findViewById(R.id.countiesTablelayout);
        buttonCreator.buttonCreator(countiesBtnLayout,counties);
    }
    private ArrayList<String> chosenInterests = new ArrayList<>();
    String chosenGender;
    private ArrayList<String> chosenCounties = new ArrayList<>();


    private ArrayList<String> selectedButtons = new ArrayList<>();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backbtn) {
            finish();
            return;
        }

        if (v.getId() == R.id.savebtn) {
            for(String button : selectedButtons) {
                // Check if the button is contained in the gender array
                if (Arrays.asList(gender).contains(button)) {
                    // Add the button to the chosenGender array if it's not already there

                    if (!Objects.equals(chosenGender, button)) {
                        chosenGender =button;                    }
                }
                // Check if the button is contained in the counties array
                if (Arrays.asList(counties).contains(button)) {
                    // Add the button to the chosenCounties array if it's not already there
                    if (!chosenCounties.contains(button)) {
                        chosenCounties.add(button);
                    }
                }
                // Check if the button is contained in the interests array
                if (Arrays.asList(interests).contains(button)) {
                    // Add the button to the chosenInterests array if it's not already there
                    if (!chosenInterests.contains(button)) {
                        chosenInterests.add(button);
                    }
                }
            }


            QueryDatabase queryDatabase = new QueryDatabase(this);

            queryDatabase.putQueryDatabase("chosengender",chosenGender,this);
            queryDatabase.putQueryDatabase("chosencounties",chosenCounties,this);
            queryDatabase.putQueryDatabase("choseninterest",chosenInterests,this);


// Store multiple string values in the preferences

            if(chosenCounties.size() >0) {
                SharedPreferences countieprefs = getSharedPreferences("countiesPrefs", Context.MODE_PRIVATE);
// Get the editor object to make changes to the preferences
                SharedPreferences.Editor countieseditor = countieprefs.edit();
// Store multiple string values in the preferences
                countieseditor.clear();
                for (int i = 0; i < chosenCounties.size(); i++) {
                    countieseditor.putString("ChosenCounty" + i, chosenCounties.get(i));

                }
                countieseditor.apply();
            }

            if(chosenInterests.size() >0) {
                SharedPreferences interestsprefs = getSharedPreferences("choseninterests", Context.MODE_PRIVATE);
// Get the editor object to make changes to the preferences
                SharedPreferences.Editor interestseditor = interestsprefs.edit();
// Store multiple string values in the preferences
                interestseditor.clear();
                for (int i = 0; i < chosenInterests.size(); i++) {
                    interestseditor.putString("choseninterests" + i, chosenInterests.get(i));
                }
                interestseditor.apply();
            }


            if(chosenGender != null) {
                SharedPreferences genderPrefs = getSharedPreferences("chosengenderPrefs", Context.MODE_PRIVATE);
// Get the editor object to make changes to the preferences
                SharedPreferences.Editor genderprefseditor = genderPrefs.edit();
// Store multiple string values in the preferences
                genderprefseditor.clear();
                    genderprefseditor.putString("ChosenGender", chosenGender);
                genderprefseditor.apply();
            }
// Save the changes

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

            return;
        }


        int buttonId = v.getId();
        Button button =  findViewById(buttonId);
        String buttonText = button.getText().toString();

        if (selectedButtons.contains(buttonText)) {
            selectedButtons.remove(buttonText);
            buttonCreator.gdDefault.setStroke(3, Color.BLACK);
            button.setTextColor(Color.BLACK);
        } else {
            selectedButtons.add(buttonText);
            buttonCreator.gdDefault.setStroke(3, Color.parseColor("#b7312c"));
            button.setTextColor(Color.parseColor("#b7312c"));

        }




    }




}





