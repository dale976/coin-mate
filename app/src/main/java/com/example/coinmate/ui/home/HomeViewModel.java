package com.example.coinmate.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.coinmate.Domain.Comic;
import com.example.coinmate.Domain.Wallet;
import com.example.coinmate.activities.ItemViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
//    private MutableLiveData<ArrayList<Comic>> mComics;
    private MutableLiveData<ArrayList<String>> mString = new MutableLiveData<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Today's top coins");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<ArrayList<String>> getStrings() {
        if (mString == null) {
            mString = new MutableLiveData<>(new ArrayList<>());
            loadStrings();
        }
        return mString;
    }

    public void setStrings(ArrayList<String> strings) {
        mString.setValue(strings);
    }
    private void loadComics() {
        ArrayList<Comic> newComics = new ArrayList<>();
//        newComics.add(new Comic("1234", "some title", "foobar", "somepath"));
//        mComics.setValue(newComics);
    }

    private void loadStrings() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("string 1");
        strings.add("string 2");
        strings.add("string 3");
        mString.setValue(strings);
    }
}