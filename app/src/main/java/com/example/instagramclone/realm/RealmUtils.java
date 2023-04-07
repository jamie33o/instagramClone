package com.example.instagramclone.realm;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUtils {
        public static RealmResults<RealmModel> getRealmModelsByUsername(Realm realm, String userName) {
            return realm.where(RealmModel.class)
                    .equalTo("userName", userName)
                    .findAll();
        }
    }



