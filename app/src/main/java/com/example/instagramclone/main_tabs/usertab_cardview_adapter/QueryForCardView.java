package com.example.instagramclone.main_tabs.usertab_cardview_adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;


import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.likedprofiles_tab.MessagesTab;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.reusable_code.ParseUtils.UtilsClass;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;

import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.io.File;
import java.util.ArrayList;

import java.util.List;

public class QueryForCardView {
    private String genderPref;
    private List<String> chosenCountiesList;
   Context context;
    int cvIndex,likedIndex,likeUIndex;

    public QueryForCardView(Context context) {
        this.context = context;


    }

    public void getQueryForCardView(List<ItemModel> itemmodelList,CardStackAdapter adapters,UsersTab usersTab) {
        //check so doesnt bring back same profiles

         cvIndex = 0;


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

                    if (parseModel.getGender() != null)
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

                                            itemmodelList.add(new ItemModel(name, age, county, userclassPointer, parseModel.getIsPayed(),parseModel.getBoolShowLocation(),parseModel.getBoolShowAge()));

                                            ParseFile imageFile1;
                                            if (parsemodel.getImage1Data() != null) {
                                                imageFile1 = parsemodel.getImage1Data();
                                                int count = cvIndex;
                                                imageFile1.getFileInBackground(new GetFileCallback() {
                                                    @Override
                                                    public void done(File file, ParseException e) {
                                                        itemmodelList.get(count).setImage1(file.getAbsolutePath());
                                                    }
                                                });
                                            }
                                            ParseFile imageFile2;
                                            if (parsemodel.getImage2Data() != null) {
                                                imageFile2 = parsemodel.getImage2Data();
                                                int count = cvIndex;
                                                imageFile2.getFileInBackground(new GetFileCallback() {
                                                    @Override
                                                    public void done(File file, ParseException e) {
                                                        itemmodelList.get(count).setImage2(file.getAbsolutePath());
                                                    }
                                                });
                                            }
                                            ParseFile imageFile3;
                                            if (parsemodel.getImage3Data() != null) {
                                                imageFile3 = parsemodel.getImage3Data();
                                                int count = cvIndex;
                                                imageFile3.getFileInBackground(new GetFileCallback() {
                                                    @Override
                                                    public void done(File file, ParseException e) {
                                                        itemmodelList.get(count).setImage3(file.getAbsolutePath());
                                                    }
                                                });
                                            }
                                            cvIndex++;
                                            if (itemmodelList.size() == parseModels.size()) {
                                                android.os.Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapters.notifyDataSetChanged();

                                                    }
                                                },600);
                                                if(itemmodelList.size()>0){

                                                    usersTab.searchPopUpBtn.setVisibility(View.GONE);
                                                    usersTab.like.setVisibility(View.VISIBLE);
                                                    usersTab.dislike.setVisibility(View.VISIBLE);
                                                    usersTab.rewind.setVisibility(View.VISIBLE);

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

    public void queryForLikedProfiles(List<ItemModel> itemmodelList, RecyclerView.Adapter<?> adapters, MessagesTab tab) {

        likedIndex = 0;
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

                                            int count = likedIndex;

                                            ParseFile imageFile = null;
                                            if (parsemodel.getImage1Data() != null) {
                                                imageFile = parsemodel.getImage1Data();
                                                imageFile.getFileInBackground(new GetFileCallback() {
                                                    @Override
                                                    public void done(File file, ParseException e) {
                                                        itemmodelList.get(count).setImage1(file.getAbsolutePath());

                                                    }
                                                });
                                            }


                                            itemmodelList.add(new ItemModel(name, age, county, userclassPointer, parseModel.getIsPayed(),parseModel.getBoolShowLocation(),parseModel.getBoolShowAge()));

                                            if (itemmodelList.size() == parseModels.size()) {

                                                adapters.notifyItemRangeInserted(0, itemmodelList.size());

                                            }
                                            likedIndex++;
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

    public void queryUserLikedMe(List<ItemModel> itemmodelList, RecyclerView.Adapter<?> adapters, MessagesTab tab) {

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
                                            boolean isPayed = parsemodel.getIsPayed();
                                            ParseUser userclassPointer = parsemodel.getUserClaassPointer();
                                            itemmodelList.add(new ItemModel(name, age, county, userclassPointer,isPayed,parseModel.getBoolShowLocation(),parseModel.getBoolShowAge()));

                                            ParseFile imageFile;
                                            if (parsemodel.getImage1Data() != null) {
                                                int count = likeUIndex;

                                                imageFile = parsemodel.getImage1Data();
                                                imageFile.getFileInBackground(new GetFileCallback() {
                                                    @Override
                                                    public void done(File file, ParseException e) {
                                                        itemmodelList.get(count).setImage1(file.getAbsolutePath());

                                                    }
                                                });


                                            }



                                            if (itemmodelList.size() == parseModels.size()) {

                                                adapters.notifyItemRangeInserted(0, itemmodelList.size());

                                            }
                                            likeUIndex++;
                                        }
                                    }else {
                                        tab.peopleWhoLikedYouTV.setText("Sorry no like's yet!! \n check back later....");
                                    }
                                }
                            }
                        });
                    }
                }


            }
        });

    }
}