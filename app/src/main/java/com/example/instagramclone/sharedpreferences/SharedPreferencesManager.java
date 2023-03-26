package com.example.instagramclone.sharedpreferences;


import java.util.Set;

public interface SharedPreferencesManager {
        void saveString(String key, String value);
        String getString(String key, String defaultValue);
        void saveStringSet(String key, Set<String> values);
        Set<String> getStringSet(String key, Set<String> defaultValue);
        void clear();
    }




