package com.example.instagramclone;

import static com.parse.Parse.getApplicationContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QueryDatabase {

    String name;
    String age;
    String county;
    String userName;
    String profileBio;
    String profileProfession;
    String profileHobbies;
    String sports;


    String[] countiesArray,interests;

    String gender;
    String user;
    Context context;

    int counter = 0;

    UsersTab usersTab;
    public QueryDatabase(Context context) {


        this.context = context;
        user = ParseUser.getCurrentUser().getUsername();
       // interests = new String[0];

        countiesArray = new String[0];



    }

    //uploads the user info one at a time

    public void putQueryDatabase(String columnKey, Object columnValue) {
        if (columnValue != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");

// Set the query to filter based on the "username" column
            query.whereEqualTo("username", user);


            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        if (columnValue instanceof String) {
                            //   System.out.println(columnValue);
                            object.put(columnKey, columnValue);

                        } else {
                            // System.out.println(columnValue);
                            object.put(columnKey, new ArrayList<>(Collections.singleton(columnValue)));
                        }
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
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

    /*public void getUserInfoQueryDatabase() {
        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");

        parseQuery.whereEqualTo("username", user);


        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    // Retrieve fields and store them in separate arrays and a string

                     age = objects.get(0).getString("age");
                     county = objects.get(0).getString("county");
                     gender = objects.get(0).getString("gender");
                    JSONArray interestsJSONArray = objects.get(0).getJSONArray("choseninterest");
                    JSONArray countiesJSONArray = objects.get(0).getJSONArray("chosencounties");
                    List<String> interestsList = new ArrayList<>();
                    List<String> countiesList = new ArrayList<>();
                    for (int i = 0; i < Objects.requireNonNull(interestsJSONArray).length(); i++) {
                        try {
                            interestsList.add(interestsJSONArray.getString(i));
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    for (int i = 0; i < countiesJSONArray.length(); i++) {
                        try {
                            countiesList.add(countiesJSONArray.getString(i));
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                     interests = interestsList.toArray(new String[interestsList.size()]);
                   counties = countiesList.toArray(new String[countiesList.size()]);

                }
                    dialog.dismiss();

            }
        });
    }

*/






    public void getQueryDatabaseCardview(UsersTab usersTab) {
        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");

        /*for (String interest : interests) {
            parseQuery.whereContains("choseninterests", interest);
        }

       parseQuery.whereContains("chosencounties",county);*/
        parseQuery.orderByDescending("createdAt");

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject post : objects) {



                        String name = post.get("name") + "";
                        String age = post.get("age") + "";
                        String county = post.get("county") + "";


                        ParseFile postPicture = (ParseFile) post.get("image1");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null) {
                                    counter++;
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    File file = new File(getApplicationContext().getCacheDir(), counter + ".jpg");
                                    String filePath = file.getAbsolutePath();
                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(filePath);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    } catch (FileNotFoundException a) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            fos.close();
                                        } catch (IOException a) {
                                            e.printStackTrace();
                                        }
                                    }

                                    usersTab.addList(filePath, name, age, county);


                                    if (usersTab.items.size() == objects.size()) {

                                        usersTab.setNewItems(usersTab.items);

                                    }

                                }
                                dialog.dismiss();
                            }
                        });
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
    }



    public void queryDatabaseProfile(MyProfileTab myProfileTab){




        ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Profile");
        queryAll.whereEqualTo("username",user);
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.show();


        queryAll.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> Object, ParseException e) {

                if (e == null) {

                    if (Object != null) {
                        ParseObject userObject = Object.get(0);


                        if (userObject.get("name") != null)
                            name = userObject.get("name") + "";
                        if (userObject.get("age") != null)
                            age = userObject.get("age") + "";

                        if (userObject.get("county") != null)
                            county = userObject.get("county") + "";
                        if (userObject.get("username") != null)
                            userName = userObject.get("username") + "";
                        if (userObject.get("profession") != null)
                            profileProfession = userObject.get("profession") + "";
                        if (userObject.get("bio") != null)
                            profileBio = userObject.get("bio") + "";

                        if(userObject.getList("choseninterest") != null ){
                            JSONArray jsonArray = new JSONArray(userObject.getList("choseninterest"));
                            JSONArray innerArray = null;
                            try {
                                innerArray = jsonArray.getJSONArray(0);
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            }
                            interests = new String[innerArray.length()];
                            for (int i = 0; i < innerArray.length(); i++) {
                                try {
                                    interests[i] = innerArray.getString(i);
                                } catch (JSONException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }

                        if(userObject.getList("chosencounties") != null ){
                            JSONArray jsonArray = new JSONArray(userObject.getList("chosencounties"));
                            JSONArray innerArray = null;
                            try {
                                innerArray = jsonArray.getJSONArray(0);
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            }
                            countiesArray = new String[innerArray.length()];
                            for (int i = 0; i < innerArray.length(); i++) {
                                try {
                                    countiesArray[i] = innerArray.getString(i);
                                } catch (JSONException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }





                        if (userObject.get("image1") != null) {
                            ParseFile postPicture = (ParseFile) userObject.get("image1");
                            postPicture.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (data != null && e == null) {
                                        //crates image and image view
                                        myProfileTab.bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap
                                        myProfileTab.imgViewShare.setImageBitmap(myProfileTab.bitmap1);

                                    }
                                }
                            });
                        }


                        if (userObject.get("image2") != null) {

                            ParseFile postPicture1 = (ParseFile) userObject.get("image2");
                            postPicture1.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (data != null && e == null) {
                                        //crates image and image view
                                        myProfileTab.bitmap2 = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap
                                        myProfileTab.imgViewShare1.setImageBitmap(myProfileTab.bitmap2);

                                    }
                                }
                            });


                        }
                        if (userObject.get("image3") != null) {

                            ParseFile postPicture2 = (ParseFile) userObject.get("image3");
                            postPicture2.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (data != null && e == null) {
                                        //crates image and image view
                                        myProfileTab.bitmap3 = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap
                                        myProfileTab.imgViewShare2.setImageBitmap(myProfileTab.bitmap3);

                                    }

                                }

                            });
                        }


                    } else {
                        ParseObject object = new ParseObject("Profile");
                        object.put("username", user);


                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");
                                    query.whereEqualTo("username", user);
                                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                                        public void done(ParseObject user, ParseException e) {
                                            if (e == null) {

                                                myProfileTab.currentUser = user;
                                                //queryDatabaseProfile();
                                            } else {
                                                // Something is wrong
                                            }
                                        }
                                    });
                                    Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "not done", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();

                            }
                        });


                    }
                }


                System.out.println(Arrays.toString(interests));
                myProfileTab.setUserTextField(name,age,county,userName,profileBio,profileProfession,profileHobbies,sports,interests,countiesArray);
                dialog.dismiss();

            }

        });




    }





}
