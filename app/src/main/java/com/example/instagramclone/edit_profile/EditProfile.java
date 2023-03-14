package com.example.instagramclone.edit_profile;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.instagramclone.ButtonCreator;
import com.example.instagramclone.QueryDatabase;
import com.example.instagramclone.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditProfile  extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String IMAGE_EXTENSION = ".png";
   String parseUser;
    public ImageView imgViewShare, imgViewShare1, imgViewShare2;

    public EditText edtName, edtBio, edtProfession, edtAge, edtUsername;
    ImageButton backBtn;


    ButtonCreator buttonCreator;

    private TableLayout countyLayout, profileHobbiesLayout, sportsLayout, chosencountiesLayout, choseninterestsLayout;


    private ImageView clickedImageView;
    private String columnName;
    QueryDatabase queryDatabase;
    String[] columnNames;
    ParseObject userClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        queryDatabase = new QueryDatabase(this);
        queryDatabase.queryDatabaseProfile(this);
        buttonCreator = new ButtonCreator(this,this);


        imgViewShare = findViewById(R.id.imgViewShare);
        imgViewShare1 = findViewById(R.id.imgViewShare1);
        imgViewShare2 = findViewById(R.id.imgViewShare2);


        queryDatabase.getCurrentUserImagesQueryDatabase(this);



        backBtn = findViewById(R.id.backbtn);

        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        countyLayout = findViewById(R.id.countyLayout);
        edtUsername = findViewById(R.id.edtUsername);
        edtBio = findViewById(R.id.edtBio);
        profileHobbiesLayout = findViewById(R.id.profileHobbiesLayout);
        edtProfession = findViewById(R.id.edtProfession);
        sportsLayout = findViewById(R.id.sportsLayout);
        chosencountiesLayout = findViewById(R.id.chosencountiesLayout);
        choseninterestsLayout = findViewById(R.id.choseninterestsLayout);

            backBtn.setOnClickListener(this);
            imgViewShare.setOnClickListener(this);
            imgViewShare1.setOnClickListener(this);
            imgViewShare2.setOnClickListener(this);
            imgViewShare.setOnClickListener(this);
            chosencountiesLayout.setOnFocusChangeListener(this);
            choseninterestsLayout.setOnClickListener(this);
            edtProfession.setOnFocusChangeListener(this);
            edtBio.setOnFocusChangeListener(this);
            edtUsername.setOnFocusChangeListener(this);
            edtAge.setOnFocusChangeListener(this);
            edtName.setOnFocusChangeListener(this);





        parseUser = ParseUser.getCurrentUser().getUsername();
        userClass = ParseUser.getCurrentUser();



        columnNames = new String[]{"image1", "image2", "image"};


    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            EditText editText = (EditText) v;
            String text = editText.getText().toString();
            String fieldName = editText.getTag().toString();
            System.out.println(editText + text + fieldName + this);

            // Upload text to database for the field that lost focus
            queryDatabase.putQueryDatabase(fieldName, text, this);
            if (fieldName.equals("username")) {
                userClass.put("username", text);
            }


        }
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();


        if (viewId == R.id.backbtn) {
            finish();
        }

        if (viewId == R.id.imgViewShare || viewId == R.id.imgViewShare1 || viewId == R.id.imgViewShare2) {


            clickedImageView = (ImageView) view;
            columnName = (String) clickedImageView.getTag();

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_MEDIA_IMAGES) !=
                            PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]
                                {Manifest.permission.READ_MEDIA_IMAGES},
                        1000);

            }else if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {

               ActivityCompat.requestPermissions(this,new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);


            } else {
                getChoosenImage();
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

        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                Uri selectedImage = data.getData();

                try {
                    if(columnName == imgViewShare.getTag()) {

                        Picasso.get()
                                .load(selectedImage)
                                .placeholder(R.drawable.mainplaceholder)
                                .transform(new RoundedCornersTransformation(20, 0))
                                .resize(250, 400)
                                .into(imgViewShare);

                    }

                    if(columnName == imgViewShare1.getTag()) {

                        Picasso.get()
                                .load(selectedImage)
                                .placeholder(R.drawable.mainplaceholder)
                                .transform(new RoundedCornersTransformation(20, 0))
                                .resize(250, 400)
                                .into(imgViewShare1);
                    }
                    if(columnName == imgViewShare2.getTag()) {
                        Picasso.get()
                                .load("selectedImage")
                                .placeholder(R.drawable.mainplaceholder)
                                .transform(new RoundedCornersTransformation(20, 0))
                                .resize(250, 400)
                                .into(imgViewShare2);
                    }
                    createParseImageUpload(selectedImage,columnName);


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }







    public void createParseImageUpload(Uri imageUri, String columnName) {
        try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();
            ParseFile parseFile = new ParseFile(columnName + IMAGE_EXTENSION, bytes);
            queryDatabase.putQueryDatabase(columnName, parseFile, this);
        } catch (IOException e) {
            // Handle the exception
        }
    }


    public void addList(String name, String age, String county, String userName, String profileBio, String profileProfession, String profileHobbies, String[] chosenInterests, String[] chosenCounties) {
        edtName.setText(name);
        edtAge.setText(age);
        edtUsername.setText(userName);
        edtBio.setText(profileBio);
        edtProfession.setText(profileProfession);

        if(county != null )
            buttonCreator.buttonCreator(countyLayout, county);
        if (profileHobbies != null)
            buttonCreator.buttonCreator(profileHobbiesLayout, profileHobbies);

        if (chosenCounties != null)
            buttonCreator.buttonCreator(chosencountiesLayout, chosenCounties);
        if (chosenInterests!= null)
            buttonCreator.buttonCreator(choseninterestsLayout, chosenInterests);


    }
}
