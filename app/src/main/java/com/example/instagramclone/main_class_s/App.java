package com.example.instagramclone.main_class_s;

import android.app.Application;

import com.parse.Parse;



import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("A6lCqPiTdOmmevX2qQ5kScqDHEIGG7Fxx3NQUVHh")
                // if defined
                .clientKey("xOL54wSKUvfdDXg9ohb2kdeZsStfbhvmdQThXMa7")
                .server("https://parseapi.back4app.com/")
                .build()
        );
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1) // Increment the schema version number
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

     //for deleting realm database
       /* String realmPath = Realm.getDefaultConfiguration().getPath();
        File realmFile = new File(realmPath);
        if (realmFile.exists()) {
            realmFile.delete();*/
        }





}
