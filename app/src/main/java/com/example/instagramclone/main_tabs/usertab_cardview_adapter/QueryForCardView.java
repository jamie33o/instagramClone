package com.example.instagramclone.main_tabs.usertab_cardview_adapter;

import android.content.Context;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;


import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.likedprofiles_tab.LikedProfilesTab;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.reusable_code.ParseUtils.UtilsClass;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;

import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.ArrayList;

import java.util.List;

public class QueryForCardView {
    private List<String> chosenCountiesListCardView;
    private String genderPref;
    private List<String> chosenCountiesList;
   Context context;

    public QueryForCardView(Context context) {
        this.context = context;

        chosenCountiesListCardView = new ArrayList<>();

    }

    public void getQueryForCardView(List<ItemModel> itemmodelList,CardStackAdapter adapters,UsersTab usersTab) {
        //check so doesnt bring back same profiles


        if(itemmodelList!=null&&itemmodelList.size()>0)
         itemmodelList.clear();
        //query user profile
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if (parseModel != null) {

                        //todo add query to stop disliked profile from coming back

                        ParseQuery<ParseModel> parseQuery = ParseModel.getQuery(false);

                    if (parseModel.getGender() != null && parseModel.getGender() != null)
                        genderPref = parseModel.getGender().equals("Male") ? "Female" : "Male";

                    //sex orientation search
                    if (parseModel.getSexualOrientation() != null) {
                        switch (parseModel.getSexualOrientation()) {
                            case "Straight":
                                parseQuery.whereEqualTo("gender", genderPref);
                                parseQuery.whereNotEqualTo("sexualOrientaion", "Lesbian");
                                parseQuery.whereNotEqualTo("sexualOrientaion", "Gay");
                                break;
                            case "Gay":
                                parseQuery.whereEqualTo("sexualOrientaion", "Gay");
                                parseQuery.whereEqualTo("gender", parseModel.getGender());
                                break;
                            case "Lesbian":
                                parseQuery.whereEqualTo("sexualOrientaion", "Lesbian");
                                break;
                            case "Bisexual":
                                parseQuery.whereEqualTo("gender", genderPref);

                                if (parseModel.getGender().equals("Male")) {
                                    parseQuery.whereEqualTo("sexualOrientaion", "Gay");

                                } else {
                                    parseQuery.whereEqualTo("sexualOrientaion", "Lesbian");
                                }
                                break;
                        }
                    }
                        //counties search pref
                        if (parseModel.getSearchDistance() > 0) {

                            ParseGeoPoint location = parseModel.getLocation();

                            // Add a constraint to the query to retrieve users within ? kilometers from the location
                            parseQuery.whereWithinKilometers("location", location, parseModel.getSearchDistance());

                        } else if (parseModel.getChosenCounties() != null) {
                            chosenCountiesList = new ArrayList<>(parseModel.getChosenCounties());
                            parseQuery.whereContainedIn("whereIlive", chosenCountiesList);

                        }

                        parseQuery.findInBackground(new FindCallback<ParseModel>() {
                            @Override
                            public void done(List<ParseModel> parseModels, ParseException e) {

                                if (e == null) {
                                    if (!parseModels.isEmpty()) {
                                        for (ParseModel parsemodel : parseModels) {

                                            String name = parsemodel.getName();
                                            String age = parsemodel.getAge() + "";
                                            String county = parsemodel.getWhereILive();
                                            ParseUser userclassPointer = parsemodel.getUserClaassPointer();

                                            ParseFile imageFile = null;
                                            if (parsemodel.getImage1Data() != null) {
                                                imageFile = parsemodel.getImage1Data();
                                            }

                                            String imageUr2 = null;
                                            if (imageFile != null) {
                                                imageUr2 = imageFile.getUrl();
                                            }
                                            itemmodelList.add(new ItemModel(imageUr2, name, age, county, userclassPointer));

                                            if (itemmodelList.size() == parseModels.size()) {

                                                adapters.notifyDataSetChanged();
                                                if(itemmodelList.size()>0){
                                                    usersTab.searchPopUpBtn.setVisibility(View.GONE);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void queryForLikedProfiles(List<ItemModel> itemmodelList, RecyclerView.Adapter<?> adapters, LikedProfilesTab tab) {

        //query user profile
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if (parseModel != null) {



                        if (parseModel.getLikedUsers().size() >= 20) {
                            parseModel.getLikedUsers().subList(0, 5).clear();
                        }

                        ParseQuery<ParseModel> parseQuery = ParseModel.getQuery(false);




                        parseQuery.whereContainedIn("userclasspointer",parseModel.getLikedUsers());


                        parseQuery.findInBackground(new FindCallback<ParseModel>() {
                            @Override
                            public void done(List<ParseModel> parseModels, ParseException e) {

                                if (e == null) {
                                    if (!parseModels.isEmpty()) {
                                        for (ParseModel parsemodel : parseModels) {

                                            String name = parsemodel.getName();
                                            String age = parsemodel.getAge() + "";
                                            String county = parsemodel.getWhereILive();
                                            ParseUser userclassPointer = parsemodel.getUserClaassPointer();
                                            ParseFile imageFile = null;
                                            if (parsemodel.getImage1Data() != null) {
                                                imageFile = parsemodel.getImage1Data();
                                            }

                                            String imageUrllikeU = null;
                                            if (imageFile != null) {
                                                imageUrllikeU = imageFile.getUrl();
                                            }
                                            itemmodelList.add(new ItemModel(imageUrllikeU, name, age, county, userclassPointer));

                                            if (itemmodelList.size() == parseModels.size()) {

                                                adapters.notifyItemRangeInserted(0, itemmodelList.size());

                                            }
                                        }
                                    }else {
                                        tab.peopleYouLikedTV.setText("You have not liked any profiles yet!! \n better get swipping....");

                                    }
                                }
                            }
                        });
                      parseModel.pinInBackground();
                    }
                }

            }
        });
    }

    public void queryUserLikedMe(List<ItemModel> itemmodelList,RecyclerView.Adapter<?> adapters,LikedProfilesTab tab) {

        //query user profile
        ParseModel.getQuery(true).getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if (parseModel != null) {


                        ParseQuery<ParseModel> parseQuery = ParseModel.getQuery(false);

                        parseQuery.whereEqualTo("likedUsers", UtilsClass.getCurrentUser());

                        parseQuery.findInBackground(new FindCallback<ParseModel>() {
                            @Override
                            public void done(List<ParseModel> parseModels, ParseException e) {

                                if (e == null) {
                                    if (!parseModels.isEmpty()) {
                                        for (ParseModel parsemodel : parseModels) {

                                            String name = parsemodel.getName();
                                            String age = parsemodel.getAge() + "";
                                            String county = parsemodel.getWhereILive();
                                            ParseUser userclassPointer = parsemodel.getUserClaassPointer();
                                            ParseFile imageFile = null;
                                            if (parsemodel.getImage1Data() != null) {
                                                imageFile = parsemodel.getImage1Data();
                                            }

                                            String imageUrllikeMe = null;
                                            if (imageFile != null) {
                                                imageUrllikeMe = imageFile.getUrl();
                                            }
                                            itemmodelList.add(new ItemModel(imageUrllikeMe, name, age, county, userclassPointer));

                                            if (itemmodelList.size() == parseModels.size()) {

                                                adapters.notifyItemRangeInserted(0, itemmodelList.size());

                                            }
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        tab.peopleWhoLikedYouTV.setText("Sorry no like's yet!! \n check back later....");
                    }
                }


            }
        });

    }
}