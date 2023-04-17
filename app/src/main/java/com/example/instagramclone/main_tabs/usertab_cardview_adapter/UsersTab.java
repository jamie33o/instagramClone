package com.example.instagramclone.main_tabs.usertab_cardview_adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


import android.util.Log;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;
import com.example.instagramclone.reusable_code.SearchPopUp;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

public class UsersTab extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE = 123;
    // boolean startPages;
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private List<ItemModel> items;
    private QueryForCardView queryForCardView;
    private View view;

    public Button searchPopUpBtn;
    private ImageButton dislike, like, rewind;

    static List<ParseUser> likedCards;
    private List<ParseUser> dislikedCards;
    private List<ParseUser> cardsToViewLater;

    public UsersTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        items = new ArrayList<>();

        dislikedCards = new ArrayList<>();
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    if(parseModel.getLikedUsers()!=null) {
                        likedCards = new ArrayList<>(parseModel.getLikedUsers());
                    }else {
                        likedCards = new ArrayList<>();

                    }
                }
                }
            });
        init(view);

        cardsToViewLater = new ArrayList<>();
        dislike = view.findViewById(R.id.dislike);
        dislike.setOnClickListener(this);
        rewind = view.findViewById(R.id.rewind);
        rewind.setOnClickListener(this);
        like = view.findViewById(R.id.like);
        like.setOnClickListener(this);
        searchPopUpBtn = view.findViewById(R.id.search_pop_up);
        searchPopUpBtn.setOnClickListener(this);

        queryForCardView = new QueryForCardView(getContext());
        queryForCardView.getQueryForCardView(items,adapter,this);




        return view;
    }


    public void init(View view) {

        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(view.getContext(), new CardStackListener() {

            @Override
            public void onCardDragging(Direction direction, float ratio) {

                if (direction.name().equals("Left")) {
                    dislike.setImageResource(R.drawable.xnooutline);
                } else {
                    dislike.setImageResource(R.drawable.xred);

                }
                if (direction.name().equals("Right")) {
                    like.setImageResource(R.drawable.heartwhitenooutline);
                } else {
                    like.setImageResource(R.drawable.heartgreen);

                }


            }

            @Override
            public void onCardSwiped(Direction direction) {

                if (direction == Direction.Right) {
                    likedCards.add(adapter.getItems().get(manager.getTopPosition() - 1).getUserClassPointer());

                }
                if (direction == Direction.Top) {
                   // cardsToViewLater.add(adapter.getItems().get(manager.getTopPosition() - 1).getUserClassPointer());
                }
                if (direction == Direction.Left) {
                    //dislikedCards.add(adapter.getItems().get(manager.getTopPosition()).getUserClassPointer());
                }
                if (direction == Direction.Bottom) {


                }

                // adding pages??
                if (manager.getTopPosition() == adapter.getItemCount()) {

                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                like.setImageResource(R.drawable.heartgreen);
                dislike.setImageResource(R.drawable.xred);
                //System.out.println( "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                like.setImageResource(R.drawable.heartgreen);
                dislike.setImageResource(R.drawable.xred);
                if (position == items.size() - 1) {
                    // pages();
                  searchPopUp();

                }
            }
        });
        manager.setStackFrom(StackFrom.Top);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(new DecelerateInterpolator())
                .build();
        manager.setRewindAnimationSetting(setting);
        adapter = new CardStackAdapter(items);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());


    }

    void pages() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> newItems = new ArrayList<>(items);
        CardStackCallback callback = new CardStackCallback(old, newItems);
        DiffUtil.DiffResult results = DiffUtil.calculateDiff(callback);
        results.dispatchUpdatesTo(adapter);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Reload the activity here
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    queryForCardView.getQueryForCardView(items,adapter,UsersTab.this);

                }
            },1000);


        }
    }


    public void searchPopUp() {
        saveLikedProfileCards(view.getContext());
        searchPopUpBtn.setVisibility(View.VISIBLE);

        Intent intent = new Intent(getContext(), SearchPopUp.class);
        intent.putExtra("cardview", "cardview");
        startActivityForResult(intent, REQUEST_CODE);

    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == searchPopUpBtn.getId()){
            searchPopUp();
        }

        if (id == dislike.getId()) {
            dislikedCards.add(adapter.getItems().get(manager.getTopPosition()).getUserClassPointer());
            adapter.notifyItemRemoved(manager.getTopPosition());

        } else if (id == like.getId()) {
            likedCards.add(adapter.getItems().get(manager.getTopPosition()).getUserClassPointer());

            adapter.notifyItemRemoved(manager.getTopPosition());

        } else if (id == rewind.getId()) {

            // adapter.notifyItemInserted(manager.getTopPosition());
            adapter.notifyItemMoved(manager.getTopPosition() - 1, manager.getTopPosition());


            //cardStackView.swipe();
        }
    }

    public static void saveLikedProfileCards(Context context) {


        if (likedCards != null) {
            ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
                @Override
                public void done(ParseModel parseModel, ParseException e) {
                    if (e == null) {

                        parseModel.setLikedUsers(likedCards);
                        parseModel.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                            }
                        });


                    }

                }


            });
        }
    }

}

