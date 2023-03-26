package com.example.instagramclone.reusable_database_queries;

import android.content.Context;
import android.widget.ImageView;

public interface DataBaseUtils {

      void deleteFileFromDatabase(String columnName);
        void uploadToDataBase(String columnKey, Object columnValue, Context context);


}
