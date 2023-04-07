package com.example.instagramclone.sharedpreferences;


import java.util.Set;

public interface SharedPreferencesManager {
        void saveString(String key, String value);
        String getString(String key, String defaultValue);
        void saveInt(String key, Integer value);
        Integer getInt(String key, Integer defaultValue);
        void saveStringSet(String key, Set<String> values);
        void saveBoolean(String key, boolean value);
        boolean getBoolean(String key, boolean defaultValue);
        Set<String> getStringSet(String key, Set<String> defaultValue);
        void clear();
    }




