package com.example.instagramclone.main_tabs.usertab_cardview_adapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackView;

import androidx.annotation.NonNull;

public class CardStackAdapter extends CardStackView.Adapter<CardStackAdapter.ViewHolder> {

    private  List<ItemModel> items;

    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
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
       private final ImageView image;
        private final TextView name, age, county;
        private ParseUser user;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            county = itemView.findViewById(R.id.item_city);
            itemView.setOnClickListener(this);
        }

        public void setData(ItemModel data) {

                Picasso.get()
                        .load(data.getImage())
                        .placeholder(R.drawable.baseline_add_a_photo_24)
                        .fit()
                        .centerCrop()
                        .into(image);

                        name.setText(data.getName());
                        age.setText(data.getAge());
                        county.setText(data.getCounty());
                        user = data.getUserClassPointer();
        }

        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);

            Intent intent = new Intent(itemView.getContext(), ProfilePage.class);
            intent.putExtras(bundle);
            itemView.getContext().startActivity(intent);
        }
    }

    public List<ItemModel> getItems() {

        return items;

    }





}