package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.net.Uri;
/*
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.realm.RealmModel;*/
import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.parse.ParseFile;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/*

import io.realm.Realm;
*/

public class ImageProcessor {
    private static final String IMAGE_EXTENSION = ".png";
    DataBaseUtils dataBaseUtils;


    Context context;

    public ImageProcessor(Context context){
        this.context = context;

        dataBaseUtils = new ReusableQueries(context);


    }



    public void createParseImageUpload(Uri imageUri, String columnName) {
        try (InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();

        } catch (IOException e) {
            FancyToast.makeText(context,e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        }
    }





    public File createFileFromInputStream(Uri filePath) {

        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File file = null;
        try {
            file = File.createTempFile("temp_image", ".jpg", context.getCacheDir());
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
