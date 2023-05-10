package com.example.instagramclone.choices_tabs;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;

import java.util.List;

public class HeightAdapter extends RecyclerView.Adapter<HeightAdapter.HeightViewHolder> {
    private final Context context;
    private final List<String> heightList;

    public View view;
    public String height;
    TextView tv;
    public HeightAdapter(Context context, List<String> heightList) {
        this.context = context;
        this.heightList = heightList;

    }

    @NonNull
    @Override
    public HeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_height, parent, false);
        return new HeightViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HeightViewHolder holder, int position) {
        String height = heightList.get(position);
        holder.heightTextView.setText(height);
         tv = holder.heightTextView;
    }

    public String getHeight(){
        return tv.getText().toString();
    }



    @Override
    public int getItemCount() {
        return heightList.size();
    }

    public static class HeightViewHolder extends RecyclerView.ViewHolder {
        public TextView heightTextView;
        public HeightViewHolder(View itemView) {
            super(itemView);
            heightTextView = itemView.findViewById(R.id.item_height);

        }

    }
}
