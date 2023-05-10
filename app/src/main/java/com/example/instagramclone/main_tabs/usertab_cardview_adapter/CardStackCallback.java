package com.example.instagramclone.main_tabs.usertab_cardview_adapter;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.DiffUtil;

import com.example.instagramclone.main_tabs.ItemModel;

public class CardStackCallback extends DiffUtil.Callback {

    private final List<ItemModel> old, baru;

    public CardStackCallback(List<ItemModel> old, List<ItemModel> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(old.get(oldItemPosition).getImage1(), baru.get(newItemPosition).getImage1());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}