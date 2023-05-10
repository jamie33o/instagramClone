package com.example.instagramclone.main_tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.instagramclone.main_tabs.ProfileTab.ProfileTab;
import com.example.instagramclone.main_tabs.likedprofiles_tab.MessagesTab;
import com.example.instagramclone.main_tabs.usertab_cardview_adapter.UsersTab;
public class TabAdapter extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new UsersTab();
            case 1:
                return new MessagesTab();

            case 2:
                return new ProfileTab();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }



}
