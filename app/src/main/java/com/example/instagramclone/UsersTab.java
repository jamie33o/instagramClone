package com.example.instagramclone;

import static android.content.ContentValues.TAG;

import static com.parse.Parse.getApplicationContext;

import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
//import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

//import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.parse.ParseUser;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

public class UsersTab extends Fragment {

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    List<ItemModel> items;
    int counter = 0;
    ItemModel item;
    int itemsLeft;
    View view;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        queryDatabase();
        items = new ArrayList<>();


        return view;
    }


    private void init(View view) {
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
                    pages();

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
                    Intent intent = new Intent(getActivity().getApplication(), MatchChoices.class);
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

    private void pages() {
        System.out.println("pages called");
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(items);
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult results = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        results.dispatchUpdatesTo(adapter);


    }


    public void queryDatabase() {

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Profile");
        parseQuery.orderByDescending("createdAt");

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject post : objects) {

                        String name = post.get("profileName") + "";
                        String age = post.get("age") + "";
                        String county = post.get("county") + "";


                        ParseFile postPicture = (ParseFile) post.get("image1");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null) {
                                    counter++;
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    File file = new File(getApplicationContext().getCacheDir(), counter + ".jpg");
                                    String filePath = file.getAbsolutePath();
                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(filePath);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    } catch (FileNotFoundException a) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            fos.close();
                                        } catch (IOException a) {
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.println("items added");
                                    item = new ItemModel(filePath, name, age, county);
                                    items.add(item);
                                    itemsLeft = items.size();

                                    if (items.size() == objects.size())
                                        init(view);

                                }
                                dialog.dismiss();
                            }
                        });
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

   /* private List<ItemModel> addList() {
        System.out.println("add list called");

        items.add(new ItemModel("/data/user/0/com.example.instagramclone/cache/2.jpg","jgjg","hggf","FGhhgf"));



        return items;

    }*/

}
