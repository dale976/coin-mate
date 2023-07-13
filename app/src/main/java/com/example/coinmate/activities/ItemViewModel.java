package com.example.coinmate.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinmate.Domain.Comic;

import java.util.ArrayList;
import java.util.List;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Comic>> comics;

    public void setComics(MutableLiveData<ArrayList<Comic>> comicsArray){
        comics.setValue(comicsArray.getValue());
    }

    public MutableLiveData<ArrayList<Comic>> getComics() {
        if (comics == null) {
            comics = new MutableLiveData<>();
//            loadComics();
        }
        return comics;
    }

    private void loadComics() {
        ArrayList<Comic> dummyComics = new ArrayList<>();
        dummyComics.add(new Comic("1234", "some title", "foobar", "somepath"));
        comics.setValue(dummyComics);
    }
}
