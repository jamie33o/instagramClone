package com.example.instagramclone.main_tabs.ProfileTab.profile_page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.SizeBasedOnDensity;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.neovisionaries.i18n.CountryCode;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {

    private ImageView backbtn;
    private TextView tvName, tvAge, tvBio,tital_at_company,where_i_live,myGender_N_pronouns;
    List<String> languageslist;
    private TableLayout interestsLayoutProfile, languagesLayoutProfile, mybasicsLayoutProfile, mylifestyleLayoutProfile;
    SizeBasedOnDensity sizeBasedOnDensity;
    List<String> images;
    List<String> interestsList;
    private GetTrackTask getTrackTask;
    ViewPager2 viewPager;
    String artisrName,songName;
    private ImageButton pauseButton,playButton;
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "857b49fc34f84d8a91f7baf4faa63acd";
    private static final String REDIRECT_URI = "editprofile:/callback";
    private static final String CLIENT_SECRET = "6fad0bc190fc484cbf8bb7bc734e23b1";

    private ParseUser otherUserProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setTitle(null);



        sizeBasedOnDensity = new SizeBasedOnDensity(this);
        ButtonCreator buttonCreator = new ButtonCreator(this, new ArrayList<>());

        viewPager = findViewById(R.id.view_pager_profile_page);

        //toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        backbtn = toolbar.findViewById(R.id.btnBackToolbar);
        backbtn.setVisibility(View.VISIBLE);
        backbtn.setOnClickListener(this);
        TextView toolbarTxt = toolbar.findViewById(R.id.toolbar_txt);
        toolbarTxt.setText(R.string.editProfileToolbarTxt);




        //Spotify song play/pause
        playButton = findViewById(R.id.playbtn);
        playButton.setOnClickListener(this);
        pauseButton = findViewById(R.id.pausebtn);
        pauseButton.setOnClickListener(this);
        pauseButton.setVisibility(View.GONE);


        //textviews
        tvName = findViewById(R.id.tvName);
        tvAge = findViewById(R.id.tvAge);
        tvBio = findViewById(R.id.tvBio);
        tital_at_company = findViewById(R.id.title_at_company);
        myGender_N_pronouns = findViewById(R.id.my_gender_N_pronouns);
        where_i_live = findViewById(R.id.lives_in);

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

        //get profile of card swiped up
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null)
         otherUserProfile = bundle.getParcelable("user");


        boolean iscurrentUser = otherUserProfile == null;


        ParseQuery<ParseModel> query = ParseModel.getQuery(iscurrentUser);

        if(!iscurrentUser){
            query.whereEqualTo("userclasspointer", otherUserProfile);
        }


        query.getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    if (parseModel.getInterests() != null)
                        interestsList = parseModel.getInterests();
                    if (parseModel.getLanguages() != null)
                        languageslist = parseModel.getLanguages();


                    artisrName = parseModel.getArtistName();
                    songName = parseModel.getSongName();
                    List<String> myLifeStyle = new ArrayList<>();

                    if (parseModel.getWorkout() != null) {
                        List<String> wl = new ArrayList<>(parseModel.getWorkout());
                        myLifeStyle.addAll(wl);
                    }

                    if (parseModel.getPets() != null)
                        myLifeStyle.add(parseModel.getPets());
                    if (parseModel.getSmoking() != null)
                        myLifeStyle.add(parseModel.getSmoking());
                    if (parseModel.getDrinking() != null)
                        myLifeStyle.add(parseModel.getDrinking());
                    if (parseModel.getDietary() != null)
                        myLifeStyle.add(parseModel.getDietary());
                    if (parseModel.getSocialMedia() != null)
                        myLifeStyle.add(parseModel.getSocialMedia());
                    if (parseModel.getKids() != null)
                        myLifeStyle.add(parseModel.getKids());


                    List<String> mybasics = new ArrayList<>();

                    if (parseModel.getRelationshipGoals() != null)
                        mybasics.add(parseModel.getRelationshipGoals());
                    if (parseModel.getReligion() != null)
                        mybasics.add(parseModel.getReligion());
                    if (parseModel.getStarSign() != null)
                        mybasics.add(parseModel.getStarSign());
                    if (parseModel.getHeight() != null)
                        mybasics.add(parseModel.getHeight());


// Convert the RealmList to an array of strings

                    if (interestsList != null) {
                        String[] interestsArray = interestsList.toArray(new String[0]);
                        buttonCreator.buttonCreator(interestsLayoutProfile, ProfilePage.this, "", interestsArray);
                    }
                    if (myLifeStyle.size() > 0)
                        buttonCreator.buttonCreator(mylifestyleLayoutProfile, ProfilePage.this, "", myLifeStyle.toArray(new String[0]));

                    if (mybasics.size() > 0)
                        buttonCreator.buttonCreator(mybasicsLayoutProfile, ProfilePage.this, "", mybasics.toArray(new String[0]));

                    if (languageslist != null) {
                        String[] languagesArray = languageslist.toArray(new String[0]);
                        buttonCreator.buttonCreator(languagesLayoutProfile, ProfilePage.this, "languages", languagesArray);
                    }
                     images = new ArrayList<>();
                    if (parseModel.getImage1Data() != null) {
                        images.add(parseModel.getImage1Data().getUrl());
                    }
                    if (parseModel.getImage2Data() != null) {
                        images.add(parseModel.getImage2Data().getUrl());
                    }
                    if (parseModel.getImage3Data() != null) {
                        images.add(parseModel.getImage3Data().getUrl());
                    }

                    ImageAdapter adapter = new ImageAdapter(images,ProfilePage.this);
                    viewPager.setAdapter(adapter);


                    ProgressBar progressBar = findViewById(R.id.image_profile_progressBar);
                    progressBar.setProgress(10);

                    viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            int totalItems = adapter.getItemCount();
                            int progress = (int) ((position + 1) / (float) totalItems * 100);
                            progressBar.setProgress(progress);
                        }


                    });





                    String name = parseModel.getName() + ", ";
                    tvName.setText(name);

                    if (parseModel.getAge() > 0) {
                        String age = parseModel.getAge() + "";
                        tvAge.setText(age);
                    }
                    String bio = parseModel.getBio();
                    tvBio.setText(bio);


                    //where i live/hometown string
                    String whereilive = (parseModel.getWhereILive().length()>1&&parseModel.getHometown().length()>1)
                            ? "I live in " + parseModel.getWhereILive() + " i'm from " + parseModel.getHometown()
                            : (parseModel.getWhereILive().length()>1) ? "I live in " + parseModel.getWhereILive()
                            : (parseModel.getHometown().length()>1) ? "I'm from " + parseModel.getHometown()
                            : null;


                    if(whereilive!=null){
                        where_i_live.setText(whereilive);
                    }else {
                        where_i_live.setVisibility(View.GONE);
                    }



                    //gender and pronounds string
                    String gender_N_pronouns = (parseModel.getGender().length()>1&&parseModel.getPronouns().size()>1)
                            ? "I am a " + parseModel.getGender() + " my pronouns are " + parseModel.getPronouns()
                            : (parseModel.getGender()!=null) ? "I am a " + parseModel.getGender()
                            : (parseModel.getPronouns()!=null) ? "My pronouns are " + parseModel.getPronouns()
                            : null;

                    if(gender_N_pronouns!=null)
                        myGender_N_pronouns.setText(gender_N_pronouns);
                }else {
                    myGender_N_pronouns.setVisibility(View.GONE);
                }



                String title_or_fofStudy = parseModel.getJobTitleFieldOfStudy() + " at " + parseModel.getCompanyRcolledge();

                if (parseModel.getCompanyRcolledge().length() >= 1 || parseModel.getJobTitleFieldOfStudy().length() >= 1) {
                    tital_at_company.setText(title_or_fofStudy);
                } else {
                    tital_at_company.setVisibility(View.GONE);
                }

            }

        });


    }

    @Override
    protected void onPause() {
        if(getTrackTask!=null) {
            getTrackTask.getMediaPlayer().pause();
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
        }
        super.onPause();
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
        if (viewId == R.id.btnBackToolbar) {
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