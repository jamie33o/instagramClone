package com.example.instagramclone.edit_profile_N_profile;

import android.content.Context;
import android.os.Handler;

import com.example.instagramclone.reusable_code.Dialogs;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class QueriesEditProfile implements SharedPreferencesManager{
   public String name;

    String age;
    String county;
    String userName;
    String profileBio;
    String yourgender;
    String profileProfession;

    Dialogs dialogs;

    private final SharedPreferencesManager preferencesManager;

    EditProfileModel editProfileModel;
    Context context;
    public QueriesEditProfile(Context context){

        this.context = context;


        preferencesManager = new SharedPreferencesManagerImpl(context, "Profile", Context.MODE_PRIVATE);

        editProfileModel = new EditProfileModel();

        dialogs = new Dialogs();
    }

    public void getUserProfileFromSharedPrefs(EditProfile editProfile){

        name = preferencesManager.getString("name","");
        age = preferencesManager.getString("age","");
        //county = preferencesManager.getString("county","");
        userName = preferencesManager.getString("username","");
        profileProfession = preferencesManager.getString("profession","");
        yourgender = preferencesManager.getString("yourGender","");
        profileBio = preferencesManager.getString("bio","");


        String savedJson = preferencesManager.getString("whereilive", ""); // Retrieve the JSON string from shared preferences for the "chosenCounty" key
        Gson gson = new Gson(); // Create a Gson instance
        String[] wherilive = gson.fromJson(savedJson, String[].class);

        String sexualOrientationJson = preferencesManager.getString("sexualOrientation", ""); // Retrieve the JSON string from shared preferences for the "chosenCounty" key
        Gson gson1 = new Gson(); // Create a Gson instance
        String[] sexualOrientationArray = gson1.fromJson(sexualOrientationJson, String[].class);


            String savedJsoninterest = preferencesManager.getString("interests", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson2 = new Gson(); // Create a Gson instance
        String[] chosenInterests = gson2.fromJson(savedJsoninterest, String[].class);



        String savedlanguages = preferencesManager.getString("languages", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson3 = new Gson();
        String[] languages = gson3.fromJson(savedlanguages, String[].class);

        String savedmybasics = preferencesManager.getString("mybasics", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson4 = new Gson();
        String[] mybasics = gson4.fromJson(savedmybasics, String[].class);

        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                editProfile.loadViews( mybasics, profileBio,sexualOrientationArray,yourgender, profileProfession, languages, chosenInterests, wherilive);

            }

        }, 1000);




    }


    public void queryProfileNstoreInSharedPrefs() {



        ParseQuery<ParseObject> query = new ParseQuery<>("Profile");
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject Object, ParseException e) {

                if (e == null) {

                    if (Object != null) {

                        if (Object.get("name") != null)
                            name = Object.get("name") + "";
                        if (Object.get("age") != null)
                            age = Object.get("age") + "";
                        if (Object.get("county") != null)
                            county = Object.get("county") + "";
                        if (Object.get("username") != null)
                            userName = Object.get("username") + "";
                        if (Object.get("profession") != null)
                            profileProfession = Object.get("profession") + "";
                        if(Object.get("yourGender") != null)
                            yourgender = Object.get("yourGender") +"";
                         if (Object.get("bio") != null)
                            profileBio = Object.get("bio") + "";

                        ParseFile imageFile1 = (ParseFile) Object.get("image1");
                        if(imageFile1!=null) {

                            imageFile1.getFileInBackground(new GetFileCallback() {
                                @Override
                                public void done(File file1, ParseException e) {
                                    if(e == null) {
                                        preferencesManager.saveString("image1", file1.getAbsolutePath());

                                    }
                                }
                            });


                        }
                            ParseFile imageFile2 = (ParseFile) Object.get("image2");
                        if(imageFile2!=null) {
                            imageFile2.getFileInBackground(new GetFileCallback() {
                                @Override
                                public void done(File file2, ParseException e) {
                                    if(e == null) {
                                        preferencesManager.saveString("image2", file2.getAbsolutePath());
                                    }
                                }
                            });


                        }
                            ParseFile imageFile3 = (ParseFile) Object.get("image3");
                        if(imageFile3!=null) {
                            imageFile3.getFileInBackground(new GetFileCallback() {
                                @Override
                                public void done(File file3, ParseException e) {
                                    if(e == null) {
                                        preferencesManager.saveString("image3", file3.getAbsolutePath());
                                    }
                                }
                            });

                        }



                        preferencesManager.saveString("name",name);
                         preferencesManager.saveString("age", age);
                          preferencesManager.saveString("county",county);
                         preferencesManager.saveString("username", userName);
                          preferencesManager.saveString("profession",profileProfession);
                          preferencesManager.saveString("yourGender",yourgender);
                          preferencesManager.saveString("bio",profileBio);

                    }
                }


        }

        });

    }
    public void putUsernameNprofileUsername(String username, Context context){

        ParseQuery<ParseObject> query = new ParseQuery<>("Profile");
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject profile, ParseException e) {
                if (e == null && profile != null) {

                    Objects.requireNonNull(UtilsClass.getCurrentUser()).setUsername(username);
                    profile.put("username", username);
                    preferencesManager.saveString("username",username);

                    ParseObject.saveAllInBackground(Arrays.asList(UtilsClass.getCurrentUser(), profile), new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                UtilsClass.getCurrentUser().fetchInBackground();
                               FancyToast.makeText(context, "Username Updated", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                } else {
                    FancyToast.makeText(context, "Error retrieving profile", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
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
