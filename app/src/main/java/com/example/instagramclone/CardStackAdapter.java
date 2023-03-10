package com.example.instagramclone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.List;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<ItemModel> items;

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

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, age, county;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            county = itemView.findViewById(R.id.item_city);
        }

        void setData(ItemModel data) {

            Picasso.get()
                    .load(new File(data.getImage()))

                    .placeholder(R.drawable.baseline_add_a_photo_24)
                    .rotate(90)
                    .fit()
                    .centerCrop()
                    .into(image);

                        name.setText(data.getName());
                        age.setText(data.getAge());
                        county.setText(data.getCounty());
            System.out.println(getItemCount());
        }
    }

    public List<ItemModel> getItems() {

        return items;

    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
       notifyDataSetChanged(); // notify the adapter that the data set has changed

    }

    public void clearItems() {
        items.clear();
        notifyItemRangeChanged(0,items.size()); // notify the adapter that the data set has changed
    }




}