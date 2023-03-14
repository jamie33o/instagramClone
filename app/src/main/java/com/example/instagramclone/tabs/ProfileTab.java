package com.example.instagramclone.tabs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagramclone.ButtonCreator;
import com.example.instagramclone.edit_profile.EditProfile;
import com.example.instagramclone.QueryDatabase;
import com.example.instagramclone.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ProfileTab extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
//Original myprofiletab


    ButtonCreator buttonCreator;
    String parseUser;
    QueryDatabase queryDatabase;
    public ImageView imgViewShare;
    String[] columnNames;
    Bitmap receivedImageBitmap,bitmap1,bitmap2,bitmap3;
    ParseObject userClass;
    String filePath, filename;


    String columnName;
    public ProfileTab() {



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                container, false);




        buttonCreator = new ButtonCreator(getContext(),this);


        queryDatabase = new QueryDatabase(getContext());




        imgViewShare = view.findViewById(R.id.imgViewShare);


        imgViewShare.setOnClickListener(ProfileTab.this);

        imgViewShare.setImageResource(R.drawable.baseline_add_a_photo_24);




        parseUser = ParseUser.getCurrentUser().getUsername();
        userClass = ParseUser.getCurrentUser();


        //queryDatabase.getUserInfoQueryDatabase();


        String[] views = new String[3];



        return view;//must return type view



    }










    @Override
    public void onClick(View view) {//for uploading image


        int viewId = view.getId();


        if (viewId == R.id.imgViewShare || viewId == R.id.imgViewShare1 || viewId == R.id.imgViewShare2) {
            Intent intent = new Intent(getContext(), EditProfile.class);
            startActivity(intent);
            /*clickedImageView = (ImageView) view;
            columnName = (String) clickedImageView.getTag();


            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]
                                {android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);


            }else{
                getChoosenImage();*/
            }




      //  }
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
                queryDatabase.putQueryDatabase(fieldName, text,getContext());
                if(fieldName.equals("username")) {
                    userClass.put("username", text);
                }
        }
    }
}