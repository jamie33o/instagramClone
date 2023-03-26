package com.example.instagramclone.reusable_code;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.GradientDrawable;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.Gravity;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.TableLayout;
        import android.widget.TextView;

        import com.example.instagramclone.R;
        import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
        import com.example.instagramclone.reusable_database_queries.ReusableQueries;
        import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
        import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
        import com.example.instagramclone.usertab_cardview_adapter.UsersTab;
        import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Objects;
        import java.util.Set;

public class MatchChoices extends AppCompatActivity {
    UsersTab usersTab;
    String[] interests, gender, counties, sexualOrientation;

    String tableClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_choices);




        usersTab = new UsersTab();


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();


        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);






/*


            if (tableClicked != null && tableClicked.equals("yourcounty")) {
                buttonCreator.buttonCreator(tableLayout,this, counties);
                txtview.setText("Choose Your County");
            }


            if (cardview != null && cardview.equals("cardview")) {
                buttonCreator.buttonCreator(tableLayout,this, counties);
                txtview.setText("Choose area's or distance ");
            }
        if (tableClicked != null && tableClicked.equals("chosencountiesLayout")) {
            buttonCreator.buttonCreator(tableLayout,this, counties);
            txtview.setText("Choose area's or distance ");
        }


            if(tableClicked != null && tableClicked.equals("sexualOrientation")) {
                buttonCreator.buttonCreator(tableLayout,this, sexualOrientation);
                txtview.setText("Sexual Orientation");
            }
        if(tableClicked != null && tableClicked.equals("Gender")) {
            buttonCreator.buttonCreator(tableLayout,this, gender);
            txtview.setText("Choose Your Gender");
        }


        if(tableClicked != null && tableClicked.equals("languages")) {
            buttonCreator.buttonCreator(tableLayout,this, languages);
            txtview.setText("Languages I Know");
        }




    private ArrayList<String> languagesList = new ArrayList<>();
    private ArrayList<String> sexualOrientationList = new ArrayList<>();
    String yourGender;
    String yourCounty;
    private ArrayList<String> chosenCountiesList = new ArrayList<>();
*/

/*

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backbtn) {
            finish();
            return;
        }


        if (v.getId() == R.id.savebtn) {
            for(String button : selectedButtonsList) {//sorts out which text should go in which array

                if(tableClicked != null && tableClicked.equals("sexualOrientation")) {

                    // Check if the button is contained in the gender array
                    if (Arrays.asList(sexualOrientation).contains(button)) {
                        // Add the button to the chosenGender array if it's not already there
                        if (!sexualOrientationList.contains(button)) {
                            sexualOrientationList.add(button);

                        }
                    }
                }


                if(tableClicked != null && tableClicked.equals("languages")) {

                    // Check if the button is contained in the gender array
                    if (Arrays.asList(languages).contains(button)) {
                        // Add the button to the chosenGender array if it's not already there
                        if (!languagesList.contains(button)) {
                            languagesList.add(button);

                        }
                    }
                }

               if(tableClicked != null && tableClicked.equals("Gender")) {
                   if (Arrays.asList(gender).contains(button)) {
                       // Add the button to the chosenGender array if it's not already there
                       if (!Objects.equals(yourGender, button)) {
                           yourGender = button;
                       }
                   }
               }

                // Check if the button is contained in the counties array
                if(tableClicked != null && tableClicked.equals("yourcounty")) {
                    if (Arrays.asList(counties).contains(button)) {
                        // Add the button to the chosenCounties array if it's not already there
                        if (!Objects.equals(yourCounty, button)) {
                            yourCounty = button;
                        }
                    }
                }

                if (tableClicked != null && tableClicked.equals("chosencountiesLayout"))
                 if (Arrays.asList(counties).contains(button)) {
                    // Add the button to the chosenCounties array if it's not already there
                    if (!chosenCountiesList.contains(button)) {
                        chosenCountiesList.add(button);

                    }
                }

                if (cardview != null && cardview.equals("cardview"))
                    if (Arrays.asList(counties).contains(button)) {
                        // Add the button to the chosenCounties array if it's not already there
                        if (!chosenCountiesList.contains(button)) {
                            chosenCountiesList.add(button);

                        }
                    }

                // Check if the button is contained in the interests array


            DataBaseUtils queryDatabase = new ReusableQueries(this);

            queryDatabase.uploadToDataBase("languages",languagesList,this);

            queryDatabase.uploadToDataBase("yourGender",yourGender,this);


            queryDatabase.uploadToDataBase("county",yourCounty,this);

            queryDatabase.uploadToDataBase("sexualOrientation", sexualOrientationList,this);




            Gson gson = new Gson(); // Create a Gson instance
            String jsoncounties = gson.toJson(chosenCountiesList); // Convert the list of strings to a JSON string using Gson
            if(chosenCountiesList.size() >0) { // Check if the list contains any elements
                sharedPreferencesManager.saveString("chosenCounty", jsoncounties); // Store the JSON string in shared preferences for the "chosenCounty" key
            }




            Gson gson2 = new Gson(); // Create a Gson instance
            String jsonsexOrientation = gson2.toJson(sexualOrientationList); // Convert the array of strings to a JSON string using Gson
            if(sexualOrientationList != null) {
                   sharedPreferencesManager.saveString("sexualOrientation", jsonsexOrientation);
            }

            Gson gson3 = new Gson(); // Create a Gson instance
            String jsonlanguages = gson3.toJson(languagesList); // Convert the array of strings to a JSON string using Gson
            if(languagesList != null) {
                sharedPreferencesManager.saveString("languages", jsonlanguages);
            }

            if(yourGender != null) {
// Get the editor object to make changes to the preferences
// Store multiple string values in the preferences
                sharedPreferencesManager.saveString("yourGender", yourGender);
            }

// Save the changes

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

            return;*/
    }


}



