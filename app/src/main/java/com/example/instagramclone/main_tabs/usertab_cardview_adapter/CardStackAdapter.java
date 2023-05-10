package com.example.instagramclone.main_tabs.usertab_cardview_adapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.ProfileTab.RoundedCornersTransformation;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ImageAdapter;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;
import com.example.instagramclone.reusable_code.DotsIndicator;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class CardStackAdapter extends CardStackView.Adapter<CardStackAdapter.ViewHolder> {

    private final List<ItemModel> items;
    int position;

    public CardStackAdapter(List<ItemModel> items,int position) {
        this.items = items;

        this.position=position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends CardStackView.ViewHolder implements  View.OnClickListener{
        private final TextView name, age, county;
        private ParseUser user;
        int position;
        private List<String> img; // initial index

        private final ViewPager2 viewPager2;
        private final View dot1;
        private final View dot2;
        private final View dot3;

        ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            county = itemView.findViewById(R.id.item_city);
            viewPager2 = itemView.findViewById(R.id.viewPager_cardstackview);
            dot1 = itemView.findViewById(R.id.dot1);
            dot2 = itemView.findViewById(R.id.dot2);
            dot3 = itemView.findViewById(R.id.dot3);


        }

        public void setData(ItemModel data) {

            int counter=0;
            counter++;

            System.out.println(counter);
            img = new ArrayList<>();
            List<View> dots = new ArrayList<>();
                img.add(data.getImage1());
                dots.add(dot1);

                    img.add(data.getImage2());
                    dots.add(dot2);


                img.add(data.getImage3());
                dots.add(dot3);

            new DotsIndicator(dots,itemView.getContext(),viewPager2);

            position = 0;
            ImageAdapter imageAdapter = new ImageAdapter(img,itemView.getContext(),0);
            viewPager2.setAdapter(imageAdapter);



            name.setText(data.getName());
            age.setText(data.getAge());
            county.setText(data.getCounty());
            user = data.getUserClassPointer();

           itemView.findViewById(R.id.clickable_left).setOnClickListener(this);
            itemView.findViewById(R.id.clickable_right).setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.clickable_left){
                position = position > 0 ? position-1: 0 ;
                viewPager2.setCurrentItem(position);
            } else if (v.getId() == R.id.clickable_right) {
                position = position == img.size()-1 ? img.size()-1: position+1 ;
                viewPager2.setCurrentItem(position);

            }
/*
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);

            Intent intent = new Intent(itemView.getContext(), ProfilePage.class);
            intent.putExtras(bundle);
            itemView.getContext().startActivity(intent);*/
        }
    }

    public List<ItemModel> getItems() {

        return items;

    }





}