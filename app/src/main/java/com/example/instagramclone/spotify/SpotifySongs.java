package com.example.instagramclone.spotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.reusable_code.Dialogs;
import com.neovisionaries.i18n.CountryCode;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

public class SpotifySongs extends AppCompatActivity implements SongAdapter.SongViewHolder.OnCloseActivityListener  {
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "857b49fc34f84d8a91f7baf4faa63acd";
    private static final String REDIRECT_URI = "editprofile:/callback";

    private SongAdapter songAdapter;
    private final List<Song> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_songs);

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"user-library-read","streaming","user-follow-read","user-top-read","user-read-private", "playlist-read-private", "user-read-playback-state", "playlist-read-collaborative"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);


        RecyclerView songsRecyclerView = findViewById(R.id.songsRecyclerView);
        songAdapter = new SongAdapter(songs);
        songsRecyclerView.setAdapter(songAdapter);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        songAdapter.setViewHolderOnCloseActivityListener(this);

        Button deleteSong = findViewById(R.id.delete_song);

        deleteSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
                    @Override
                    public void done(ParseModel parseModel, ParseException e) {
                        if (e == null) {
                            // Check if column exists in the object

                            parseModel.remove(ParseModel.ALBUM_NAME);
                            parseModel.remove(ParseModel.ARTIST_NAME);
                            parseModel.remove(ParseModel.TRACK_IMAGE);
                            parseModel.remove(ParseModel.SONG_NAME);
                            parseModel.pinInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Dialogs.showSnackbar(SpotifySongs.this,"Song deleted", 2000);

                                    } else {
                                        Dialogs.showSnackbar(SpotifySongs.this,"Error deleting song", 2000);

                                    }
                                }
                            });
                        } else {
                            Dialogs.showSnackbar(SpotifySongs.this,"Error deleting song", 2000);

                        }
                    }

                });   }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                case TOKEN:
                    String accessToken = response.getAccessToken();

                    SpotifyApi api = new SpotifyApi.Builder()
                            .setAccessToken(accessToken)
                            .build();
                    SearchView searchView = findViewById(R.id.searchView);
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            // Call GetPlaylistsTask with search query
                            GetPlaylistsTask task = new GetPlaylistsTask(SpotifySongs.this, api);
                            task.execute(query);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            // Do nothing
                            return false;
                        }
                    });


                    break;
                case ERROR:
                    String error = response.getError();
                    break;
                default:
                    // Handle other cases
            }
        }


    }

    @Override
    public void onCloseActivity() {
        finish();
    }

    public static class GetPlaylistsTask extends AsyncTask<String, Void, List<Track>> {
        private final WeakReference<SpotifySongs> activityRef;
        private final SpotifyApi api;

        GetPlaylistsTask(SpotifySongs activity, SpotifyApi api) {
            activityRef = new WeakReference<>(activity);
            this.api = api;
        }

        public List<Track> doInBackground(String... params) {

            try {
                SearchTracksRequest searchTracksRequest = api.searchTracks(params[0])
                        .market(CountryCode.IE)
                        .limit(20)
                        .build();


                Paging<Track> trackPaging = searchTracksRequest.execute();

                return new ArrayList<>(Arrays.asList(trackPaging.getItems()));

            } catch (Exception e) {


                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            SpotifySongs activity = activityRef.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            if(tracks!=null) {
                // Update UI with the top tracks
                for (Track track : tracks) {
                    Image image = track.getAlbum().getImages()[0];
                    String imageUrl = image.getUrl(); //
                    Song song = new Song(track.getName(), track.getAlbum().getName(), track.getArtists()[0].getName(),imageUrl);
                    activity.songs.add(song);
                }
                activity.songAdapter.notifyDataSetChanged();

            }
        }
    }
}