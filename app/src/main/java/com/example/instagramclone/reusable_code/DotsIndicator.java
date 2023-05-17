package com.example.instagramclone.reusable_code;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.R;

import java.util.List;
import java.util.Objects;

public class DotsIndicator {

    int currentPosition;
    int lastpos = 0;

    public DotsIndicator(List<View> dots, Context context, RecyclerView rv, LinearLayoutManager lm) {

        dots.get(0).setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_corner_shape_red, null));
        //used to change color of dots as user scrolls trough recycleview
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Get the current position of the adapter
                currentPosition = lm.findFirstVisibleItemPosition();
                dots.get(lastpos).setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_coner_shape_grey, null));
                dots.get(currentPosition).setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_corner_shape_red, null));

                lastpos = currentPosition;


            }
        });


    }

    public DotsIndicator(List<View> dots, Context context, ViewPager2 vp) {
        dots.get(0).setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_corner_shape_red, null));




        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                dots.get(lastpos).setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_coner_shape_grey, null));
                dots.get(position).setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_corner_shape_red, null));
                lastpos = position;
            }
        });
    }
}