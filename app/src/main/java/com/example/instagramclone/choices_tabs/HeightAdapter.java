package com.example.instagramclone.choices_tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;

import java.util.List;

public class HeightAdapter extends RecyclerView.Adapter<HeightAdapter.HeightViewHolder> {
    private Context context;
    private List<String> heightList;
    private String selectedText = "";

    public HeightAdapter(Context context, List<String> heightList) {
        this.context = context;
        this.heightList = heightList;
    }

    @NonNull
    @Override
    public HeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_height, parent, false);
        return new HeightViewHolder(view,this);
    }
    @Override
    public void onBindViewHolder(@NonNull HeightViewHolder holder, int position) {

        String height = heightList.get(position);
        holder.heightTextView.setText(height);


    }
    public String getSelectedText(){
        return selectedText;
    }
    public void setSelectedText(String selectedText){
        this.selectedText=selectedText;
    }

    @Override
    public int getItemCount() {
        return heightList.size();
    }

    public static class HeightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView heightTextView;
        HeightAdapter adapter;


        public HeightViewHolder(View itemView, HeightAdapter adapter) {
            super(itemView);
            this.adapter =adapter;
            heightTextView = itemView.findViewById(R.id.item_height);

            heightTextView.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            String text = heightTextView.getText().toString();
            adapter.setSelectedText(text);
        }
    }
}
