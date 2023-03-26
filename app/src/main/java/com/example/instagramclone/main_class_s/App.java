package com.example.instagramclone.main_class_s;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       // Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("A6lCqPiTdOmmevX2qQ5kScqDHEIGG7Fxx3NQUVHh")
                // if defined
                .clientKey("xOL54wSKUvfdDXg9ohb2kdeZsStfbhvmdQThXMa7")
                .server("https://parseapi.back4app.com/")
                .build()
               // eqiw9g1mn1lt5u32DTwPL8aHgNMramByygTuXuml
          //      4U0hOsiBfYBuQUcTctHWX4CdoPtU4kLrzbd6yK5b
        );
    }



}
