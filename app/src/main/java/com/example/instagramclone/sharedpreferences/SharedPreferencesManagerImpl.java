package com.example.instagramclone.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesManagerImpl implements SharedPreferencesManager {
        private final SharedPreferences preferences;

        public SharedPreferencesManagerImpl(Context context, String name, int mode) {
            preferences = context.getSharedPreferences(name, mode);
        }

        @Override
        public void saveString(String key, String value) {
            preferences.edit().putString(key, value).apply();
        }

        @Override
        public String getString(String key, String defaultValue) {
            return preferences.getString(key, defaultValue);
        }

        @Override
        public void saveStringSet(String key, Set<String> values) {
            preferences.edit().putStringSet(key, values).apply();
        }

        @Override
        public Set<String> getStringSet(String key, Set<String> defaultValue) {
            return preferences.getStringSet(key, defaultValue);
        }

        @Override
        public void clear() {
            preferences.edit().clear().apply();
        }
    }



