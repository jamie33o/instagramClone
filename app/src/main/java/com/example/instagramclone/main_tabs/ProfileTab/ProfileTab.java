package com.example.instagramclone.main_tabs.ProfileTab;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProfileTab extends Fragment implements View.OnClickListener {

    ButtonCreator buttonCreator;

    public ImageView profileImgView,editProfilebtn;

    String image1;

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
                        ParseFile img1 = parseModel.getImage1Data();
                        img1.getFileInBackground(new GetFileCallback() {
                            @Override
                            public void done(File file, ParseException e) {
                                piccassoLoadToImageView.getImageNloadIntoImageview(file.getAbsolutePath(), profileImgView, sizeBasedOnDensity.widthRatio(220), sizeBasedOnDensity.heightRatio(220), 300);
                            }
                        });

                    }
            }
            }
        });

        pricesModelListProfilepg = new ArrayList<>();

        pricesModelListProfilepg.add(new PricesModel("Popular","1 week","€5.99","10% off"));
        pricesModelListProfilepg.add(new PricesModel("Best value","1 Month","€15.99","20% off"));
        pricesModelListProfilepg.add(new PricesModel("Bargain","1 Year","€130","50% off"));





        View dot1 = view.findViewById(R.id.dot1);
        View dot2 = view.findViewById(R.id.dot2);
        View dot3 = view.findViewById(R.id.dot3);


           List<View> dots = new ArrayList<>();
           dots.add(dot1);
           dots.add(dot2);
           dots.add(dot3);
        dots.get(0).setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_corner_shape_red,null));

        // Initialize ViewPager and set up PagerAdapter
        viewPager = view.findViewById(R.id.viewPager2);
        PricesAdapter pagerAdapter = new PricesAdapter(getContext(),pricesModelListProfilepg,null);
        viewPager.setAdapter(pagerAdapter);

        // Set up auto-scrolling using ScheduledExecutorService
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (currentPage == NUM_PAGES - 1) {
                        currentPage = 0;
                    } else {
                        currentPage++;
                    }

                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dots.get(currentPage == 0 ? 2 : currentPage - 1).setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.rounded_coner_shape_grey, null));
                            dots.get(currentPage).setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.rounded_corner_shape_red, null));
                            viewPager.setCurrentItem(currentPage, true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, AUTO_SCROLL_DELAY, TimeUnit.MILLISECONDS);





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