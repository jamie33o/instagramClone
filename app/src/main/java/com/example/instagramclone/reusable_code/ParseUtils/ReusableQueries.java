package com.example.instagramclone.reusable_code.ParseUtils;

import android.content.Context;

import com.example.instagramclone.reusable_code.Snackbar_Dialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;


public class ReusableQueries implements DataBaseUtils {

    Context context;

    public ReusableQueries(Context context) {
        this.context = context;

    }


    public void deleteFileFromDatabase(String columnName) {
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    // Check if column exists in the object

                    parseModel.remove(columnName);
                    parseModel.remove(columnName+"Name");
                    parseModel.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Snackbar_Dialog.showSnackbar(context,"Image deleted", 2000);

                            } else {
                                Snackbar_Dialog.showSnackbar(context,"Error deleting image", 2000);

                            }
                        }
                    });
                } else {
                    Snackbar_Dialog.showSnackbar(context,"Error deleting image", 2000);

                }
            }

        });
    }


}