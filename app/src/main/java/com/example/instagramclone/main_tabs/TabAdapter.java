package com.example.instagramclone.main_tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.instagramclone.usertab_cardview_adapter.UsersTab;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {

        switch (tabPosition){
            case 0:

                return new UsersTab();
            case 1:
                return new SharePictureTab();

            case 2:
                return new ProfileTab();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "Users";


            case 1:
                return "Share Picture";

            case 2:
            return "Profile";

            default:
                return null;

        }
    }


}
