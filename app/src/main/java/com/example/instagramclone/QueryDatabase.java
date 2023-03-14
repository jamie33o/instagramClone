package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.instagramclone.edit_profile.EditProfile;
import com.example.instagramclone.edit_profile.RoundedCornersTransformation;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueryDatabase {

    String name;
    String age;
    String county;
    String userName;
    String profileBio;
    String profileProfession;
    String profileHobbies;
    UsersTab usersTab;


    SharedPreferences genderPrefs,countiePrefs;
    String[] chosenCounties, chosenInterests;

    String gender;
    String user;
    List<String> chosenCountiesListCardView;
    Context context;
    ProgressDialog dialog;

    String chosengender;

    SharedPreferences interestsprefs;

    public QueryDatabase(Context context) {
        this.context = context;
        user = ParseUser.getCurrentUser().getUsername();

        usersTab = new UsersTab();
        chosenCountiesListCardView = new ArrayList<>();

        genderPrefs = context.getSharedPreferences("chosengenderPrefs", Context.MODE_PRIVATE);
        countiePrefs = context.getSharedPreferences("countiesPrefs", Context.MODE_PRIVATE);
         interestsprefs = context.getSharedPreferences("choseninterests", Context.MODE_PRIVATE);


    }


    public void showProgressDialog(Context context) {
        dialog = new ProgressDialog(context, R.style.TransparentProgressDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    public void dismissProgressDialog(ProgressDialog dialog) {
        try {
            if (dialog != null && dialog.isShowing() && dialog.getWindow() != null) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception
        }
    }




    //uploads the user info one at a time

    public void putQueryDatabase(String columnKey, Object columnValue,Context context) {




        if (columnValue != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");

// Set the query to filter based on the "username" column

            query.whereEqualTo("username",user);
            //showProgressDialog(context);

            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        if (columnValue instanceof String) {
                            object.put(columnKey, columnValue);

                        } else if(columnValue instanceof String[]) {

                            object.put(columnKey, Arrays.asList(columnValue));
                        } else if (columnValue instanceof ParseFile) {
                            object.put(columnKey, columnValue);
                            System.out.println(columnKey);

                        }
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    //if (columnValue instanceof ParseFile)
                                       // getCurrentUserImagesQueryDatabase();
                                    FancyToast.makeText(context, "Success", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                } else {
                                    FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                                //   dismissProgressDialog(dialog);
                            }
                        });
                    } else {
                        FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }

                }
            });
        }
    }





    public void getQueryDatabaseCardview(UsersTab usersTab) {

        // Get the shared preferences object

// Retrieve the string values from the preferences
        int i = 0;
        while (countiePrefs.contains("ChosenCounty" + i)) {
            String countypref = countiePrefs.getString("ChosenCounty" + i, "");
            chosenCountiesListCardView.add(countypref);
            i++;
        }

        chosengender = genderPrefs.getString("ChosenGender", "");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");

             if (chosenCountiesListCardView.size() > 0)
               parseQuery.whereContainedIn("county", Arrays.asList(chosenCountiesListCardView));

             parseQuery.whereNotEqualTo("username", user);

        parseQuery.whereContains("chosengender", chosengender);

        if(!parseQuery.hasCachedResult()){
          usersTab.choicesPopUp();
        }

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseObject post : objects) {



                            String name = post.get("name") + "";
                            String age = post.get("age") + "";
                            String county = post.get("county") + "";

                            ParseFile imageFile = (ParseFile) post.get("image1");
                            assert imageFile != null;
                            String imageUr2 = imageFile.getUrl();

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


    public void getCurrentUserImagesQueryDatabase(EditProfile editProfile) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");

        query.whereEqualTo("username", user);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject userObject, ParseException e) {


                //if (userObject.get("image1") != null) {
                    ParseFile imageFile = (ParseFile) userObject.get("image1");
                    assert imageFile != null;
                    String imageUrl1 = imageFile.getUrl();
                    Picasso.get()
                            .load(imageUrl1)
                            .placeholder(R.drawable.mainplaceholder)
                            .transform(new RoundedCornersTransformation(20,0))
                            .resize(250,400)
                            .into(editProfile.imgViewShare);
              //  }
               // if (userObject.get("image2") != null) {
                    ParseFile imageFile2 = (ParseFile) userObject.get("image2");
                    assert imageFile2 != null;
                    String imageUrl2 = imageFile2.getUrl();
                    Picasso.get()
                            .load(imageUrl2)
                            .placeholder(R.drawable.mainplaceholder)
                            .transform(new RoundedCornersTransformation(20,0))
                            .resize(250,400)
                            .into(editProfile.imgViewShare1);
               // }
               // if (userObject.get("image3") != null) {
                    ParseFile imageFile3 = (ParseFile) userObject.get("image3");
                    assert imageFile3 != null;
                    String imageUrl3 = imageFile3.getUrl();
                    Picasso.get()
                            .load(imageUrl3)
                            .placeholder(R.drawable.mainplaceholder)
                            .resize(250,400)

                            .transform(new RoundedCornersTransformation(20,0))
                            .into(editProfile.imgViewShare2);
               // }
// Load and cache image 2

            }
        });
    }

            public void queryDatabaseProfile(EditProfile editProfile) {

        ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Profile");
                queryAll.whereEqualTo("username", user);

                queryAll.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> Object, ParseException e) {

                        if (e == null) {

                            if (Object != null) {
                                ParseObject userObject = Object.get(0);


                                if (userObject.get("name") != null)
                                    name = userObject.get("name") + "";
                                if (userObject.get("age") != null)
                                    age = userObject.get("age") + "";

                                if (userObject.get("county") != null)
                                    county = userObject.get("county") + "";
                                if (userObject.get("username") != null)
                                    userName = userObject.get("username") + "";
                                if (userObject.get("profession") != null)
                                    profileProfession = userObject.get("profession") + "";
                                if (userObject.get("bio") != null)
                                    profileBio = userObject.get("bio") + "";

                            }
                        }
                            List<String> chosenCountiesList = new ArrayList<>();
                            int i = 0;
                            while (countiePrefs.contains("ChosenCounty" + i)) {
                                String countypref = countiePrefs.getString("ChosenCounty" + i, "");
                                chosenCountiesList.add(countypref);
                                i++;
                            }
                        List<String> chosenInterestsList = new ArrayList<>();
                            int x = 0;
                            while (interestsprefs.contains("choseninterests" + x)) {
                                String interestpref = interestsprefs.getString("choseninterests" + x, "");
                                chosenInterestsList.add(interestpref);
                                x++;
                            }
                            chosengender = genderPrefs.getString("gender", "");

                            chosenCounties = chosenCountiesList.toArray(new String[0]);
                            chosenInterests = chosenInterestsList.toArray(new String[0]);




                        editProfile.addList(name, age, county, userName, profileBio, profileProfession, profileHobbies, chosenInterests, chosenCounties);



                    }

                });

            }





    }









