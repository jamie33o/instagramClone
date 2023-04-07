package com.example.instagramclone.edit_profile_N_profile;

import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.spotify.SpotifySongs;
import com.example.instagramclone.reusable_code.SizeBasedOnDensity;
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

import android.widget.TextView;
import android.widget.Toast;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.Snackbar_Dialog;
import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmList;

public class EditProfile  extends AppCompatActivity implements View.OnClickListener{
    ImageView v,xIcon;
    String image1,image2,image3;
    SizeBasedOnDensity sizeBasedOnDensity;
    public ImageView imgVeditPro1, imgVeditPro2, imgVeditPro3;
    public EditText edt_jobTitle_R_fieldofStudy, edtBio, edt_Company_R_Colledge;
    ImageButton backBtn;
    DataBaseUtils dataBaseUtils;
    PiccassoLoadToImageView piccassoLoadToImageView;
    ButtonCreator buttonCreator;
    private TableLayout where_I_LiveLayout, myBasicsLayout, sexualOrientationLayout, languagesLayout, myLifestyleLayout, choseninterestsLayout,myFavouriteSong;
    TableLayout clickedlayout;
    String tableClicked;
    // Request code will be used to verify if result comes from the login activity. Can be set to any integer.

    ImageView x_icon1, x_icon2, x_icon3;
    private ImageView clickedImageView;
    private String columnName_viewTag;
    QueriesEditProfile queryForProfile;
    ImageProcessor imageProcessor;
    Realm realm;

    RealmModel results;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context=this;

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

        //image processor create parse upload create file from image in galery
        imageProcessor = new ImageProcessor(this);

        //get all profile info from shared prefs
        queryForProfile = new QueriesEditProfile(this);
        dataBaseUtils = new ReusableQueries(this);

        //load images into image views
        piccassoLoadToImageView = new PiccassoLoadToImageView(this);
        buttonCreator = new ButtonCreator(this,new ArrayList<>());
        realm = RealmManager.getRealmInstance();
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();
        //back button in action bar
        backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(this);

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


        //loads buttons edt txt etc....
        loadViews();

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
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();//store clicked button id

        if (view instanceof TableLayout) {//check if clicked item is instance of tablelayout
            // The view is a TableLayout
            clickedlayout = (TableLayout) view;//if tableaout clicked store id
            tableClicked = (String) clickedlayout.getTag();//store clicked layout tag

            //saves all inputs to realm database
            uploadToRealm();

            //loads choices tabs if tablelayout press except favourite song
            if(viewId!=myFavouriteSong.getId()) {

                Intent intent = new Intent(context, Choices_tabs_MainPage.class);
                intent.putExtra("com.example.instagram.layoutthatwasclicked", tableClicked);
                startActivityForResult(intent, 5000);

            }


            if(viewId==myFavouriteSong.getId()){


                Intent intent = new Intent(context, SpotifySongs.class);
                //intent.putExtra("com.example.instagram.layoutthatwasclicked", tableClicked);
                startActivityForResult(intent, 7000);



            }

        }

        if (view instanceof ImageView) {
            clickedImageView = (ImageView) view;
            columnName_viewTag = (String) clickedImageView.getTag();
            if (viewId == R.id.backbtn) {
                if(results!=null) {
                    uploadToRealm();
                    Snackbar_Dialog.showSnackbar(this, "Success!!!\n Selection's saved", 2000);
                }else{
                    Snackbar_Dialog.showSnackbar(this, "Error!!!\n Selection's not saved", 2000);

                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },2000);
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

                realm.beginTransaction();

                dataBaseUtils.deleteFileFromDatabase(columnName_viewTag);

                switch (columnName_viewTag){
                    case "image1":
                        results.setImage1Name("");
                        break;
                    case "image2":
                        results.setImage2Name("");
                        break;
                    case "image3":
                        results.setImage3Name("");
                        break;
                }
                realm.commitTransaction();

                piccassoLoadToImageView.getImageNloadIntoImageview(v, "null",columnName_viewTag, sizeBasedOnDensity.widthRatio(100),sizeBasedOnDensity.heightRatio(160), 20);


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

        if(requestCode == 5000) {

            loadViews();
        }
        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                Uri selectedImage = data.getData();
                //uri to input stream then sends to output stream  in creatfileoutputstream and that returns to image File
                //used this to create file of image so picasso would get orientation of image rite

                File imageFile = imageProcessor.createFileFromInputStream(selectedImage);

                switch (columnName_viewTag){
                    case "image1":
                        image1 = imageFile.getAbsolutePath();
                        Snackbar_Dialog.showSnackbar(this, "Success!!!\n Image Saved", 2000);
                        break;
                    case "image2":
                        image2 = imageFile.getAbsolutePath();
                        Snackbar_Dialog.showSnackbar(this, "Success!!!\n Image Saved", 2000);
                        break;
                    case"image3":
                        image3 = imageFile.getAbsolutePath();
                        Snackbar_Dialog.showSnackbar(this, "Success!!!\n Image Saved", 2000);
                        break;
                }

                if(columnName_viewTag.matches("image[1-3]")) {
                    piccassoLoadToImageView.getImageNloadIntoImageview(v,imageFile.getAbsolutePath(),columnName_viewTag,sizeBasedOnDensity.widthRatio(100),sizeBasedOnDensity.heightRatio(160),20);
                }

                if (xIcon != null && xIcon.getRotation() == 0f) {
                    xIcon.setRotation(45f);
                }

            }
        }

    }
        public void setSong(String imageUrl,String albumName,String artistName,String trackName) {


        TextView albumNametv = findViewById(R.id.albumName);
        albumNametv.setText(albumName);
            TextView artistNametv = findViewById(R.id.artistName);
            artistNametv.setText(artistName);
            TextView trackNametv = findViewById(R.id.trackName);
            trackNametv.setText(trackName);
            ImageView trackImage = findViewById(R.id.trackImage);
// Create an ImageView object for the song image and add it to the TableRow
            Picasso.get()
                    .load(imageUrl)
                    .resize(150,150)
                    .centerCrop()
                    .into(trackImage);




        }


    public void loadViews() {


        if(results!=null) {

            realm.beginTransaction();

            RealmList<String> realmList = results.getPronounes();

            List<String> mybasics = new ArrayList<>(realmList);

            if(results.getRelationShipGoals()!=null)
                mybasics.add(results.getRelationShipGoals());
            if(results.getHometown()!=null)
                mybasics.add(results.getHometown());
            if(results.getReligion()!=null)
                mybasics.add(results.getReligion());
            if(results.getStarSign()!=null)
                mybasics.add(results.getStarSign());
            if(results.getGender()!=null)
                mybasics.add(results.getGender());
            if(results.getHeight()!=null)
                mybasics.add(results.getHeight());

            buttonCreator.buttonCreator(myBasicsLayout, this, "", mybasics.toArray(new String[0]));

            RealmList<String> realmList1 = results.getWorkout();

            List<String> myLifeStyle = new ArrayList<>(realmList1);
            if(results.getPets()!=null)
                myLifeStyle.add(results.getPets());
            if(results.getSmoking()!=null)
                myLifeStyle.add(results.getSmoking());
            if(results.getDrinking()!=null)
                myLifeStyle.add(results.getDrinking());
            if(results.getDietary()!=null)
                myLifeStyle.add(results.getDietary());
            if(results.getSocialMedia()!=null)
                myLifeStyle.add(results.getSocialMedia());
            if(results.getKids()!=null)
                myLifeStyle.add(results.getKids());


            piccassoLoadToImageView.getImageNloadIntoImageview(imgVeditPro1,results.getImage1Name(),"image1", sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 30);
            piccassoLoadToImageView.getImageNloadIntoImageview(imgVeditPro2, results.getImage2Name(),"image2", sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 30);
            piccassoLoadToImageView.getImageNloadIntoImageview(imgVeditPro3, results.getImage3Name(),"image3", sizeBasedOnDensity.widthRatio(100), sizeBasedOnDensity.heightRatio(160), 30);


            if (results.getBio() != null)
                edtBio.setText(results.getBio());

            if (results.getJobTitle_fieldOfStudy() != null)
                edt_jobTitle_R_fieldofStudy.setText(results.getJobTitle_fieldOfStudy());
            if (results.getCompanyRcolledge() != null)
                edt_Company_R_Colledge.setText(results.getCompanyRcolledge());

            if (results.getSexualOrientaion() != null) {
                buttonCreator.buttonCreator(this.sexualOrientationLayout, null, "", results.getSexualOrientaion());
            }
            if (results.getWhereIlive() != null) {
                buttonCreator.buttonCreator(where_I_LiveLayout, null, "", results.getWhereIlive());
            }
            if (results.getLanguages() != null) {
                buttonCreator.buttonCreator(languagesLayout, null, "", results.getLanguages().toArray(new String[0]));
            }
            if (results.getInterests() != null)
                buttonCreator.buttonCreator(choseninterestsLayout, null, "", results.getInterests().toArray(new String[0]));
            if (!myLifeStyle.isEmpty())
                buttonCreator.buttonCreator(myLifestyleLayout, null, "", myLifeStyle.toArray(new String[0]));

            realm.commitTransaction();
        }
    }



    public void uploadToRealm() {

        realm.beginTransaction();

        if (image1 != null && !image1.equals(""))
            results.setImage1Name(image1);
        if (image2 != null && !image2.equals(""))
            results.setImage2Name(image2);
        if (image3 != null && !image3.equals(""))
            results.setImage3Name(image3);
        results.setBio(edtBio.getText().toString());
        results.setJobTitle_fieldOfStudy(edt_jobTitle_R_fieldofStudy.getText().toString());
        results.setCompanyRcolledge(edt_Company_R_Colledge.getText().toString());

        realm.commitTransaction();

        queryForProfile.data_From_Realm_To_Database();


    }



}