package com.example.instagramclone.main_tabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.example.instagramclone.edit_profile_N_profile.QueriesEditProfile;
import com.example.instagramclone.login_signup.SignUp;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class SocialMediaActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Ignite");

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        QueriesEditProfile queryForProfile = new QueriesEditProfile(this);
        queryForProfile.queryProfileNstoreInSharedPrefs();


        viewPager = findViewById(R.id.viewPager_heights);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, false);

        sharedPreferencesManager = new SharedPreferencesManagerImpl(this,"Profile", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);//inflates menu xml

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.postImageItem) {// checks if user has clicked camera image

            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    checkSelfPermission(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        3000);


            } else {
                captureImage();
            }


        }else if(item.getItemId() == R.id.logoutUserItem){

            sharedPreferencesManager.clear();
            ParseUser.logOut();
            finish();
            Intent intent = new Intent(SocialMediaActivity.this, SignUp.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

   if(requestCode == 3000){
       if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
           captureImage();
       }
   }

    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 4000 && resultCode == RESULT_OK && data != null){
            try{
                Uri capturedImage = data.getData();//this to capture image from user camera
                Bitmap bitmap = MediaStore.Images.Media.
                        getBitmap(this.getContentResolver(),capturedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//this is to turn to byte array for uploading to server
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();

                ParseFile parseFile = new ParseFile("img.png", bytes);////the img in bytes create parse the file
                ParseObject parseObject = new ParseObject("Photo");//new parse object with class Photo
                parseObject.put("picture", parseFile);//parse object and put column called picture
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());//adds username of current user

                final ProgressDialog dialog = new ProgressDialog(this);//shows loading image
                dialog.setMessage("Loading");
                dialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e ==null){
                            Toast.makeText(SocialMediaActivity.this,"Done!!!", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SocialMediaActivity.this,"Unknown Error: ", Toast.LENGTH_SHORT).show();

                        }
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}