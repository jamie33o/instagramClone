package com.example.instagramclone.main_tabs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import com.example.instagramclone.edit_profile_N_profile.ProfilePage;
import com.example.instagramclone.edit_profile_N_profile.QueriesEditProfile;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.edit_profile_N_profile.EditProfile;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.R;
import com.parse.ParseObject;

import java.util.ArrayList;

public class ProfileTab extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {




    ButtonCreator buttonCreator;

    ReusableQueries queryDatabase;
    public ImageView profileImgView,editProfilebtn;
    String[] columnNames;
    Bitmap receivedImageBitmap,bitmap1,bitmap2,bitmap3;
    ParseObject userClass;
    QueriesEditProfile queriesEditProfile;
    String image1;
    private LinearLayout dotsLayout;
    private ImageView image, image2, image3;
    private View dot1, dot2, dot3;

    private int currentImage = 1;
    Handler handler1;
    Runnable runnable;

    public ProfileTab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                container, false);





        buttonCreator = new ButtonCreator(getContext(),new ArrayList<>());


        editProfilebtn = view.findViewById(R.id.editProfileBtn);
        editProfilebtn.setOnClickListener(this);

        queryDatabase = new ReusableQueries(getContext());

        profileImgView = view.findViewById(R.id.profileImgView);

        profileImgView.setOnClickListener(this);
        PiccassoLoadToImageView piccassoLoadToImageView = new PiccassoLoadToImageView(getContext());
        piccassoLoadToImageView.getImageNloadIntoImageview(profileImgView,"image1",500,500,300);




        dotsLayout = view.findViewById(R.id.dotsLayout);
        image =  view.findViewById(R.id.image1);
        image2 =  view.findViewById(R.id.image2);
        image3 =  view.findViewById(R.id.image3);
        dot1 =  view.findViewById(R.id.dot1);
        dot2 =  view.findViewById(R.id.dot2);
        dot3 =  view.findViewById(R.id.dot3);

        // Set initial state
        image.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
        image3.setVisibility(View.GONE);
           dot1.setBackgroundColor(getResources().getColor(R.color.purple_200));;
           dot2.setBackgroundColor(getResources().getColor(R.color.purple_200));
           dot3.setBackgroundColor(getResources().getColor(R.color.purple_200));





        // Start automatic image switching
         handler1 = new Handler();

       runnable = new Runnable() {
            public void run() {
                if(isAdded())
                    switchImage();

                handler1.postDelayed(this, 3000); // Switch image every 2 seconds
            }
        };


        handler1.removeCallbacksAndMessages(null);
        handler1.postDelayed(runnable, 3000);








        return view;//must return type view



    }


    private void switchImage() {

        switch (currentImage) {
            case 1:
                image.setVisibility(View.GONE);
                image2.setVisibility(View.VISIBLE);
                dot1.setBackgroundColor(getResources().getColor(R.color.purple_200));
                dot2.setBackgroundColor(getResources().getColor(R.color.white));
                currentImage = 2;
                break;
            case 2:
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.VISIBLE);
                dot2.setBackgroundColor(getResources().getColor(R.color.purple_200));
                dot3.setBackgroundColor(getResources().getColor(R.color.white));
                currentImage = 3;
                break;
            case 3:
                image3.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                dot3.setBackgroundColor(getResources().getColor(R.color.purple_200));
                dot1.setBackgroundColor(getResources().getColor(R.color.white));
                currentImage = 1;
                break;
        }



    }







    @Override
    public void onClick(View view) {//for uploading image


        int viewId = view.getId();

     /*   clickedImageView = (ImageView) view;
        columnName = (String) clickedImageView.getTag();*/
        if (viewId == R.id.editProfileBtn ) {
            Intent intent = new Intent(getContext(), EditProfile.class);
            startActivity(intent);

        }

        if (viewId == R.id.profileImgView) {

            Intent intent = new Intent(getContext(), ProfilePage.class);
            startActivity(intent);
        }


    }






   /* public void showProfileImages(Bitmap bitmap, String columnName){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           filename =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        }

                    File file = new File(getApplicationContext().getCacheDir(), filename + IMAGE_EXTENSION);
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



                    Picasso.get().load(filePath).into(clickedImageView);


        }
*/


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


                    //createParseImageUpload(receivedImageBitmap,columnName);


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

/*

    public void createParseImageUpload(Bitmap imageBitmap, String columnName) {
        if (imageBitmap != null) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bytes = outputStream.toByteArray();
                ParseFile parseFile = new ParseFile(columnName + IMAGE_EXTENSION, bytes);//IMAGE_EXTENSION:.png
                queryDatabase.putQueryDatabase(columnName, parseFile,getContext());

            } catch (IOException e) {
                // Handle the exception
            }
        }
    }

*/



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString();
                String fieldName = editText.getTag().toString();

                // Upload text to database for the field that lost focus

                queryDatabase.uploadToDataBase(fieldName, text,getContext());

            if(fieldName.equals("username")) {
                    userClass.put("username", text);
                }
        }
    }
}