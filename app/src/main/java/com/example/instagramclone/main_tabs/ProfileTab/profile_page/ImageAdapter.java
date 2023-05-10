package com.example.instagramclone.main_tabs.ProfileTab.profile_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final List<String> mImages;
    private final PiccassoLoadToImageView picasso;
    private final int screenWidth;
    public ImageAdapter(List<String> images, Context context,int screenWidth) {
        this.screenWidth = screenWidth;
        mImages = images;
        picasso = new PiccassoLoadToImageView(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(screenWidth != 0) {
            picasso.getImageNloadIntoImageview(mImages.get(position), holder.mImageView, screenWidth, 1125, 0);
        }else {
            picasso.getImageNloadIntoImageview(mImages.get(position), holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.image_view);
        }
    }
}
