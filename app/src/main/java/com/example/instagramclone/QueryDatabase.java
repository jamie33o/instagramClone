package com.example.instagramclone;

import static com.parse.Parse.getApplicationContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.WindowManager;
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
import java.util.Collections;
import java.util.List;

public class QueryDatabase {
    //EditProfileAdapter queryDataBaseAdapter;
    Bitmap bitmap;
    String name;
    String age;
    String county;
    String userName;
    String profileBio;
    String profileProfession;
    String profileHobbies;
    String sports;
    String image1,image2,image3;
    int x = 0;

    String[] chosenCounties, chosenInterests,results,jsonArrayOutput;
    ProfileModel profileModel;

    String gender;
    String user;

    Context context;
    ProgressDialog dialog;


    int counter = 0;

    UsersTab usersTab;
    public QueryDatabase(Context context) {
        this.context = context;
        user = ParseUser.getCurrentUser().getUsername();
       // interests = new String[0];

        profileModel = new ProfileModel();

        results = null;
        chosenCounties = null;
        chosenInterests = null;
        jsonArrayOutput = null;





    }


    public void showProgressDialog(Context context) {
         dialog = new ProgressDialog(context, R.style.TransparentProgressDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    public void dismissProgressDialog(ProgressDialog dialog) {
        try {
            if (dialog != null && dialog.isShowing() && dialog.getWindow() != null) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception
        }
    }




    //uploads the user info one at a time

    public void putQueryDatabase(String columnKey, Object columnValue,Context context) {



        if (columnValue != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");

// Set the query to filter based on the "username" column

            //showProgressDialog(context);

            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        if (columnValue instanceof String) {
                            //   System.out.println(columnValue);
                            object.put(columnKey, columnValue);

                        } else if(columnValue instanceof String[]) {
                            // System.out.println(columnValue);
                            object.put(columnKey, new ArrayList<>(Collections.singleton(columnValue)));
                        } else if (columnValue instanceof ParseFile) {
                            object.put(columnKey, columnValue);

                        }
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                    FancyToast.makeText(context, "Success", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                } else {
                                    FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                             //   dismissProgressDialog(dialog);
                            }
                        });
                    } else {
                        FancyToast.makeText(context, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }

                }
            });
        }
    }





    public void getQueryDatabaseCardview(UsersTab usersTab) {
        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");

       /* for (String interest : interests) {
            parseQuery.whereContains("choseninterests", interest);
        }*/

      // parseQuery.whereContains("chosencounties",county);
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

/*


    public void getCurrentUserImagesQueryDatabase(Object obj,String... imageColumns) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");

        query.whereEqualTo("username", user);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                for(String imageColumn: imageColumns){

                if (object.get(imageColumn) != null) {
                    ParseFile postPicture = (ParseFile) object.get(imageColumn);
                    postPicture.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (data != null && e == null) {

                                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap


                                if(imageColumn.equals("image1")){


                                }


                            }
                        }

                    });
                }

                }


            }
        });
*/


    public void queryDatabaseProfile(EditProfile editProfile){

        ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Profile");
        queryAll.whereEqualTo("username",user);

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

                        String[] choicesGet = new String[] {"choseninterest","chosencounties"};
                        String[][] results = new String[choicesGet.length][]; // Initialize a 2D array to store the results

                                for (String choice : choicesGet) {


                                    if (userObject.getList(choice) != null) {
                                        JSONArray jsonArray = new JSONArray(userObject.getList(choice));
                                        JSONArray innerArray = null;
                                        try {
                                            innerArray = jsonArray.getJSONArray(0);
                                        } catch (JSONException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        jsonArrayOutput = new String[innerArray.length()];
                                        for (int i = 0; i < innerArray.length(); i++) {
                                            try {
                                                jsonArrayOutput[i] = innerArray.getString(i);
                                            } catch (JSONException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }
                                        results[x] = jsonArrayOutput;
                                        x++;
                                    }

                                }

// Access the results for each choice using the corresponding index
                         chosenInterests = results[0];
                         chosenCounties = results[1];




                        if (userObject.get("image1") != null) {
                            ParseFile postPicture = (ParseFile) userObject.get("image1");
                            postPicture.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (data != null && e == null) {
                                       String filePath;
                                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        File file = new File(getApplicationContext().getCacheDir(), counter + ".png");
                                        filePath = file.getAbsolutePath();
                                        FileOutputStream fos = null;


                                        try {
                                            fos = new FileOutputStream(filePath);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                        } catch (FileNotFoundException a) {
                                            a.printStackTrace();
                                        } finally {
                                            try {
                                                fos.close();
                                            } catch (IOException a) {
                                                a.printStackTrace();
                                            }

                                        }

                                        image1 = filePath;
                                        counter++;


                                    }
                                }
                            });
                        }else{
                            counter++;
                            image1 =null;
                        }

                        if (userObject.get("image2") != null) {
                            ParseFile postPicture = (ParseFile) userObject.get("image2");
                            postPicture.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (data != null && e == null) {
                                      String filePath;
                                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        File file = new File(getApplicationContext().getCacheDir(), counter + ".png");
                                        filePath = file.getAbsolutePath();
                                        FileOutputStream fos = null;


                                        try {
                                            fos = new FileOutputStream(filePath);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                        } catch (FileNotFoundException a) {
                                            a.printStackTrace();
                                        } finally {
                                            try {
                                                fos.close();
                                            } catch (IOException a) {
                                                a.printStackTrace();
                                            }

                                        }

                                        image2 = filePath;
                                        counter++;
                                        if(counter == 3){
                                            editProfile.addList(name,age,county,userName,profileBio,profileProfession,profileHobbies,chosenInterests,chosenCounties,image1,image2,image3);

                                        }
                                    }
                                }
                            });
                        }else{
                            counter++;
                            image2 = null;
                        }

                        if (userObject.get("image3") != null) {
                            ParseFile postPicture = (ParseFile) userObject.get("image3");
                            postPicture.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (data != null && e == null) {
                                        String filePath;
                                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        File file = new File(getApplicationContext().getCacheDir(), counter +".png");
                                        filePath = file.getAbsolutePath();
                                        FileOutputStream fos = null;


                                        try {
                                            fos = new FileOutputStream(filePath);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                        } catch (FileNotFoundException a) {
                                            a.printStackTrace();
                                        } finally {
                                            try {
                                                fos.close();
                                            } catch (IOException a) {
                                                a.printStackTrace();
                                            }

                                            image3 = filePath;
                                        }
                                    counter++;
                                        if(counter == 3){
                                            editProfile.addList(name,age,county,userName,profileBio,profileProfession,profileHobbies,chosenInterests,chosenCounties,image1,image2,image3);

                                        }
                                    }
                                }
                            });
                        }else {
                            counter++;
                            image3=null;
                        }


                    } else {
                        ParseObject object = new ParseObject("Profile");
                        object.put("username", user);


                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                    queryDatabaseProfile(editProfile);
                                    Toast.makeText(context, "profile created", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context, "Error creating profile", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                }


            }

        });




    }





}
