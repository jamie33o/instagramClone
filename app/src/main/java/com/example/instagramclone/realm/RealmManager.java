package com.example.instagramclone.realm;

import io.realm.Realm;

public class RealmManager {
    private static Realm sRealmInstance;

    public static Realm getRealmInstance() {
        if (sRealmInstance == null) {
            sRealmInstance = Realm.getDefaultInstance();
        }
        return sRealmInstance;
    }

    public static void closeRealmInstance() {
        if (sRealmInstance != null) {
            sRealmInstance.close();
            sRealmInstance = null;
        }
    }
}
