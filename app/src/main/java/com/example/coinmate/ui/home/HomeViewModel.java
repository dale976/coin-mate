package com.example.coinmate.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinmate.ui.common.listItem.ListItem;

import java.util.ArrayList;
import java.util.Collection;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<ArrayList> mItems;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment where is the list???");

        mItems = new MutableLiveData<ArrayList>();
        mItems.setValue(generateListItems());

    }

    private ArrayList<ListItem> generateListItems() {
        ArrayList items = new ArrayList<ListItem>();
        for (int i = 0; i < 5; i++) {
            ListItem item = new ListItem();
            item.name = "Some Coin Name " + i;
            item.id = i;
            items.add(item);
        }
        return items;
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList> getListItems() { return mItems; }
}