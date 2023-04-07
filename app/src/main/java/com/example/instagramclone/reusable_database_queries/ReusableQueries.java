package com.example.instagramclone.reusable_database_queries;

import android.content.Context;
import android.view.ViewParent;

import com.example.instagramclone.reusable_code.Snackbar_Dialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import io.realm.Realm;

public class ReusableQueries implements DataBaseUtils {
    Realm realm;

    Context context;

    public ReusableQueries(Context context) {
        this.context = context;

        // Get the object you want to update


    }


    public void deleteFileFromDatabase(String columnName) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Profile");
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // Check if column exists in the object

                    object.remove(columnName);

                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Snackbar_Dialog.showSnackbar(context,"Image deleted", 2000);

                               // Snackbar_Dialog.showAlert(context, "Image deleted", 1, true);


                            } else {
                               // Snackbar_Dialog.showAlert(context, "Image Couldnt be Deleted", 2, true);
                                Snackbar_Dialog.showSnackbar(context,"Error deleting image", 2000);

                            }
                        }
                    });
                } else {
                 //   Snackbar_Dialog.showAlert(context, e.getMessage(), 2, true);
                    Snackbar_Dialog.showSnackbar(context,"Error deleting image", 2000);

                }
            }

        });
    }
}