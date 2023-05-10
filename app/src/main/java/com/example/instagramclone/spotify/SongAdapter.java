package com.example.instagramclone.spotify;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.Dialogs;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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

            public interface OnCloseActivityListener {
                void onCloseActivity();
            }
            private OnCloseActivityListener onCloseActivityListener;


            public SongViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.songImage);
                songNameTextView = itemView.findViewById(R.id.songNameTextView);
                artistNameTextView = itemView.findViewById(R.id.artistNameTextView);
                albumNametv = itemView.findViewById(R.id.albumNameTv);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
                            @Override
                            public void done(ParseModel parseModel, ParseException e) {
                                if (e == null) {
                                    parseModel.setSongName(songNameTextView.getText().toString());
                                    parseModel.setArtistName(artistNameTextView.getText().toString());
                                    parseModel.setAlbumName(albumNametv.getText().toString());

                                    if (parseModel.getTrackImage()!=null) {
                                        File oldFile = new File(parseModel.getTrackImage());
                                        if (oldFile.exists()) {
                                            boolean deleted = oldFile.delete();
                                            if (!deleted) {
                                                // handle error
                                            }
                                        }
                                    }

                                    Drawable drawable = imageView.getDrawable();

                                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                                    // Assume that you have the image as a Bitmap object
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

// Create a file to save the image
                                    File file = new File(itemView.getContext().getCacheDir(), timeStamp + ".jpg");

                                    try {
                                        // Convert the bitmap to JPEG format and save it to the file
                                        FileOutputStream fos = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                        fos.flush();
                                        fos.close();
                                    } catch (IOException q) {
                                        q.printStackTrace();
                                    }

// Now you can use the file as needed

                                    parseModel.setTrackImage(file.getAbsolutePath());
                                    // Save the object locally
                                    parseModel.pinInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                // Display a toast message to indicate that the object has been saved locally
                                                Dialogs.showSnackbar(v.getContext(), "Success!!!\n Selection's saved", 2000);
                                            } else {
                                                Dialogs.showSnackbar(v.getContext(), "Error!!!\n Selection's not saved", 2000);


                                            }
                                        }
                                    });
                                } else {
                                    Dialogs.showSnackbar(v.getContext(), "Error!!!\n Selection's not saved", 2000);
                                }
                            }
                        });



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


