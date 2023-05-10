package com.example.instagramclone.main_tabs.likedprofiles_tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.instagramclone.R;
import com.example.instagramclone.braintree_payment.PaymentActivity;
import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging.Messaging_Activity;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Liked_Profile_Adapter extends RecyclerView.Adapter<Liked_Profile_Adapter.LikedProfileHolder> {
    private final Context context;
    private final List<ItemModel> likedProfiles;

    String text;
    public Liked_Profile_Adapter(Context context, List<ItemModel> likedProfiles) {
        this.context = context;
        this.likedProfiles = likedProfiles;
    }

    @NonNull
    @Override
    public LikedProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.liked_profiles_item_card, parent, false);
        return new LikedProfileHolder(view,this);
    }
    @Override
    public void onBindViewHolder(@NonNull LikedProfileHolder holder, int position) {
        holder.setData(likedProfiles.get(position));

    }

    public void setText(String text){
        this.text=text;
    }

    public String getText(){
        return text;
    }
    @Override
    public int getItemCount() {
        return likedProfiles.size();
    }

    public static class LikedProfileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageLp;
        private final TextView name, age, county;
       private final Liked_Profile_Adapter adapter;
        private ParseUser recipientUserClassPointer;
        private  boolean isPayed=false;
        public LikedProfileHolder(View itemView, Liked_Profile_Adapter adapter) {
            super(itemView);
            this.adapter =adapter;
            imageLp = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            county = itemView.findViewById(R.id.item_city);

        }


        public void setData(ItemModel data) {
            isPayed = data.getIsPayed();

            Picasso.get()
                    .load(data.getImage1())
                    .placeholder(R.drawable.baseline_add_a_photo_24)
                    .fit()
                    .centerCrop()
                    .into(imageLp);


            name.setText(data.getName());
            if(data.getShowLocation()){
                age.setText(data.getAge());
            }else {
                age.setVisibility(View.GONE);

            }

            if(data.getShowLocation()){
                county.setText(data.getCounty());
            }else {
                county.setVisibility(View.GONE);

            }

            recipientUserClassPointer = data.getUserClassPointer();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(isPayed) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", recipientUserClassPointer);

                Intent intent = new Intent(view.getContext(), Messaging_Activity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }else{
                view.getContext().startActivity(new Intent(view.getContext(), PaymentActivity.class));
            }
        }
    }
}
