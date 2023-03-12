package com.example.instagramclone;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EditProfile  extends AppCompatActivity implements EditProfileAdapter.MyViewHolder.OnItemClickListener, EditProfileAdapter.MyViewHolder.OnFocusChangeListener {
    private static final String IMAGE_EXTENSION = ".png";
    EditProfileAdapter queryDataBaseAdapter;
    String parseUser;
    String filePath, filename;

    private ImageView clickedImageView;
    private String columnName;
    QueryDatabase queryDatabase;
    String[] columnNames;
    Bitmap receivedImageBitmap, bitmap1, bitmap2, bitmap3;
    ParseObject userClass;
    // String filePath, filename;
    private RecyclerView recyclerView;
    private EditProfileAdapter adapter;
    private List<ProfileModel> profileModels;

    // String columnName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_recycleview);

        //queryDataBaseAdapter = new EditProfileAdapter();
        queryDatabase = new QueryDatabase(this);
        queryDatabase.queryDatabaseProfile(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queryDatabase = new QueryDatabase(this);
        queryDatabase.queryDatabaseProfile(this);

        // Initialize the model list and adapter
        profileModels = new ArrayList<>();
        adapter = new EditProfileAdapter(profileModels, this, this);

        // Set the adapter on the RecyclerView

        // Add some dummy data to the model list




        parseUser = ParseUser.getCurrentUser().getUsername();
        userClass = ParseUser.getCurrentUser();



        columnNames = new String[]{"image1", "image2", "image"};

        String[] views = new String[3];

        // queryDatabase.getCurrentUserImagesQueryDatabase(getContext(),columnNames);


        //  return view;//must return type view


    }

    /*
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            }
        }*/
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
    public void onItemClick(View view, int position) {
        int viewId = view.getId();


        if (viewId == R.id.backbtn) {
            finish();
        }

        if (viewId == R.id.imgViewShare || viewId == R.id.imgViewShare1 || viewId == R.id.imgViewShare2) {


            clickedImageView = (ImageView) view;
            columnName = (String) clickedImageView.getTag();


            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]
                                {android.Manifest.permission.READ_EXTERNAL_STORAGE},
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


   /* public String turnImageToPNGreturnFilePath(Bitmap bitmap){
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

        return filePath;



    }*/



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){


                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getApplication().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);


                    createParseImageUpload(receivedImageBitmap,columnName);


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }







    public void createParseImageUpload(Bitmap imageBitmap, String columnName) {
        if (imageBitmap != null) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bytes = outputStream.toByteArray();
                ParseFile parseFile = new ParseFile(columnName + IMAGE_EXTENSION, bytes);//IMAGE_EXTENSION:.png
                queryDatabase.putQueryDatabase(columnName, parseFile,this);

            } catch (IOException e) {
                // Handle the exception
            }
        }
    }


    public void addList(String name, String age, String county, String userName, String profileBio, String profileProfession, String profileHobbies, String[] chosenInterests, String[] chosenCounties, String image1, String image2, String image3) {

            profileModels.add(new ProfileModel(name, age, county, userName, profileBio, profileProfession, profileHobbies, chosenInterests, chosenCounties, image1, image2, image3));
        recyclerView.setAdapter(adapter);

        }


    }
