package com.example.instagramclone.main_tabs.ProfileTab.profile_page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.DotsIndicator;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.neovisionaries.i18n.CountryCode;
import com.parse.GetCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
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
    private List<String> languageslist;
    private TableLayout interestsLayoutProfile, languagesLayoutProfile, mybasicsLayoutProfile, mylifestyleLayoutProfile;
    private List<String> images;
    private ImageAdapter adapter;

    private List<String> interestsList;
    private GetTrackTask getTrackTask;
    private ViewPager2 viewPager;
    private String artisrName,songName;
    private ImageButton pauseButton,playButton;
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "857b49fc34f84d8a91f7baf4faa63acd";
    private static final String REDIRECT_URI = "editprofile:/callback";
    private int screenWidth;
    private ParseUser otherUserProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setTitle(null);

        //used to set the images in image adapter to full screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;

        ButtonCreator buttonCreator = new ButtonCreator(this, new ArrayList<>());

        viewPager = findViewById(R.id.view_pager_profile_page);


        backbtn = findViewById(R.id.back_btn);
        backbtn.setVisibility(View.VISIBLE);
        backbtn.setOnClickListener(this);


        //dots to show which image user is on
        View dot1 = findViewById(R.id.dot1);
        dot1.setVisibility(View.GONE);
        View dot2 = findViewById(R.id.dot2);
        dot2.setVisibility(View.GONE);
        View dot3 = findViewById(R.id.dot3);
        dot3.setVisibility(View.GONE);
        List<View> dots = new ArrayList<>();



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


                    if(parseModel.getArtistName() != null) {
                        artisrName = parseModel.getArtistName();
                        songName = parseModel.getSongName();
                        AuthorizationClient.openLoginActivity(ProfilePage.this, REQUEST_CODE, request);
                    }else{
                        View songLayout = findViewById(R.id.songLayout);
                        songLayout.setVisibility(View.GONE);
                    }

                    List<String> myLifeStyle = new ArrayList<>();

                    if (parseModel.getWorkout() != null) {
                        List<String> wl = new ArrayList<>(parseModel.getWorkout());
                        myLifeStyle.addAll(wl);
                    }

                    if (parseModel.getPets()!= null && !parseModel.getPets().equals(""))
                        myLifeStyle.add(parseModel.getPets());
                    if (parseModel.getSmoking()!= null && !parseModel.getSmoking().equals(""))
                        myLifeStyle.add(parseModel.getSmoking());
                    if (parseModel.getDrinking()!= null && !parseModel.getDrinking().equals(""))
                        myLifeStyle.add(parseModel.getDrinking());
                    if (parseModel.getDietary()!= null && !parseModel.getDietary().equals(""))
                        myLifeStyle.add(parseModel.getDietary());
                    if (parseModel.getSocialMedia()!= null && !parseModel.getSocialMedia().equals(""))
                        myLifeStyle.add(parseModel.getSocialMedia());
                    if (parseModel.getKids()!= null && !parseModel.getKids().equals(""))
                        myLifeStyle.add(parseModel.getKids());


                    List<String> mybasics = new ArrayList<>();

                    if (parseModel.getRelationshipGoals()!= null && !parseModel.getRelationshipGoals().equals(""))
                        mybasics.add(parseModel.getRelationshipGoals());
                    if (parseModel.getReligion()!= null &&!parseModel.getReligion().equals(""))
                        mybasics.add(parseModel.getReligion());
                    if (parseModel.getStarSign()!= null &&!parseModel.getStarSign().equals(""))
                        mybasics.add(parseModel.getStarSign());
                    if (parseModel.getGender()!= null &&!parseModel.getGender().equals(""))
                        mybasics.add(parseModel.getGender());
                    if (parseModel.getHeight()!= null && !parseModel.getHeight().equals(""))
                        mybasics.add(parseModel.getHeight());



                    if (interestsList != null) {
                        String[] interestsArray = interestsList.toArray(new String[0]);
                        buttonCreator.buttonCreator(interestsLayoutProfile, ProfilePage.this, interestsArray);
                    }
                    if (myLifeStyle.size() > 0)
                        buttonCreator.buttonCreator(mylifestyleLayoutProfile, ProfilePage.this,  myLifeStyle.toArray(new String[0]));

                    if (mybasics.size() > 0)
                        buttonCreator.buttonCreator(mybasicsLayoutProfile, ProfilePage.this, mybasics.toArray(new String[0]));

                    if (languageslist != null) {
                        String[] languagesArray = languageslist.toArray(new String[0]);
                        buttonCreator.buttonCreator(languagesLayoutProfile, ProfilePage.this, languagesArray);
                    }
                     images = new ArrayList<>();
                    if (parseModel.getImage1Data() != null) {
                        ParseFile img1 = parseModel.getImage1Data();
                        img1.getFileInBackground(new GetFileCallback() {
                            @Override
                            public void done(File file, ParseException e) {
                                images.add(file.getAbsolutePath());
                                dot1.setVisibility(View.VISIBLE);

                                dots.add(dot1);

                            }
                        });
                    }
                    if (parseModel.getImage2Data() != null) {
                        ParseFile img1 = parseModel.getImage2Data();
                        img1.getFileInBackground(new GetFileCallback() {
                            @Override
                            public void done(File file, ParseException e) {
                                images.add(file.getAbsolutePath());
                                dot2.setVisibility(View.VISIBLE);

                                dots.add(dot2);

                            }
                        });                    }
                    if (parseModel.getImage3Data() != null) {
                        ParseFile img1 = parseModel.getImage3Data();
                        img1.getFileInBackground(new GetFileCallback() {
                            @Override
                            public void done(File file, ParseException e) {
                                images.add(file.getAbsolutePath());
                                dot3.setVisibility(View.VISIBLE);
                                dots.add(dot3);

                            }
                        });
                    }



                     adapter = new ImageAdapter(images,ProfilePage.this,screenWidth);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setAdapter(adapter);

                        }
                    },100);



                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new DotsIndicator(dots,ProfilePage.this,viewPager);

                        }
                    },300);






                    String name = parseModel.getName() + ", ";
                    tvName.setText(name);

                    if (parseModel.getAge() > 0 && !parseModel.getBoolShowAge()) {
                        String age = parseModel.getAge() + "";
                        tvAge.setText(age);
                    }
                    String bio = parseModel.getBio();
                    tvBio.setText(bio);


                    //where i live/hometown string

                    String whereilive = (parseModel.getWhereILive()!=null && parseModel.getHometown().length() > 1)
                                ? "I live in " + parseModel.getWhereILive() + " i'm from " + parseModel.getHometown()
                                : (parseModel.getWhereILive() != null) ? "I live in " + parseModel.getWhereILive()
                                : (parseModel.getHometown().length() > 1) ? "I'm from " + parseModel.getHometown()
                                : null;


                    if(whereilive!=null){
                        where_i_live.setText(whereilive);
                    }else {
                        where_i_live.setVisibility(View.GONE);
                    }



                    //gender and pronounds string
                    String gender_N_pronouns = (parseModel.getGender()!= null && parseModel.getPronouns().size()>0)
                            ? "I am a " + parseModel.getGender() + " my pronouns are " + parseModel.getPronouns()
                            : (parseModel.getGender()!=null) ? "I am a " + parseModel.getGender()
                            : (parseModel.getPronouns()!=null) ? "My pronouns are " + parseModel.getPronouns()
                            : null;

                    if(gender_N_pronouns!=null)
                        myGender_N_pronouns.setText(gender_N_pronouns);
                }else {
                    myGender_N_pronouns.setVisibility(View.GONE);
                }





                if ((parseModel.getCompanyRcolledge() != null && parseModel.getCompanyRcolledge().length() >= 1) || (parseModel.getJobTitleFieldOfStudy() != null && parseModel.getJobTitleFieldOfStudy().length() >= 1)) {
                    String title_or_fofStudy = parseModel.getJobTitleFieldOfStudy() + " at " + parseModel.getCompanyRcolledge();
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
        if (viewId == backbtn.getId()) {
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