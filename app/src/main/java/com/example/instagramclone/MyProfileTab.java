package com.example.instagramclone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

public class MyProfileTab extends Fragment implements View.OnClickListener {

    private EditText edtProfileName,edtBio,edtProfession,edtHobbies,edtSport,edtAge,edtUsername,edtCounty;
    private Button btnUpdateInfo;

    String parseUser;
    private boolean imgViewShareClicked,imgViewShare1Clicked,imgViewShare2Clicked;
    ;
    private ImageView imgViewShare,imgViewShare1,imgViewShare2;

    ParseObject currentUser,userprofile;


    Bitmap receivedImageBitmap,bitmap1,bitmap2,bitmap3;
    ParseObject userClass;
    public MyProfileTab() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                    container, false);


            imgViewShare = view.findViewById(R.id.imgViewShare);
            imgViewShare1 = view.findViewById(R.id.imgViewShare1);
            imgViewShare2 = view.findViewById(R.id.imgViewShare2);


            imgViewShare.setOnClickListener(MyProfileTab.this);
            imgViewShare1.setOnClickListener(MyProfileTab.this);
            imgViewShare2.setOnClickListener(MyProfileTab.this);
            imgViewShare.setImageResource(R.drawable.baseline_add_a_photo_24);
            imgViewShare1.setImageResource(R.drawable.baseline_add_a_photo_24);
            imgViewShare2.setImageResource(R.drawable.baseline_add_a_photo_24);



            edtProfileName = view.findViewById(R.id.edtProfileName);
            edtAge = view.findViewById(R.id.edtAge);
            edtCounty = view.findViewById(R.id.edtCounty);
            edtUsername = view.findViewById(R.id.edtUsername);
            edtBio = view.findViewById(R.id.edtBio);
            edtHobbies = view.findViewById(R.id.edtHobbies);
            edtProfession = view.findViewById(R.id.edtProfession);
            edtSport = view.findViewById(R.id.edtSport);

            btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

            parseUser = ParseUser.getCurrentUser().getUsername();
            userClass = ParseUser.getCurrentUser();

            queryDatabaseProfile();


            btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Profile");
                    queryAll.whereEqualTo("username", parseUser);
                    queryAll.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {

                                if (objects.size() > 0) {

                                    for (ParseObject profile : objects) {


                                        profile.put("profileName", edtProfileName.getText().toString());
                                        profile.put("age",edtAge.getText().toString());
                                        profile.put("county",edtCounty.getText().toString());
                                        profile.put("profileBio", edtBio.getText().toString());
                                        profile.put("profileProfession", edtProfession.getText().toString());
                                        profile.put("profileHobbies", edtHobbies.getText().toString());
                                        profile.put("profileSport", edtSport.getText().toString());

                                        ProgressDialog progressDialog = new ProgressDialog(getContext());
                                        progressDialog.setMessage("Updating Info");
                                        progressDialog.show();

                                        profile.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    FancyToast.makeText(getContext(),
                                                            "Info Updated",
                                                            FancyToast.LENGTH_SHORT, FancyToast.INFO,
                                                            true).show();

                                                } else {
                                                    FancyToast.makeText(getContext(),
                                                            e.getMessage(),
                                                            FancyToast.LENGTH_LONG,
                                                            FancyToast.ERROR, true).show();


                                                }
                                                progressDialog.dismiss();

                                            }

                                        });

                                    }
                                }
                            }

                        }


                    });
                }
            });
            return view;//must return type view


          } catch (Exception e) {
        e.printStackTrace();
    }
       return null;
        }


        public void queryDatabaseProfile(){




            ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Profile");
            queryAll.whereEqualTo("username",parseUser);
            queryAll.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){


                        if(objects.size() > 0){

                            for(ParseObject profile : objects){



                                if(profile.get("profileName")  != null )
                                    edtProfileName.setText(profile.get("profileName") + "");
                                if(profile.get("age")  != null )
                                    edtAge.setText(profile.get("age") + "");
                                if(profile.get("county")  != null )
                                    edtCounty.setText(profile.get("county") + "");
                                if(profile.get("username")  != null )
                                    edtUsername.setText(profile.get("username") + "");
                                if(profile.get("profileBio")  != null )
                                    edtBio.setText(profile.get("profileBio") + "");
                                if(profile.get("profileProfession")  != null )
                                    edtProfession.setText(profile.get("profileProfession") + "");
                                if(profile.get("profileHobbies")  != null )
                                    edtHobbies.setText(profile.get("profileHobbies") + "");
                                if(profile.get("profileSport")  != null )
                                    edtSport.setText(profile.get("profileSport") + "");


                                if(profile.get("image1") != null) {
                                    ParseFile postPicture = (ParseFile) profile.get("image1");
                                    postPicture.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            if (data != null && e == null) {
                                                //crates image and image view
                                                bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap
                                                imgViewShare.setImageBitmap(bitmap1);

                                            }
                                        }
                                    });
                                }



                                if(profile.get("image2") != null) {

                                    ParseFile postPicture1 = (ParseFile) profile.get("image2");
                                    postPicture1.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            if (data != null && e == null) {
                                                //crates image and image view
                                                bitmap2 = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap
                                                imgViewShare1.setImageBitmap(bitmap2);

                                            }
                                        }
                                    });



                                }
                                if(profile.get("image3") != null) {

                                    ParseFile postPicture2 = (ParseFile) profile.get("image3");
                                    postPicture2.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            if (data != null && e == null) {
                                                //crates image and image view
                                                bitmap3 = BitmapFactory.decodeByteArray(data, 0, data.length);//convert  byte array from server to bitmap
                                                imgViewShare2.setImageBitmap(bitmap3);

                                            }

                                        }

                                    });
                                }


                            }
                        } else{
                            ParseObject object = new ParseObject("Profile");
                            object.put("username",parseUser);


                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Profile");
                                        query.whereEqualTo("username", parseUser);
                                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                                            public void done(ParseObject user, ParseException e) {
                                                if (e == null) {
                                                    currentUser = user;
                                                    queryDatabaseProfile();
                                                } else {
                                                    // Something is wrong
                                                }
                                            }
                                        });
                                        Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "not done", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    }
                }
            });



        }



    @Override
    public void onClick(View view) {//for uploading image

        switch(view.getId()){

            case R.id.imgViewShare:
                imgViewShareClicked = true;
            case R.id.imgViewShare1:
                imgViewShare1Clicked =true;

            case R.id.imgViewShare2:
                imgViewShare2Clicked = true;
                if(android.os.Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]
                                    {android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);



                }else{
                    getChoosenImage();
                }

                break;


        }
    }

    private void getChoosenImage() {
        Toast.makeText(getContext(),"Now we can access the images", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {

                getChoosenImage();//checks if permission granted then returns
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){


                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);


                    uploadImages();


                    if(imgViewShareClicked) {

                        imgViewShare.setImageBitmap(receivedImageBitmap);
                        imgViewShareClicked =false;
                    }
                    else if(imgViewShare1Clicked) {

                        imgViewShare1.setImageBitmap(receivedImageBitmap);
                        imgViewShare1Clicked =false;

                    }
                    else if(imgViewShare2Clicked) {
                        imgViewShare2.setImageBitmap(receivedImageBitmap);
                        imgViewShare2Clicked =false;

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }


    public void uploadImages(){


        if(receivedImageBitmap!= null){

            ParseFile parseFile = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//converts image to array of bytes
            receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayOutputStream);//how u want to compress it


            byte[] bytes = byteArrayOutputStream.toByteArray(); //array has image

            if(imgViewShareClicked) {
                parseFile = new ParseFile("image1.png", bytes);////the img in bytes create parse the file
                currentUser.put("image1", parseFile);//parse object and put column called image1

            }else if(imgViewShare1Clicked) {
                parseFile = new ParseFile("image2.png", bytes);////the img in bytes create parse the file
                currentUser.put("image2", parseFile);//parse object and put column called image1

            } else if (imgViewShare2Clicked) {
                parseFile = new ParseFile("image3.png", bytes);////the img in bytes create parse the file
                currentUser.put("image3", parseFile);//parse object and put column called image1



            }



            final ProgressDialog dialog = new ProgressDialog(getContext());//shows loading image
            dialog.setMessage("Loading");
            dialog.show();
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e ==null){
                        Toast.makeText(getContext(),"Done!!!",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getContext(),"Unknown Error: ", Toast.LENGTH_SHORT).show();

                    }
                    dialog.dismiss();
                }
            });

        }else{
            Toast.makeText(getContext(),"Unknown Error: you must select an Image", Toast.LENGTH_SHORT).show();

        }
        //INTENT USED TO GET IMAGES reult might be image or might me null


    }


}