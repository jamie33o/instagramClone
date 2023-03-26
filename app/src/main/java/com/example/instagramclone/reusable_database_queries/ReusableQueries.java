package com.example.instagramclone.reusable_database_queries;

import android.content.Context;

import com.example.instagramclone.reusable_code.Dialogs;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReusableQueries implements DataBaseUtils {
    SharedPreferencesManager sharedPreferencesManager;

    Context context;
    Dialogs dialogs;

    public String imageUrl1,imageUrl;
    public ReusableQueries(Context context) {
        this.context = context;
        sharedPreferencesManager = new SharedPreferencesManagerImpl(context,"Profile", Context.MODE_PRIVATE);

        dialogs = new Dialogs();

    }



    public void deleteFileFromDatabase(String columnName) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Profile");
        dialogs.showProgressDialog(context);
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // Check if column exists in the object
                    if (object.containsKey(columnName)) {
                        object.remove(columnName);

                        //deletes image filepath from preferences
                        sharedPreferencesManager.saveString(columnName,"null");
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                    dialogs.dismissProgressDialog(context, "Picture Deleted!", true);

                                } else {
                                    dialogs.dismissProgressDialog(context, "Picture Couldnt be Deleted", false);
                                }
                            }
                        });
                    } else {
                        // No image to delete
                        dialogs.dismissProgressDialog(context, "Picture Deleted!", true);
                    }
                } else {
                    // Error fetching object
                    dialogs.dismissProgressDialog(context, "Picture Couldnt be Deleted", false);
                }
            }
        });
    }


    //uploads the user info one at a time

    public void uploadToDataBase(String columnKey, Object columnValue, Context context) {


        if (columnValue != null) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Profile");

            query.whereEqualTo("username", UtilsClass.getCurrentUsername());

            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        if (columnValue instanceof String) {
                            sharedPreferencesManager.saveString(columnKey,columnValue.toString());
                            object.put(columnKey, columnValue);
                        } else if (columnValue instanceof List) {
                            object.put(columnKey, Collections.singletonList(columnValue));
                        } else if (columnValue instanceof ParseFile) {
                            object.put(columnKey, columnValue);
                        }else if (columnValue instanceof String[]) {
                            object.put(columnKey, columnValue);
                        }
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                    if(columnKey.equals("username"))
                                        Objects.requireNonNull(UtilsClass.getCurrentUser()).fetchInBackground();
                                    FancyToast.makeText(context, "Success", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                } else {
                                    FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                            }
                        });
                    } else {
                        FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            });
        }
    }
}
