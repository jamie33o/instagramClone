package com.example.instagramclone.main_tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPost extends AppCompatActivity {

    private LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);

        linearLayout = findViewById(R.id.linearLayout);


        Intent receivedIntentObject = getIntent();
        String receivedUserName = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this,receivedUserName, FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        setTitle(receivedUserName + "'s posts");//sets title at top of page

        //shows user profile when profile username clicked
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("GetUserLocation");
        //parseQuery.whereEqualTo("username", receivedUserName);
        parseQuery.orderByDescending("createdAt");//gets created date and puts it up newiest first

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for(ParseObject post : objects) {
                        TextView postDescription = new TextView(UsersPost.this);
                        postDescription.setText(post.get("image_des") + "");
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null){
                                    //crates image and image view
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);//convert  byte array from server to bitmap

                                    ImageView postImageView = new ImageView(UsersPost.this);//create new ui component imageview same as done in xml
                                    //creating the design of the imageview lyout
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(params);
                                    postImageView.setAdjustViewBounds(true);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    // creates xml layout for the description for img
                                    LinearLayout.LayoutParams des_params =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,15,5,106);

                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.MAGENTA);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);


                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);

                                }

                            }
                        });

                    }
                }else{
                    FancyToast.makeText(UsersPost.this,receivedUserName + " does'nt have any post",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();
                    finish();
                }
                dialog.dismiss();

            }

        });

    }
}