package com.example.instagramclone.edit_profile_N_profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.R;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.reusable_code.SizeBasedOnDensity;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.neovisionaries.i18n.CountryCode;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmList;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {

    RealmList<String> realmList1;
    private ImageView backbtn, profileImg;
    private TextView tvName, tvAge, tvBio;
    RealmList<String> languageslist;
    private TableLayout interestsLayoutProfile, languagesLayoutProfile, mybasicsLayoutProfile, mylifestyleLayoutProfile, myfavouritesongLayoutProile;
    SizeBasedOnDensity sizeBasedOnDensity;
    Realm realm;
    RealmList<String> interestsList;
    private GetTrackTask getTrackTask;

    String artisrName,songName;
    RealmModel results;
    private ImageButton pauseButton,playButton;
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "857b49fc34f84d8a91f7baf4faa63acd";
    private static final String REDIRECT_URI = "editprofile:/callback";
    private static final String CLIENT_SECRET = "6fad0bc190fc484cbf8bb7bc734e23b1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        realm = RealmManager.getRealmInstance();
        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();
        sizeBasedOnDensity = new SizeBasedOnDensity(this);
        ButtonCreator buttonCreator = new ButtonCreator(this, new ArrayList<>());

        //buttons
        backbtn = findViewById(R.id.btnBackProfile);
        backbtn.setOnClickListener(this);
        profileImg = findViewById(R.id.imgProfile);
        playButton = findViewById(R.id.playbtn);
        playButton.setOnClickListener(this);
        pauseButton = findViewById(R.id.pausebtn);
        pauseButton.setOnClickListener(this);
        pauseButton.setVisibility(View.GONE);


        //textviews
        tvName = findViewById(R.id.tvName);
        tvAge = findViewById(R.id.tvAge);
        tvBio = findViewById(R.id.tvBio);

        //spotify
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-library-read", "streaming", "user-follow-read", "user-top-read", "user-read-private", "playlist-read-private", "user-read-playback-state", "playlist-read-collaborative"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
        //tablelayouts
        languagesLayoutProfile = findViewById(R.id.languagesLayoutProfile);
        interestsLayoutProfile = findViewById(R.id.interestsLayoutProfile);
        mybasicsLayoutProfile = findViewById(R.id.myBasicsLayoutProfile);
        mylifestyleLayoutProfile = findViewById(R.id.myLifestyleLayoutProfile);
        myfavouritesongLayoutProile = findViewById(R.id.myFavouriteLayoutProfile);

        realm.beginTransaction();
        if(results.getInterests() != null)
            interestsList = results.getInterests();
        if(results.getLanguages() != null)
            languageslist = results.getLanguages();

        if(results.getWorkout()!= null) {
            realmList1 = results.getWorkout();
        }
        artisrName =results.getArtistName();
        songName= results.getSongName();
        List<String> myLifeStyle = new ArrayList<>(realmList1);


        if (results.getPets() != null)
            myLifeStyle.add(results.getPets());
        if (results.getSmoking() != null)
            myLifeStyle.add(results.getSmoking());
        if (results.getDrinking() != null)
            myLifeStyle.add(results.getDrinking());
        if (results.getDietary() != null)
            myLifeStyle.add(results.getDietary());
        if (results.getSocialMedia() != null)
            myLifeStyle.add(results.getSocialMedia());
        if (results.getKids() != null)
            myLifeStyle.add(results.getKids());

        RealmList<String> realmList = results.getPronounes();

        List<String> mybasics = new ArrayList<>(realmList);

        if (results.getRelationShipGoals() != null)
            mybasics.add(results.getRelationShipGoals());
        if (results.getHometown() != null)
            mybasics.add(results.getHometown());
        if (results.getReligion() != null)
            mybasics.add(results.getReligion());
        if (results.getStarSign() != null)
            mybasics.add(results.getStarSign());
        if (results.getGender() != null)
            mybasics.add(results.getGender());
        if (results.getHeight() != null)
            mybasics.add(results.getHeight());


// Convert the RealmList to an array of strings
        String[] interestsArray = interestsList.toArray(new String[0]);
        String[] languagesArray = languageslist.toArray(new String[0]);

        if (interestsArray.length > 0)
            buttonCreator.buttonCreator(interestsLayoutProfile, this, "", interestsArray);

        if (myLifeStyle.size() > 0)
            buttonCreator.buttonCreator(mylifestyleLayoutProfile, this, "", myLifeStyle.toArray(new String[0]));

        if (mybasics.size() > 0)
            buttonCreator.buttonCreator(mybasicsLayoutProfile, this, "", mybasics.toArray(new String[0]));

        if (languagesArray.length > 0)
            buttonCreator.buttonCreator(languagesLayoutProfile, this, "languages", languagesArray);

        PiccassoLoadToImageView piccassoLoadToImageView = new PiccassoLoadToImageView(this);

        piccassoLoadToImageView.getImageNloadIntoImageview(profileImg, results.getImage1Name(), "image1", sizeBasedOnDensity.fullScreenWidth(), sizeBasedOnDensity.heightRatio(400), 0);

        String name = results.getName() + ", ";
        tvName.setText(name);

        tvAge.setText(results.getAge());

        tvBio.setText(results.getBio());

        realm.commitTransaction();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getTrackTask != null) {
            getTrackTask.cancel(true);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                case TOKEN:
                    String accessToken = response.getAccessToken();

                    // Fetch the track using the GetTrackTask
                    SpotifyApi api = new SpotifyApi.Builder()
                            .setAccessToken(accessToken)
                            .build();
                    getTrackTask = new GetTrackTask(this, api);

                    getTrackTask.execute(artisrName,songName);

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
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnBackProfile) {
            finish();
        }

        if(viewId == playButton.getId()){
        if (getTrackTask != null && getTrackTask.getMediaPlayer() != null) {
            getTrackTask.getMediaPlayer().start();
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);

        }
        }else if(viewId == pauseButton.getId()){
            if (getTrackTask != null && getTrackTask.getMediaPlayer() != null) {
                getTrackTask.getMediaPlayer().pause();
                pauseButton.setVisibility(View.GONE);
                playButton.setVisibility(View.VISIBLE);

            }

        }
        }


    public void setSong(String imageUrl, String albumName, String artistName, String trackName) {


        TextView albumNametv = findViewById(R.id.albumNameTv);
        albumNametv.setText(albumName);
        TextView artistNametv = findViewById(R.id.artistNameTv);
        artistNametv.setText(artistName);
        TextView trackNametv = findViewById(R.id.songNameTv);
        trackNametv.setText(trackName);
        ImageView trackImage = findViewById(R.id.songImg);
// Create an ImageView object for the song image and add it to the TableRow
        Picasso.get()
                .load(imageUrl)
                .resize(150, 150)
                .centerCrop()
                .into(trackImage);


    }





    public static class GetTrackTask extends AsyncTask<String, Void, Track> {
        private final WeakReference<ProfilePage> activityRef;
        private final SpotifyApi api;

        public MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }

        public void setMediaPlayer(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        private MediaPlayer mediaPlayer;

        GetTrackTask(ProfilePage activity, SpotifyApi api) {
            activityRef = new WeakReference<>(activity);
            this.api = api;
            mediaPlayer = new MediaPlayer();
        }

        public Track doInBackground(String... params) {
            String artistName = params[0];
            String songName = params[1];

            try {

                SearchTracksRequest searchTracksRequest = api.searchTracks(artistName + " " + songName)
                        .market(CountryCode.IE)
                        .includeExternal("audio")
                        .limit(1)
                        .build();

                Paging<Track> trackPaging = searchTracksRequest.execute();

                if (trackPaging.getTotal() > 0) {
                    return trackPaging.getItems()[0];
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Track track) {
            ProfilePage activity = activityRef.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            if (track != null) {

                Image image = track.getAlbum().getImages()[0];
                String imageUrl = image.getUrl(); //
                String artistName = track.getArtists()[0].getName();
                String albumName = track.getAlbum().getName();
                String trackName = track.getName();
                String previewUrl = track.getPreviewUrl();

                activity.setSong(imageUrl, albumName, artistName, trackName);

                try {
                    mediaPlayer.setDataSource(previewUrl);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}