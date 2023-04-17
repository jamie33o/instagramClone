package com.example.instagramclone.main_tabs.ProfileTab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instagramclone.braintree_payment.PricesAdapter;
import com.example.instagramclone.braintree_payment.PricesModel;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.main_tabs.ProfileTab.edit_profile.EditProfile;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;
import com.example.instagramclone.reusable_code.SizeBasedOnDensity;
import com.example.instagramclone.reusable_code.PiccassoLoadToImageView;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.R;
import com.parse.GetCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class ProfileTab extends Fragment implements View.OnClickListener {

    ButtonCreator buttonCreator;

    public ImageView profileImgView,editProfilebtn;

    String image1;
    private LinearLayout dotsLayout;
    private View dot1, dot2, dot3;

    private ViewPager2 viewPager;
    private int currentPage = 0;
    private static final int NUM_PAGES = 3; // Number of pages in the ViewPager
    private static final int AUTO_SCROLL_DELAY = 4000; // Delay in milliseconds for auto-scrolling

    List<PricesModel> pricesModelListProfilepg;


    public ProfileTab() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                container, false);



        buttonCreator = new ButtonCreator(getContext(),new ArrayList<>());

        SizeBasedOnDensity sizeBasedOnDensity = new SizeBasedOnDensity(view.getContext());
        editProfilebtn = view.findViewById(R.id.editProfileBtn);
        editProfilebtn.setOnClickListener(this);


        profileImgView = view.findViewById(R.id.profileImgView);

        profileImgView.setOnClickListener(this);

        PiccassoLoadToImageView piccassoLoadToImageView = new PiccassoLoadToImageView(getContext());

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if (parseModel.getImage1Data() != null) {
                        image1 = parseModel.getImage1Name();
                    }
                    piccassoLoadToImageView.getImageNloadIntoImageview(profileImgView, image1, "", sizeBasedOnDensity.widthRatio(220), sizeBasedOnDensity.heightRatio(220), 300);

                }

            }
        });

        pricesModelListProfilepg = new ArrayList<>();

        pricesModelListProfilepg.add(new PricesModel("Popular","1 week","€5.99","10% off"));
        pricesModelListProfilepg.add(new PricesModel("Best value","1 Month","€15.99","20% off"));
        pricesModelListProfilepg.add(new PricesModel("Bargain","1 Year","€130","50% off"));




        dotsLayout = view.findViewById(R.id.dotsLayout);

        dot1 =  view.findViewById(R.id.dot1);
        dot2 =  view.findViewById(R.id.dot2);
        dot3 =  view.findViewById(R.id.dot3);

        // Set initial state

           dot1.setBackgroundColor(getResources().getColor(R.color.purple_200));;
           dot2.setBackgroundColor(getResources().getColor(R.color.purple_200));
           dot3.setBackgroundColor(getResources().getColor(R.color.purple_200));

           List<View> dots = new ArrayList<>();
           dots.add(dot1);
           dots.add(dot2);
           dots.add(dot3);

        // Initialize ViewPager and set up PagerAdapter
        viewPager = view.findViewById(R.id.viewPager2);
        PricesAdapter pagerAdapter = new PricesAdapter(getContext(),pricesModelListProfilepg);
        viewPager.setAdapter(pagerAdapter);

        // Set up auto-scrolling using Handler
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                dots.get(currentPage == 0 ? 2 : currentPage-1).setBackgroundColor(getResources().getColor(R.color.purple_200));

                dots.get(currentPage).setBackgroundColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, AUTO_SCROLL_DELAY);
            }
        };
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY);





    return view;//must return type view

    }




    @Override
    public void onClick(View view) {//for uploading image


        int viewId = view.getId();
        if(viewId == viewPager.getId()){
            viewPager.setUserInputEnabled(true);
        }

        if (viewId == R.id.editProfileBtn ) {
            Intent intent = new Intent(getContext(), EditProfile.class);
            startActivity(intent);

        }

        if (viewId == R.id.profileImgView) {

            Intent intent = new Intent(getContext(), ProfilePage.class);
            startActivity(intent);
        }


    }

}