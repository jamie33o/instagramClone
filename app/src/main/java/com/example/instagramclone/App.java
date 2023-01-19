package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("eqiw9g1mn1lt5u32DTwPL8aHgNMramByygTuXuml")
                // if defined
                .clientKey("4U0hOsiBfYBuQUcTctHWX4CdoPtU4kLrzbd6yK5b")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }



}
