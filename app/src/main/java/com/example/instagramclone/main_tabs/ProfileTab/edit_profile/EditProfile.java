package com.example.instagramclone.main_tabs.ProfileTab.edit_profile;

import com.example.instagramclone.braintree_payment.PaymentActivity;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.main_tabs.SocialMediaActivity;
import com.example.instagramclone.reusable_code.ParseUtils.UtilsClass;
import com.example.instagramclone.spotify.SpotifySongs;
import com.example.instagramclone.reusable_code.SizeBasedOnDensity;
import com.example.instagramclone.choices_tabs.Choices_tabs_Activity;
import com.example.instagramclone.reusable_code.ImageProcessor;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.Toast;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.Dialogs;
import com.example.instagramclone.reusable_code.ParseUtils.DataBaseUtils;
import com.example.instagramclone.reusable_code.ParseUtils.DeleteQuery;
import com.example.instagramclone.R;
import com.parse.GetCallback;
import com.parse.ParseException;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditProfile  extends AppCompatActivity implements View.OnClickListener {
    private ImageView v, xIcon;
    private static boolean hasimage1,hasimage2,hasimage3;
    private boolean isPayed = false;
    private boolean showAge = true;
    private boolean showLocation = true;
    private String image1, image2, image3;
    private SizeBasedOnDensity sizeBasedOnDensity;
    public ImageView imgVeditPro1, imgVeditPro2, imgVeditPro3;
    public EditText edt_jobTitle_R_fieldofStudy, edtBio, edt_Company_R_Colledge;
    private DataBaseUtils dataBaseUtils;
    private PiccassoLoadToImageView piccassoLoadToImageView;
   private ButtonCreator buttonCreator;
    private TableLayout where_I_LiveLayout, myBasicsLayout, sexualOrientationLayout, languagesLayout, myLifestyleLayout, choseninterestsLayout, myFavouriteSong;
    // Request code will be used to verify if result comes from the login activity. Can be set to any integer.

    private ImageView x_icon1, x_icon2, x_icon3;
    private String columnName_viewTag;
   private QueriesEditProfile queryForProfile;
    private ImageProcessor imageProcessor;
    private ImageButton backbtn;

    private TextView trackNametv;
    private ImageView trackImage;
    private TextView artistNametv;
    private TextView albumNametv;
    SwitchCompat switch_show_age, switch_show_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTitle(null);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTxt = toolbar.findViewById(R.id.toolbar_txt);
        toolbarTxt.setText(R.string.editProfileToolbarTxt);
        backbtn = toolbar.findViewById(R.id.btnBackToolbar);
        backbtn.setVisibility(View.VISIBLE);
        backbtn.setOnClickListener(this);

        sizeBasedOnDensity = new SizeBasedOnDensity(this);

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


        //switch btns
        switch_show_age = findViewById(R.id.show_age_switch);
        switch_show_age.setOnClickListener(this);
        switch_show_location = findViewById(R.id.show_location_switch);
        switch_show_location.setOnClickListener(this);

        //image processor create parse upload create file from image in galery
        imageProcessor = new ImageProcessor(this);

        //get all profile info from shared prefs
        queryForProfile = new QueriesEditProfile(this);
        dataBaseUtils = new DeleteQuery(this);

        //load images into image views
        piccassoLoadToImageView = new PiccassoLoadToImageView(this);
        buttonCreator = new ButtonCreator(this, new ArrayList<>());


        //edttext fields
        edtBio = findViewById(R.id.edtBio);
        edt_jobTitle_R_fieldofStudy = findViewById(R.id.job_title_R_fieldOfStudy);
        edt_Company_R_Colledge = findViewById(R.id.company_R_colledge);

        //tablelayouts
        where_I_LiveLayout = findViewById(R.id.where_i_live);
        languagesLayout = findViewById(R.id.languagesLayout);
        choseninterestsLayout = findViewById(R.id.choseninterestsLayout);
        sexualOrientationLayout = findViewById(R.id.sexualOrientation);
        myBasicsLayout = findViewById(R.id.my_basics);
        myLifestyleLayout = findViewById(R.id.my_lifestyle);
        myFavouriteSong = findViewById(R.id.my_favourite_song);

        myBasicsLayout.setOnClickListener(this);
        where_I_LiveLayout.setOnClickListener(this);
        sexualOrientationLayout.setOnClickListener(this);
        languagesLayout.setOnClickListener(this);
        choseninterestsLayout.setOnClickListener(this);
        myLifestyleLayout.setOnClickListener(this);
        myFavouriteSong.setOnClickListener(this);

        //spotify song views
        trackNametv = findViewById(R.id.trackName);
        trackImage = findViewById(R.id.trackImage);
        artistNametv = findViewById(R.id.artistName);
        albumNametv = findViewById(R.id.albumName);



        //loads buttons edt txt etc....
        loadViews();

        // wait  to check if images are in image views then rotate the plus/delete button accordingly
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hasimage1) {
                    x_icon1.setRotation(45f);
                }
                if (hasimage2) {
                    x_icon2.setRotation(45f);
                }
                if (hasimage3) {
                    x_icon3.setRotation(45f);
                }
            }

        }, 1000);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();//store clicked button id

        if (view instanceof TableLayout) {//check if clicked item is instance of tablelayout
            // The view is a TableLayout
            TableLayout clickedlayout = (TableLayout) view;//if tableaout clicked store id
            String tableClickedTag = (String) clickedlayout.getTag();//store clicked layout tag
            int tableClicked =0;
            switch (tableClickedTag){

                case "where_i_live":
                    tableClicked =1;
                    break;
                case "sexualOrientation":
                    tableClicked =2;
                    break;
                case "languages":
                    tableClicked =3;
                    break;
                case "my_basics":
                    tableClicked =4;
                    break;

                case "my_lifestyle":
                    tableClicked =5;
                    break;
            }

            //saves all inputs to realm database
            if(viewId == R.id.back_btn)
                saveToParse();

            //loads choices tabs if tablelayout press except favourite song
            if (viewId != myFavouriteSong.getId()) {

                Intent intent = new Intent(EditProfile.this, Choices_tabs_Activity.class);
                intent.putExtra("tableclicked", tableClicked);
                startActivityForResult(intent, 5000);

            }


            if (viewId == myFavouriteSong.getId()) {
                Intent intent = new Intent(EditProfile.this, SpotifySongs.class);
                startActivityForResult(intent, 5000);
            }

        }

        if(view instanceof SwitchCompat){

            if(viewId == R.id.show_age_switch) {
                if(!isPayed) {
                    switch_show_age.setChecked(false);
                    startActivity(new Intent(this, PaymentActivity.class));
                }else {
                    showLocation = !showLocation;
                }

            }
            if(viewId == R.id.show_location_switch) {
                if(!isPayed) {
                    switch_show_location.setChecked(false);
                    startActivity(new Intent(this, PaymentActivity.class));
                }else {
                    showAge = !showAge;
                }

            }

        }

        if (view instanceof ImageView) {
            ImageView clickedImageView = (ImageView) view;
            columnName_viewTag = (String) clickedImageView.getTag();

            if (viewId == backbtn.getId()) {
              onBackPressed();

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

                //delete image and the imagename which stores the local file path
                dataBaseUtils.deleteFileFromDatabase(columnName_viewTag);

                piccassoLoadToImageView.getImageNloadIntoImageview("null", v, sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 20);


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
        Toast.makeText(this, "Now we can access the images", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChoosenImage();//checks if permission granted then returns
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5000) {

            loadViews();
        }
        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Uri selectedImage = data.getData();
                //uri to input stream then sends to output stream  in creatfileoutputstream and that returns to image File
                //used this to create file of image so picasso would get orientation of image rite

                File imageFile = imageProcessor.createFileFromInputStream(selectedImage);

                switch (columnName_viewTag){
                    case "image1":
                        image1 = imageFile.getAbsolutePath();
                        break;
                    case "image2":
                        image2 = imageFile.getAbsolutePath();
                        break;
                    case "image3":
                        image3 = imageFile.getAbsolutePath();
                        break;
                }

                queryForProfile.uploadImages(imageFile, columnName_viewTag);

                if (columnName_viewTag.matches("image[1-3]")) {
                    piccassoLoadToImageView.getImageNloadIntoImageview(imageFile.getAbsolutePath(), v, sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 20);
                }

                if (xIcon != null && xIcon.getRotation() == 0f) {
                    xIcon.setRotation(45f);
                }

            }
        }

    }


    public void loadViews() {

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    // Set other fields here
                    List<String> mybasics = new ArrayList<>();

                    if(parseModel.getIsPayed()){
                        isPayed = true;
                    }
                    if(parseModel.getBoolShowAge()){
                        switch_show_age.setChecked(false);
                        showAge = parseModel.getBoolShowAge();
                    }
                    if(parseModel.getBoolShowLocation()){
                        switch_show_location.setChecked(false);
                        showLocation = parseModel.getBoolShowLocation();
                    }

                    if (parseModel.getRelationshipGoals()!= null && !parseModel.getRelationshipGoals().equals(""))
                        mybasics.add(parseModel.getRelationshipGoals());
                    if (parseModel.getReligion()!= null &&!parseModel.getReligion().equals(""))
                        mybasics.add(parseModel.getReligion());
                    if (parseModel.getStarSign()!= null &&!parseModel.getStarSign().equals(""))
                        mybasics.add(parseModel.getStarSign());
                    if (parseModel.getGender()!= null &&!parseModel.getGender().equals(""))
                        mybasics.add(parseModel.getGender());
                    if (parseModel.getHeight()!= null && !parseModel.getHeight().equals(""))
                        mybasics.add(parseModel.getHeight());

                    buttonCreator.buttonCreator(myBasicsLayout, EditProfile.this,  mybasics.toArray(new String[0]));

                    List<String> myLifeStyle = new ArrayList<>();
                    if (parseModel.getPets()!= null && !parseModel.getPets().equals(""))
                        myLifeStyle.add(parseModel.getPets());
                    if (parseModel.getSmoking()!= null && !parseModel.getSmoking().equals(""))
                        myLifeStyle.add(parseModel.getSmoking());
                    if (parseModel.getDrinking()!= null && !parseModel.getDrinking().equals(""))
                        myLifeStyle.add(parseModel.getDrinking());
                    if (parseModel.getDietary()!= null && !parseModel.getDietary().equals(""))
                        myLifeStyle.add(parseModel.getDietary());
                    if (parseModel.getSocialMedia()!= null && !parseModel.getSocialMedia().equals(""))
                        myLifeStyle.add(parseModel.getSocialMedia());
                    if (parseModel.getKids()!= null && !parseModel.getKids().equals(""))
                        myLifeStyle.add(parseModel.getKids());

                    albumNametv.setText(parseModel.getAlbumName());
                    artistNametv.setText(parseModel.getArtistName());
                    trackNametv.setText(parseModel.getSongName());

                    // Create an ImageView object for the song image and add it to the TableRow
                    if(parseModel.getTrackImage() != null)
                       piccassoLoadToImageView.getImageNloadIntoImageview(parseModel.getTrackImage(), trackImage, sizeBasedOnDensity.widthRatio(30), sizeBasedOnDensity.heightRatio(30), 10);

                    piccassoLoadToImageView.getImageNloadIntoImageview(parseModel.getImage1Name(), imgVeditPro1, sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 30);
                    piccassoLoadToImageView.getImageNloadIntoImageview(parseModel.getImage2Name(), imgVeditPro2, sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 30);
                    piccassoLoadToImageView.getImageNloadIntoImageview(parseModel.getImage3Name(), imgVeditPro3, sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 30);

                    if(parseModel.getImage1Data() != null)
                        hasimage1 = true;
                    if(parseModel.getImage2Data() != null)
                        hasimage2 = true;
                    if(parseModel.getImage3Data() != null)
                        hasimage3 = true;


                    if (parseModel.getBio() != null)
                        edtBio.setText(parseModel.getBio());

                    if (parseModel.getJobTitleFieldOfStudy() != null)
                        edt_jobTitle_R_fieldofStudy.setText(parseModel.getJobTitleFieldOfStudy());
                    if (parseModel.getCompanyRcolledge() != null)
                        edt_Company_R_Colledge.setText(parseModel.getCompanyRcolledge());

                    if (parseModel.getSexualOrientation() != null&& !parseModel.getSexualOrientation().equals("")) {
                        buttonCreator.buttonCreator(sexualOrientationLayout, null, parseModel.getSexualOrientation());
                    }
                    if (parseModel.getWhereILive() != null && !parseModel.getWhereILive().equals("")) {
                        buttonCreator.buttonCreator(where_I_LiveLayout, null,  parseModel.getWhereILive());
                    }
                    if (parseModel.getLanguages() != null) {
                        buttonCreator.buttonCreator(languagesLayout, null,  parseModel.getLanguages().toArray(new String[0]));
                    }
                    if (parseModel.getInterests() != null)
                        buttonCreator.buttonCreator(choseninterestsLayout, null,  parseModel.getInterests().toArray(new String[0]));
                    if (!myLifeStyle.isEmpty())
                        buttonCreator.buttonCreator(myLifestyleLayout, null,  myLifeStyle.toArray(new String[0]));

                }else{
                    Dialogs.showSnackbar(EditProfile.this,e.getMessage(),2000);
                }
            }
        });
    }


    public void saveToParse() {

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    parseModel.setBoolShowAge(showAge);
                    parseModel.setBoolShowLocation(showLocation);
                    if(image1 != null && !image1.equals(""))
                        parseModel.setImage1Name(image1);
                    if(image2 != null && !image2.equals(""))
                        parseModel.setImage2Name(image2);
                    if(image3 != null && !image3.equals(""))
                        parseModel.setImage3Name(image3);
                    parseModel.setBio(edtBio.getText().toString());
                    parseModel.setJobTitleFieldOfStudy(edt_jobTitle_R_fieldofStudy.getText().toString());
                    parseModel.setCompanyRcolledge(edt_Company_R_Colledge.getText().toString());

                    parseModel.saveInBackground();

                    // Save the object locally
                    parseModel.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Display a toast message to indicate that the object has been saved locally
                                Dialogs.showSnackbar(EditProfile.this, "Success!!!\n Selection's saved", 2000);


                            } else {
                                Dialogs.showSnackbar(EditProfile.this, e.getMessage(), 2000);

                                // Handle the error
                            }
                        }
                    });
                } else {
                    Dialogs.showSnackbar(EditProfile.this, e.getMessage(), 2000);
                }
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        saveToParse();
        //used so success msg can be shown
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //load socialmedia activity so pic loads and bring you to the profile tab
                // Create an Intent to launch the target activity
                Intent intent = new Intent(EditProfile.this, SocialMediaActivity.class);

// Pass any data you need to the target activity using extras
                intent.putExtra("data_key", 2);

// Start the target activity
                startActivity(intent);

                finish();
            }
        }, 2000);

    }
}