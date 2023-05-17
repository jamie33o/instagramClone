package com.example.instagramclone.main_tabs.usertab_cardview_adapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;


import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ImageAdapter;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;
import com.example.instagramclone.reusable_code.DotsIndicator;
import com.parse.ParseUser;

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
        private int position;
        private List<String> img; // initial index

        private final ImageView profileBtn;
        private final ViewPager2 viewPager2;
        private final View dot1;
        private final View dot2;
        private final View dot3;
        List<View> dots;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            county = itemView.findViewById(R.id.item_city);
            viewPager2 = itemView.findViewById(R.id.viewPager_cardstackview);
            dot1 = itemView.findViewById(R.id.dot1);
            dot2 = itemView.findViewById(R.id.dot2);
            dot3 = itemView.findViewById(R.id.dot3);
            profileBtn = itemView.findViewById(R.id.profie_btn);
            profileBtn.setOnClickListener(this);
            itemView.findViewById(R.id.clickable_left).setOnClickListener(this);
            itemView.findViewById(R.id.clickable_right).setOnClickListener(this);
             dots = new ArrayList<>();
            dots.add(dot1);
            dots.add(dot2);
            dots.add(dot3);

        }

        public void setData(ItemModel data) {

            img = new ArrayList<>();

            if(data.getImage1() != null) {
                img.add(data.getImage1());
            }

            if(data.getImage2() != null) {
                img.add(data.getImage2());
            }

            if(data.getImage3() != null) {
                img.add(data.getImage3());
            }



                position = 0;
                ImageAdapter imageAdapter = new ImageAdapter(img, itemView.getContext(), 0);
                viewPager2.setAdapter(imageAdapter);
            new DotsIndicator(dots, itemView.getContext(), viewPager2);


            name.setText(data.getName());
            age.setText(data.getAge());
            county.setText(data.getCounty());
            user = data.getUserClassPointer();


        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.clickable_left){
                position = position > 0 ? position-1: 0 ;
                viewPager2.setCurrentItem(position);
            } else if (v.getId() == R.id.clickable_right && img.size() > 0) {
                position = position == img.size()-1 ? img.size()-1: position+1;
                viewPager2.setCurrentItem(position);
            }

            if(v.getId() == profileBtn.getId()) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);

                Intent intent = new Intent(itemView.getContext(), ProfilePage.class);
                intent.putExtras(bundle);
                itemView.getContext().startActivity(intent);
            }
        }
    }

    public List<ItemModel> getItems() {

        return items;

    }





}