package com.example.instagramclone.edit_profile_N_profile;

import static android.app.PendingIntent.getActivity;

import com.example.instagramclone.choices_tabs.Choices_tabs_MainPage;
import com.example.instagramclone.reusable_code.ImageProcessor;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.R;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;

import java.io.File;
import java.util.ArrayList;

public class EditProfile  extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener,DataBaseUtils {
    ImageView v,xIcon;
    SharedPreferencesManager sharedPreferencesManager;
    public ImageView imgVeditPro1, imgVeditPro2, imgVeditPro3;
    public EditText edt_jobTitle_R_fieldofStudy, edtBio, edt_Company_R_Colledge;
    ImageButton backBtn;
    DataBaseUtils dataBaseUtils;
    PiccassoLoadToImageView piccassoLoadToImageView;
    ButtonCreator buttonCreator;
    private TableLayout where_I_LiveLayout, myBasicsLayout, sexualOrientationLayout, languagesLayout, myLifestyleLayout, choseninterestsLayout;

    TableLayout clickedlayout;
    String tableClicked;
    ImageView x_icon1, x_icon2, x_icon3;
    private ImageView clickedImageView;
    private String columnName_viewTag;
    QueriesEditProfile queryForProfile;

    ImageProcessor imageProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sharedPreferencesManager = new SharedPreferencesManagerImpl(this,"Profile",Context.MODE_PRIVATE);

        //imageviews
        imgVeditPro1 = findViewById(R.id.imgV_edit_profile1);
        imgVeditPro2 = findViewById(R.id.imgV_edit_profile2);
        imgVeditPro3 = findViewById(R.id.imgV_edit_profile3);
        imgVeditPro1.setOnClickListener(this);
        imgVeditPro2.setOnClickListener(this);
        imgVeditPro3.setOnClickListener(this);

        //x and plus buttons
        x_icon1 = findViewById(R.id.plus_x_1);
        x_icon2 = findViewById(R.id.plus_x_2);
        x_icon3 = findViewById(R.id.plus_x_3);
        x_icon1.setOnClickListener(this);
        x_icon2.setOnClickListener(this);
        x_icon3.setOnClickListener(this);

        //image processor create parse upload create file from image in galery
        imageProcessor = new ImageProcessor(this);

        //get all profile info from shared prefs
        queryForProfile = new QueriesEditProfile(this);
        queryForProfile.getUserProfileFromSharedPrefs(this);

        dataBaseUtils = new ReusableQueries(this);

        //load images into image views
        piccassoLoadToImageView = new PiccassoLoadToImageView(this);
        piccassoLoadToImageView.getImageNloadIntoImageview(imgVeditPro1,"image1",280,450,30);
        piccassoLoadToImageView.getImageNloadIntoImageview(imgVeditPro2,"image2",280,450,30);
        piccassoLoadToImageView.getImageNloadIntoImageview(imgVeditPro3,"image3",280,450,30);
        buttonCreator = new ButtonCreator(this,new ArrayList<>());




        // wait  to check if images are in image views then rotate the plus/delete button accordingly
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PiccassoLoadToImageView.hasimage1) {
                    x_icon1.setRotation(45f);
                }
                if (PiccassoLoadToImageView.hasimage2) {
                    x_icon2.setRotation(45f);
                }
                if (PiccassoLoadToImageView.hasimage3) {
                    x_icon3.setRotation(45f);
                }
            }

        }, 1000);


        //back button in action bar
        backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(this);

        //edttext fields
        edtBio = findViewById(R.id.edtBio);
        edt_jobTitle_R_fieldofStudy = findViewById(R.id.job_title_R_fieldOfStudy);
        edt_Company_R_Colledge = findViewById(R.id.company_R_colledge);

        edt_Company_R_Colledge.setOnFocusChangeListener(this);
        edtBio.setOnFocusChangeListener(this);
        edt_jobTitle_R_fieldofStudy.setOnFocusChangeListener(this);

        //tablelayouts
        where_I_LiveLayout = findViewById(R.id.where_i_live);
        languagesLayout = findViewById(R.id.languagesLayout);
        choseninterestsLayout = findViewById(R.id.choseninterestsLayout);
        sexualOrientationLayout = findViewById(R.id.sexualOrientation);
        myBasicsLayout = findViewById(R.id.my_basics);

        myBasicsLayout.setOnClickListener(this);
        where_I_LiveLayout.setOnClickListener(this);
        sexualOrientationLayout.setOnClickListener(this);
        languagesLayout.setOnClickListener(this);
        choseninterestsLayout.setOnClickListener(this);


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            EditText editText = (EditText) v;//stote edittext id
            String text = editText.getText().toString();//store editext text
            String fieldName = editText.getTag().toString();//store edittext tag

                // Upload text to database for the field that lost focus
                dataBaseUtils.uploadToDataBase(fieldName, text, this);

        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();//store clicked button id


        if (view instanceof TableLayout) {//check if clicked item is instance of tablelayout
            // The view is a TableLayout
            clickedlayout = (TableLayout) view;//if tableaout clicked store id
            tableClicked = (String) clickedlayout.getTag();//store clicked layout tag


                Intent intent = new Intent(this, Choices_tabs_MainPage.class);
                intent.putExtra("com.example.instagram.layoutthatwasclicked", tableClicked);
                startActivityForResult(intent,5000);


        }

        if (view instanceof ImageView) {
            clickedImageView = (ImageView) view;
            columnName_viewTag = (String) clickedImageView.getTag();
            if (viewId == R.id.backbtn) {
                finish();
                return;
            }

                switch (columnName_viewTag) {
                    case "image1":
                        xIcon = x_icon1;
                        v = imgVeditPro1;
                        break;
                    case "image2":
                        xIcon = x_icon2;
                        v = imgVeditPro2;
                        break;
                    case "image3":
                        xIcon = x_icon3;
                        v = imgVeditPro3;
                        break;
            }

            if (clickedImageView.getRotation() > 5 && columnName_viewTag.matches("image[1-3]")) {

                //if plus_x button is rotated then put back to 0 and delete image
                clickedImageView.setRotation(0f);
                dataBaseUtils.deleteFileFromDatabase(columnName_viewTag);

                piccassoLoadToImageView.getImageNloadIntoImageview(v, "", 280, 450, 20);


            } else if (columnName_viewTag.matches("image[1-3]")) {
                String readImagePermission = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        ? Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE;
                if (ContextCompat.checkSelfPermission(this, readImagePermission) == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{readImagePermission}, 1000);
                } else {
                    getChoosenImage();
                }
            }
        }
    }

    private void getChoosenImage() {
        Toast.makeText(this,"Now we can access the images", Toast.LENGTH_SHORT).show();
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
        if(requestCode == 5000){

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    queryForProfile.getUserProfileFromSharedPrefs(EditProfile.this);
                }
            },100);
        }
        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                Uri selectedImage = data.getData();
                //uri to input stream then sends to output stream  in creatfileoutputstream and that returns to image File
                //used this to create file of image so picasso would get orientation of image rite

                File imageFile = imageProcessor.createFileFromInputStream(selectedImage);

                sharedPreferencesManager.saveString(columnName_viewTag,imageFile.getAbsolutePath());

                if(columnName_viewTag.matches("image[1-3]")) {
                    piccassoLoadToImageView.getImageNloadIntoImageview(v,imageFile.getAbsolutePath(),280,450,20);
                }

                imageProcessor.createParseImageUpload(selectedImage, columnName_viewTag);


                if (xIcon != null && xIcon.getRotation() == 0f) {
                    xIcon.setRotation(45f);
                }

            }
        }
    }


    public void loadViews( String[] mybasics, String profileBio, String[] sexualOrientation, String gender, String profileProfession, String[] languages, String[] chosenInterests, String[] whereilive) {




        if(profileBio != null && !profileBio.isEmpty())
            edtBio.setText(profileBio);
        if(profileProfession != null && !profileProfession.isEmpty())
            edt_Company_R_Colledge.setText(profileProfession);

        if (mybasics != null && mybasics.length > 0) {
            buttonCreator.buttonCreator(myBasicsLayout, this, "mybasics", mybasics);
        }
       if(sexualOrientation != null && sexualOrientation.length > 0) {

            buttonCreator.buttonCreator(this.sexualOrientationLayout, this,"sexualOrientation", sexualOrientation);
        }
        if(whereilive != null && whereilive.length>0) {
            buttonCreator.buttonCreator(where_I_LiveLayout, this,"whereilive", whereilive);
        }
        if (languages != null && languages.length > 0) {
            buttonCreator.buttonCreator(languagesLayout, this,"languages", languages);
        }
         if (chosenInterests != null && chosenInterests.length > 0)
            buttonCreator.buttonCreator(choseninterestsLayout,this,"interests", chosenInterests);

    }


    @Override
    public void deleteFileFromDatabase(String columnName) {

    }

    @Override
    public void uploadToDataBase(String columnKey, Object columnValue, Context context) {

    }
}