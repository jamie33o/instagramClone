package com.example.instagramclone.tabs;

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
import android.widget.Toast;

import com.example.instagramclone.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.io.ByteArrayOutputStream;

public class SharePictureTab extends Fragment implements View.OnClickListener{

    private ImageView imgViewShare;
    private EditText edtDescription;
    private Button btnShareImg;

Bitmap receivedImageBitmap;
    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
    imgViewShare = view.findViewById(R.id.imgViewShare);

    edtDescription = view.findViewById(R.id.edtDescription);

    btnShareImg = view.findViewById(R.id.btnshareImg);

btnShareImg.setOnClickListener(SharePictureTab.this);
imgViewShare.setOnClickListener(SharePictureTab.this);



return view;
    }

    @Override
    public void onClick(View view) {

switch(view.getId()){

    case R.id.imgViewShare:
        
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
    case R.id.btnshareImg:


        if(receivedImageBitmap != null){

            if(edtDescription.getText().toString().equals("")){

                Toast.makeText(getContext(),"Error: you must specify a despription", Toast.LENGTH_SHORT).show();

            }else{

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//converts image to array of bytes
                receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayOutputStream);//how u want to compress it
                byte[] bytes = byteArrayOutputStream.toByteArray(); //array has image

                ParseFile parseFile = new ParseFile("img.png", bytes);////the img in bytes create parse the file
                ParseObject parseObject = new ParseObject("Photo");//new parse object with class Photo
                parseObject.put("picture", parseFile);//parse object and put column called picture
                parseObject.put("image_des", edtDescription.getText().toString());//new column for image decription
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());//and column for username


                final ProgressDialog dialog = new ProgressDialog(getContext());//shows loading image
                dialog.setMessage("Loading");
                dialog.show();
                parseObject.saveInBackground(new SaveCallback() {
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
            }
        }else{
            Toast.makeText(getContext(),"Unknown Error: you must select an Image", Toast.LENGTH_SHORT).show();

        }
        break;

}
    }

    private void getChoosenImage() {
        Toast.makeText(getContext(),"Now we can access the images", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent,2000);
    //INTENT USED TO GET IMAGES reult might be image or might me null

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

                imgViewShare.setImageBitmap(receivedImageBitmap);

            }catch(Exception e){
                e.printStackTrace();
            }
            }
        }

    }
}