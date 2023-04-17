package com.example.instagramclone.reusable_code.ParseUtils;

import com.parse.ParseUser;

public class UtilsClass {

        public static String getCurrentUsername() {
            if (getCurrentUser() != null) {
                return getCurrentUser().getUsername();
            }
            return null;

    }

    public static ParseUser getCurrentUser(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            return currentUser;
        }
        return null;

    }




}
