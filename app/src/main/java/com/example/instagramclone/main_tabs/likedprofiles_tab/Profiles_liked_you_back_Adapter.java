package com.example.instagramclone.main_tabs.likedprofiles_tab;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.instagramclone.main_tabs.ItemModel;

import java.util.List;

public class Profiles_liked_you_back_Adapter extends LikedProfilesAdapter{
    public Profiles_liked_you_back_Adapter(Context context, List<ItemModel> likedProfiles) {
        super(context, likedProfiles);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedProfileHolder holder, int position) {
        super.onBindViewHolder(holder, position);



    }
}