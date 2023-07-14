package com.example.coinmate.activities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coinmate.Domain.Comic;

import java.io.IOException;
import java.util.ArrayList;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Comic>> comics;

    public void setComics(MutableLiveData<ArrayList<Comic>> comicsArray){
        comics.setValue(comicsArray.getValue());
    }

    public MutableLiveData<ArrayList<Comic>> getComics() throws IOException {
        if (comics == null) {
            comics = new MutableLiveData<>();
        }
        return comics;
    }
}
