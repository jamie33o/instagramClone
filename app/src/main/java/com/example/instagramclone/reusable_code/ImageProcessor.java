package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
public class ImageProcessor {
    Context context;
    public ImageProcessor(Context context){
        this.context = context;
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
