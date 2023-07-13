package com.example.coinmate.ui.home;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

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
            Log.i("OBSERVER", "UPDATED COMICS " + comics.get(0).getTitle());
            Log.i("OBSERVER", "COMIC STATE " + comicsState.getValue());
            if (comicsState.getValue() == null) {
                Log.i("HOME FAG", "SETTING ADAPTER");
                comicsState.setValue(comics);
                comicAdapter = new ComicAdapter(comics);
                recyclerView.setAdapter(comicAdapter);
            } else {
                Log.i("HOME FAG", "ADAPTER CREATED ALREADY");
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
//                        return Objects.equals(comicsState.getValue().get(oldItemPosition).getId(), comics.get(newItemPosition).getId());
                        return false;
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                        Comic oldFav = comicsState.getValue().get(oldItemPosition).getTitle();
//                        Comic newFav = comics.get(newItemPosition).getTitle();
                        return false;
                    }
                });
//                result.dispatchUpdatesTo(Objects.requireNonNull(recyclerView.getAdapter()));
//                ArrayList<Comic> dummyComics = new ArrayList<>();
//                dummyComics.add(new Comic("zcad", "some title 123", "foobar", "somepath"));

//                comicAdapter.notifyItemInserted(0);
                result.dispatchUpdatesTo(comicAdapter);
                comicsState.setValue(comics);
            }
        };

        itemViewModel.getComics().observe(getViewLifecycleOwner(), comicsObserver);

        MutableLiveData<ArrayList<Comic>> dummyMutable = new MutableLiveData<>();
        ArrayList<Comic> dummyComics = new ArrayList<>();

//        dummyComics.add(new Comic("1234", "some title 123", "foobar", "somepath"));
//        dummyComics.add(new Comic("324234324", "some title 342 ", "foobar", "somepath"));
//        dummyComics.add(new Comic("5242342", "some title 52524 ", "foobar", "somepath"));
//        dummyComics.add(new Comic("6546456", "some title 234234", "foobar", "somepath"));
//        dummyComics.add(new Comic("2342", "some title 2345435", "foobar", "somepath"));
//        dummyMutable.setValue(dummyComics);
//        itemViewModel.setComics(dummyMutable);

        Api.getInstance().someGetRequestReturningComics(response -> {
//            Log.i("ADAPT", ""+response.toString());
            ArrayList<Comic> data = new ArrayList<>(new ArrayList<>());
            JSONArray jsonArray = response.getJSONObject("data").getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
//                Log.i("JSON", "PARSING: " + jo);
                String title = jo.getString("title");
                String id = String.valueOf(jo.getInt("id"));
                String description = jo.getString("description");
                JSONObject imgObject = jo.getJSONObject("thumbnail");
                String img = imgObject.getString("path") + imgObject.getString("extension");
                data.add(new Comic(id, title, description, img));
            }
            Log.i("DAAA ", "data " + data.get(0).getTitle());
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