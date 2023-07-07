package com.example.coinmate.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinmate.Domain.Wallet;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final ArrayList<Wallet> mWallets;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Today's top coins");

        mWallets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mWallets.add(new Wallet("coin name " + i));
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
    public ArrayList<Wallet> getItems() { return mWallets; }
}