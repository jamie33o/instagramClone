package com.example.instagramclone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TableLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class MyProfileTab extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    public EditText edtName,edtBio,edtProfession,edtAge,edtUsername;
    private TableLayout countyLayout,profileHobbiesLayout, sportsLayout,chosencountiesLayout,choseninterestsLayout;
    public Button btnUpdateInfo;

    ButtonCreator buttonCreator;
    String parseUser;
    public boolean imgViewShareClicked,imgViewShare1Clicked,imgViewShare2Clicked;
    QueryDatabase queryDatabase;
    public ImageView imgViewShare,imgViewShare1,imgViewShare2;

    ParseObject currentUser,userprofile;
    Button notSelectedButton, selectedButton;

    Bitmap receivedImageBitmap,bitmap1,bitmap2,bitmap3;
    ParseObject userClass;


    public MyProfileTab() {



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                container, false);



        buttonCreator = new ButtonCreator(getContext(),this);


        queryDatabase = new QueryDatabase(getContext());
        queryDatabase.queryDatabaseProfile(this);

        imgViewShare = view.findViewById(R.id.imgViewShare);
        imgViewShare1 = view.findViewById(R.id.imgViewShare1);
        imgViewShare2 = view.findViewById(R.id.imgViewShare2);


        imgViewShare.setOnClickListener(MyProfileTab.this);
        imgViewShare1.setOnClickListener(MyProfileTab.this);
        imgViewShare2.setOnClickListener(MyProfileTab.this);
        imgViewShare.setImageResource(R.drawable.baseline_add_a_photo_24);
        imgViewShare1.setImageResource(R.drawable.baseline_add_a_photo_24);
        imgViewShare2.setImageResource(R.drawable.baseline_add_a_photo_24);


        edtName = view.findViewById(R.id.edtName);
        edtName.setOnFocusChangeListener(this);

        edtAge = view.findViewById(R.id.edtAge);
        edtAge.setOnFocusChangeListener(this);

        countyLayout = view.findViewById(R.id.countyLayout);


        edtUsername = view.findViewById(R.id.edtUsername);
        edtUsername.setOnFocusChangeListener(this);

        edtBio = view.findViewById(R.id.edtBio);
        edtBio.setOnFocusChangeListener(this);

        profileHobbiesLayout = view.findViewById(R.id.profileHobbiesLayout);

        edtProfession = view.findViewById(R.id.edtProfession);
        edtProfession.setOnFocusChangeListener(this);

        sportsLayout = view.findViewById(R.id.sportsLayout);
        chosencountiesLayout = view.findViewById(R.id.chosencountiesLayout) ;
        choseninterestsLayout = view.findViewById(R.id.choseninterestsLayout);

        parseUser = ParseUser.getCurrentUser().getUsername();
        userClass = ParseUser.getCurrentUser();

        //queryDatabase.getUserInfoQueryDatabase();



        return view;//must return type view



    }

    public void setUserTextField(String name, String age, String county, String userName, String profileBio, String profileProfession, String profileHobbies, String sports, String[] choseninterests, String[] chosencounties){

        edtName.setText(name);
        edtAge.setText(age);
        buttonCreator.buttonCreator(countyLayout, county);
        edtUsername.setText(userName);
        edtBio.setText(profileBio);
        edtProfession.setText(profileProfession);
        if(profileHobbies != null)
            buttonCreator.buttonCreator(profileHobbiesLayout, profileHobbies);
        if(sports != null)
            buttonCreator.buttonCreator(sportsLayout, sports);
        if(chosencounties != null)
            buttonCreator.buttonCreator(chosencountiesLayout, chosencounties);
        if(choseninterests != null)
            buttonCreator.buttonCreator(choseninterestsLayout, choseninterests);



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


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                String fieldName = editText.getTag().toString();

                // Upload text to database for the field that lost focus
                queryDatabase.putQueryDatabase(fieldName, text);
                if(fieldName.equals("username")) {
                    userClass.put("username", text);
                }
        }
    }
}