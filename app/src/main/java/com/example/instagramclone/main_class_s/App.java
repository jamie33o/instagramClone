package com.example.instagramclone.main_class_s;

import android.app.Application;

import com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging.ParseMesssageModel;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.parse.Parse;
import com.parse.ParseObject;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(ParseMesssageModel.class);
        ParseObject.registerSubclass(ParseModel.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("A6lCqPiTdOmmevX2qQ5kScqDHEIGG7Fxx3NQUVHh")
                // if defined
                .clientKey("xOL54wSKUvfdDXg9ohb2kdeZsStfbhvmdQThXMa7")
                .server("https://ingnite.b4a.io/")
                .enableLocalDataStore()
                .build()

        );






        //for deleting realm database
       /* String realmPath = Realm.getDefaultConfiguration().getPath();
        File realmFile = new File(realmPath);
        if (realmFile.exists()) {
            realmFile.delete();*/
        }





}
