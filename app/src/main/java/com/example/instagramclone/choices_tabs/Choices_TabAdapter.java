package com.example.instagramclone.choices_tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.instagramclone.choices_tabs.tabs.Interests_tab;
import com.example.instagramclone.choices_tabs.tabs.My_Basics_tab;
import com.example.instagramclone.choices_tabs.tabs.My_Lifestyle_tab;
import com.example.instagramclone.choices_tabs.tabs.SexualOrientation_tab;
import com.example.instagramclone.choices_tabs.tabs.Where_i_Live_tab;
import com.example.instagramclone.choices_tabs.tabs.languages_tab;

public class Choices_TabAdapter extends FragmentStateAdapter {

    public Choices_TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int tabPosition) {
        switch (tabPosition) {
            case 0:
                return new Interests_tab();
            case 1:
                return new Where_i_Live_tab();
            case 2:
                return new SexualOrientation_tab();
            case 3:
                return new languages_tab();
            case 4:
                return new My_Basics_tab();
            case 5:
                return new My_Lifestyle_tab();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }



}
