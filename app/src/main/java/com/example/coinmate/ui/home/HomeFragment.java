package com.example.coinmate.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinmate.Adapter.ComicAdapter;
import com.example.coinmate.Domain.Comic;
import com.example.coinmate.activities.ItemViewModel;
import com.example.coinmate.api.Api;
import com.example.coinmate.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final MutableLiveData<ArrayList<Comic>> comicsState = new MutableLiveData<>();
    private ComicAdapter comicAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.listHeader;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final RecyclerView recyclerView = binding.itemList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        ItemViewModel itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        final Observer<ArrayList<Comic>> comicsObserver = comics -> {
            if (comicsState.getValue() == null) {
                comicsState.setValue(comics);
                comicAdapter = new ComicAdapter(comics);
                recyclerView.setAdapter(comicAdapter);
            } else {
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                    @Override
                    public int getOldListSize() {
                        return comicsState.getValue().size();
                    }

                    @Override
                    public int getNewListSize() {
                        return comics.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return Objects.equals(comicsState.getValue().get(oldItemPosition).getId(), comics.get(newItemPosition).getId());
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        Comic oldComic = comicsState.getValue().get(oldItemPosition);
                        Comic newComic = comics.get(newItemPosition);
                        return newComic.equals(oldComic);
                    }
                });

                result.dispatchUpdatesTo(comicAdapter);
                comicsState.setValue(comics);
            }
        };


        try {
            itemViewModel.getComics().observe(getViewLifecycleOwner(), comicsObserver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Api.getInstance().someGetRequestReturningComics(response -> {
            ArrayList<Comic> data = new ArrayList<>(new ArrayList<>());
            JSONArray jsonArray = response.getJSONObject("data").getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String title = jo.getString("title");
                String id = String.valueOf(jo.getInt("id"));
                String description = jo.getString("description");
                JSONObject imgObject = jo.getJSONObject("thumbnail");
                String img = imgObject.getString("path") + "." + imgObject.getString("extension");
                data.add(new Comic(id, title, description, img));
            }
            MutableLiveData<ArrayList<Comic>> list = new MutableLiveData<>();
            list.setValue(data);
            itemViewModel.setComics(list);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

