package com.example.instagramclone.spotify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs;

    //used to set onCloseActivityListener in binder
    private SongViewHolder.OnCloseActivityListener onCloseActivityListener;

    public SongAdapter(List<Song> songs) {
            this.songs = songs;
        }

        @NonNull
        @Override
        public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
            return new SongViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
            Song song = songs.get(position);
            holder.songNameTextView.setText(song.getName());
            holder.artistNameTextView.setText(song.getArtistName());
            holder.albumNametv.setText(song.getAlbumName());


            Picasso.get()
                    .load(song.getImage())
                    .resize(100,100)
                    .centerCrop()
                    .into(holder.imageView);

            holder.setOnCloseActivityListener(onCloseActivityListener);

        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

    public void setViewHolderOnCloseActivityListener(SongViewHolder.OnCloseActivityListener listener) {
        this.onCloseActivityListener = listener;
    }
        public static class SongViewHolder extends RecyclerView.ViewHolder {
            public TextView songNameTextView;
            public TextView artistNameTextView;
            public TextView albumNametv;
            public ImageView imageView;
            Realm realm;
            public interface OnCloseActivityListener {
                void onCloseActivity();
            }
            RealmModel results;
            private OnCloseActivityListener onCloseActivityListener;


            public SongViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.songImage);
                songNameTextView = itemView.findViewById(R.id.songNameTextView);
                artistNameTextView = itemView.findViewById(R.id.artistNameTextView);
                albumNametv = itemView.findViewById(R.id.albumNameTv);
                realm = RealmManager.getRealmInstance();
                results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        realm.beginTransaction();
                        results.setSongName(songNameTextView.getText().toString());
                        results.setArtistName(artistNameTextView.getText().toString());
                        realm.commitTransaction();

                        if (onCloseActivityListener != null) {
                            onCloseActivityListener.onCloseActivity();
                        }


                    }
                });

            }
            public void setOnCloseActivityListener(OnCloseActivityListener listener) {
                this.onCloseActivityListener = listener;
            }

        }
    }


