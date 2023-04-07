package com.example.instagramclone.choices_tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class Choices_TabAdapter extends FragmentPagerAdapter {

    int current_tab_count;
    public Choices_TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {

        switch (tabPosition){
            case 0:
                current_tab_count=1;
                return new Interests_tab();

            case 1:
                current_tab_count=2;

                return new Where_i_Live_tab();

            case 2:
                current_tab_count=3;
                return new SexualOrientation_tab();

            case 3:
                current_tab_count=4;
                return new languages_tab();

            case 4:
                current_tab_count=5;
                return new My_Basics_tab();

            case 5:
                current_tab_count=6;
                return new My_Lifestyle_tab();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 6;
    }

    public int getCurrent_tab_count() {
        return current_tab_count;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();


    }

    public void setCurrentTab(int tabPosition) {
        current_tab_count = tabPosition;
        notifyDataSetChanged();
    }


}
