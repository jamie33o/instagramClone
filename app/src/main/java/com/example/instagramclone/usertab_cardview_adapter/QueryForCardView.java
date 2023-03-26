package com.example.instagramclone.usertab_cardview_adapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class QueryForCardView  implements SharedPreferencesManager {
    SharedPreferences interestsprefs;
    String imageUr2;
    List<String> chosenCountiesListCardView;
    String userClassUserName;
    ParseUser userClass;
    String chosengender;
    SharedPreferencesManager sharedPreferencesManager;

    public  QueryForCardView (Context context){
       sharedPreferencesManager = new SharedPreferencesManagerImpl(context,"Profile", Context.MODE_PRIVATE);


        userClass = ParseUser.getCurrentUser();
        userClassUserName = userClass.getUsername();

        chosenCountiesListCardView = new ArrayList<>();
    }

    public void getQueryDatabaseCardview(UsersTab usersTab) {

        // Get the shared preferences object

        // Retrieve the string values from the preferences
        String savedJson = sharedPreferencesManager.getString("chosenCounty", ""); // Retrieve the JSON string from shared preferences for the "chosenCounty" key
        Gson gson = new Gson(); // Create a Gson instance
        List<String> chosenCountiesList = gson.fromJson(savedJson, new TypeToken<List<String>>(){}.getType()); // Convert the JSON string to a List of strings using Gson


        chosengender = sharedPreferencesManager.getString("ChosenGender", "");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");

        System.out.println(Arrays.asList(chosenCountiesList));

        parseQuery.whereNotEqualTo("username",UtilsClass.getCurrentUsername());
        parseQuery.whereContains("yourGender",chosengender);

        if(chosenCountiesList!=null)
            parseQuery.whereContainedIn("county",chosenCountiesList);

        if(!parseQuery.hasCachedResult())
            usersTab.choicesPopUp();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    System.out.println(objects.size());
                    for (ParseObject post : objects) {

                        String name = post.get("name") + "";
                        String age = post.get("age") + "";
                        String county = post.get("county") + "";
                        ParseFile imageFile = null;
                        if(post.get("image1") != null) {
                             imageFile = (ParseFile) post.get("image1");
                        }

                       if(imageFile != null) {
                            imageUr2 = imageFile.getUrl();
                       }
                        usersTab.addList(imageUr2, name, age, county);
                        if (usersTab.items.size() == objects.size()) {

                            usersTab.setNewItems(usersTab.items);
                        }

                        if(usersTab.startPages){
                            usersTab.pages();
                            usersTab.startPages = false;
                        }

                    }
                }
            }
        });

    }

    @Override
    public void saveString(String key, String value) {

    }

    @Override
    public String getString(String key, String defaultValue) {
        return null;
    }

    @Override
    public void saveStringSet(String key, Set<String> values) {

    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return null;
    }

    @Override
    public void clear() {

    }
}
