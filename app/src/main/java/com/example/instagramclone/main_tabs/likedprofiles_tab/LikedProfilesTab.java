package com.example.instagramclone.main_tabs.likedprofiles_tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.instagramclone.main_tabs.ItemModel;
import com.example.instagramclone.main_tabs.ProfileTab.edit_profile.QueriesEditProfile;
import com.example.instagramclone.main_tabs.usertab_cardview_adapter.QueryForCardView;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.reusable_code.ParseUtils.UtilsClass;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.ArrayList;
import java.util.List;

public class LikedProfilesTab extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerViewLikedProfiles,liked_you_recyclerview;
    private LikedProfilesAdapter adapterLikedProfile;
    private Profiles_liked_you_back_Adapter profiles_liked_you_back_adapter;

    private List<ItemModel> likedProfileModelList,likedYouBackList;

    public TextView peopleYouLikedTV,peopleWhoLikedYouTV;

    private QueryForCardView queryForCardView;

    public LikedProfilesTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.liked_profiles_tab, container, false);

        peopleWhoLikedYouTV = view.findViewById(R.id.liked_you_back_tv);
        peopleYouLikedTV = view.findViewById(R.id.you_liked_tv);



        likedProfileModelList = new ArrayList<>();
        // Get a reference to the RecyclerView
        recyclerViewLikedProfiles = view.findViewById(R.id.likedProfiles_recyclerview);
        // Create a new instance of the adapter and set it on the RecyclerView
        adapterLikedProfile = new LikedProfilesAdapter(getContext(), likedProfileModelList);
        // Set the layout manager on the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLikedProfiles.setLayoutManager(layoutManager);
        recyclerViewLikedProfiles.setAdapter(adapterLikedProfile);

        //snaphelper get view to snap to view
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewLikedProfiles);


        likedYouBackList = new ArrayList<>();
        // Get a reference to the 2nd RecyclerView
        liked_you_recyclerview = view.findViewById(R.id.liked_you_recyclerview);
        // Create a new instance of the 2nd adapter and set it on the 2nd RecyclerView
        profiles_liked_you_back_adapter = new Profiles_liked_you_back_Adapter(getContext(), likedYouBackList);
        LinearLayoutManager layoutManagerliked_you = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        liked_you_recyclerview.setLayoutManager(layoutManagerliked_you);
        liked_you_recyclerview.setAdapter(profiles_liked_you_back_adapter);
        SnapHelper snapHelperLikedYouBack = new LinearSnapHelper();
        snapHelperLikedYouBack.attachToRecyclerView(liked_you_recyclerview);


         queryForCardView = new QueryForCardView(getContext());

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        queryForCardView.queryForLikedProfiles(likedProfileModelList,adapterLikedProfile,this);
        queryForCardView.queryUserLikedMe(likedYouBackList,profiles_liked_you_back_adapter,this);
        super.onResume();
    }




    }
