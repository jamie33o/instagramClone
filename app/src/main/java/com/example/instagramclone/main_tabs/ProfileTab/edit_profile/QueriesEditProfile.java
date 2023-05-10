package com.example.instagramclone.main_tabs.ProfileTab.edit_profile;

import android.content.Context;

import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.reusable_code.Dialogs;
import com.parse.GetCallback;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.File;

public class QueriesEditProfile {
    Context context;

    public QueriesEditProfile(Context context) {
        this.context = context;
    }

    public void uploadImages(File file1, String column) {

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    ParseFile file = new ParseFile(file1, column + ".png");

                    // Save the file before setting it as data for ParseModel
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                switch (column) {
                                    case "image1":
                                        parseModel.setImage1Data(file);
                                        break;
                                    case "image2":
                                        parseModel.setImage2Data(file);
                                        break;
                                    case "image3":
                                        parseModel.setImage3Data(file);
                                        break;
                                }

                                parseModel.pinInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Dialogs.showSnackbar(context, "Success!!!\n Image saved", 2000);
                                        } else {
                                            Dialogs.showSnackbar(context, "Error!!!\n Image not saved", 2000);
                                        }
                                    }
                                });
                            } else {
                                Dialogs.showSnackbar(context, "Error!!!\n File not saved", 2000);
                            }
                        }
                    });

                } else {
                    Dialogs.showSnackbar(context, e.getMessage(), 2000);
                }
            }
        });

    }
}