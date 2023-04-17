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
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> mImages;
    PiccassoLoadToImageView picasso;
    public ImageAdapter(List<String> images, Context context) {
        mImages = images;
        picasso = new PiccassoLoadToImageView(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(mImages.get(position)).placeholder(R.drawable.instagramlogo).centerCrop().fit().into(holder.mImageView);
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
