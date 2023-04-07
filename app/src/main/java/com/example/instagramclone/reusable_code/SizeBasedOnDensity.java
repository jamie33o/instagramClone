package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.util.DisplayMetrics;

public class SizeBasedOnDensity {
  float density;
  Context context;
  DisplayMetrics metrics;
  public  SizeBasedOnDensity(Context context){
    this.context=context;
     metrics = context.getResources().getDisplayMetrics();
     density = metrics.density;

  }

  public int widthRatio(int width){

    return (int) (width * density);
  }

  public int heightRatio(int height){

    return (int) (height * density);
  }
  public int fullScreenWidth(){
    return metrics.widthPixels;

  }

  public int fullScreenHeight(){
    return metrics.heightPixels;

  }


}
