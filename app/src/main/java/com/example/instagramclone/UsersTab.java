package com.example.instagramclone;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

//import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

public class UsersTab extends Fragment {

    private CardStackLayoutManager manager;
    CardStackAdapter adapter;
    List<ItemModel> items;
    List<ItemModel> initialItems;

    View view;

    public UsersTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        items = new ArrayList<>();
        //initialItems = new ArrayList<>();

        init(view);

        QueryDatabase queryDatabase = new QueryDatabase(getContext());
        queryDatabase.getQueryDatabaseCardview(this);

        return view;
    }


    public void init(View view) {
        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {

            @Override
            public void onCardDragging(Direction direction, float ratio) {
                // Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {

                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right) {
                    Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top) {
                    Toast.makeText(getContext(), "Direction Top", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left) {
                    Toast.makeText(getContext(), "Direction Left", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom) {
                    Toast.makeText(getContext(), "Direction Bottom", Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                /*System.out.println( "onCardAppeared: " + position + ", name: " + tv.getText());
                System.out.println("position="+position+"\n"+ "itemS" +items.size() );
*/

                if (position == items.size() - 1) {
                   // clearItems();


                    pages();

                  //  newItems();
                    Intent intent = new Intent(getContext(), MatchChoices.class);
                    startActivity(intent);//todo create pop up so they can change choices to get more
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
        adapter = new CardStackAdapter(items);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    void pages() {
        System.out.println("pages called");
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(items);
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult results = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        results.dispatchUpdatesTo(adapter);


    }

  /*  public void newItems(){
        adapter.setItems(initialItems);
    }*/


    public void setNewItems(List<ItemModel> newItems) {
        adapter.setItems(newItems);
    }

    // Method to clear the current items in the adapter
    public void clearItems() {
        adapter.clearItems();
    }






public void addList(String image,String name,String age,String county) {

        items.add(new ItemModel(image,name,age,county));
       // initialItems.add(new ItemModel(image,name,age,county));

    }

}
