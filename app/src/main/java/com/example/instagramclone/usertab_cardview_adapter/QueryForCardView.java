package com.example.instagramclone.usertab_cardview_adapter;

import android.content.Context;

import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class QueryForCardView {
    String imageUr2;
    List<String> chosenCountiesListCardView;
    String userClassUserName;
    ParseUser userClass;
    String chosengender;
    private Realm realm;
    private RealmModel results;
    String genderPref;

    List<String> chosenCountiesList;
    RealmList<String> realmList;

    public QueryForCardView(Context context) {


        userClass = ParseUser.getCurrentUser();
        userClassUserName = userClass.getUsername();

        chosenCountiesListCardView = new ArrayList<>();


        realm = RealmManager.getRealmInstance();

        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();

    }

    public void getQueryDatabaseCardview(UsersTab usersTab) {

        // Get the shared preferences object



        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");

        parseQuery.whereNotEqualTo("username", UtilsClass.getCurrentUsername());
        if(results!=null) {

        realm.beginTransaction();

        if(results.getChosenCounties()!=null)
            realmList = results.getChosenCounties();

        if(realmList.size()>0) {
                results.setSearchDistance(0);
                chosenCountiesList = new ArrayList<>(realmList);
                parseQuery.whereContainedIn("whereilive", chosenCountiesList);
            }


            if(results.getGender()!=null&&results.getGender()!=null)
                genderPref = results.getGender().equals("Male") ? "Female" : "Male";

            if(results.getSexualOrientaion()!= null) {
                switch (results.getSexualOrientaion()) {
                    case "Straight":
                        parseQuery.whereEqualTo("gender", genderPref);
                        parseQuery.whereNotEqualTo("sexualOrientation", "Lesbian");
                        parseQuery.whereNotEqualTo("sexualOrientation", "Gay");
                        break;
                    case "Gay":
                        parseQuery.whereEqualTo("sexualOrientation", "Gay");
                        parseQuery.whereEqualTo("gender", results.getGender());
                        break;
                    case "Bisexual":
                        parseQuery.whereEqualTo("gender", genderPref);

                        if (results.getGender().equals("Male")) {
                            parseQuery.whereEqualTo("sexualOrientation", "Gay");

                        } else {
                            parseQuery.whereEqualTo("sexualOrientation", "Lesbian");
                        }
                        break;
                }
            }
// Define the latitude and longitude of the location you want to search within
        double latitude = results.getLattitude();
        double longitude = results.getLongitude();

// Create a ParseGeoPoint object for the location you want to search within
        ParseGeoPoint location = new ParseGeoPoint(latitude, longitude);

// Add a constraint to the query to retrieve users within 10 kilometers from the location
        if(results.getSearchDistance()>0) {
            parseQuery.whereWithinKilometers("location", location, results.getSearchDistance());
        }
        realm.commitTransaction();
    }

        if (!parseQuery.hasCachedResult())
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
                        if (post.get("image1") != null) {
                            imageFile = (ParseFile) post.get("image1");
                        }

                        if (imageFile != null) {
                            imageUr2 = imageFile.getUrl();
                        }
                        usersTab.addList(imageUr2, name, age, county);
                        if (usersTab.items.size() == objects.size()) {

                            usersTab.setNewItems(usersTab.items);
                        }

                        if (usersTab.startPages) {
                            usersTab.pages();
                            usersTab.startPages = false;
                        }

                    }
                }
            }
        });

    }
}