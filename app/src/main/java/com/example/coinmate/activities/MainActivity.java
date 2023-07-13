package com.example.coinmate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.coinmate.Domain.Comic;
import com.example.coinmate.api.Api;
import com.example.coinmate.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api.getInstance(this);

//        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
//        itemViewModel.getComics().observe(this, item -> {
//            Log.i("MAIN ACT", "ITEM: " + item.getValue());
//        });
//        itemViewModel.setComics(fetchComics());
//        fetchComics();

        com.example.coinmate.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private MutableLiveData<ArrayList<Comic>> fetchComics() {
        MutableLiveData<ArrayList<Comic>> comicsArr = new MutableLiveData<>();
        Api.getInstance().someGetRequestReturningComics(response -> {
            Log.i("ADAPT", ""+response.toString());
            ArrayList<Comic> data = new ArrayList<>(new ArrayList<>());
            JSONArray jsonArray = response.getJSONObject("data").getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                Log.i("JSON", "PARSING: " + jo);
                String title = jo.getString("title");
                String id = String.valueOf(jo.getInt("id"));
                String description = jo.getString("description");
                JSONObject imgObject = jo.getJSONObject("thumbnail");
                String img = imgObject.getString("path") + imgObject.getString("extension");
//                    Log.i("ADAPT: ", "" + title);
                data.add(new Comic(id+"", title, description, img));
            }
            Log.i("DAAA ", "data " + data.toString());
            comicsArr.setValue(data);
        });
        Log.i("EEED", "COMICS ARRRRRR " + comicsArr.toString());
//        itemViewModel.setComics(comicsArr);

        return comicsArr;
    }

}

