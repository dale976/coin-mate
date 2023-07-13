package com.example.coinmate.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinmate.Domain.Comic;
import com.example.coinmate.R;

import java.util.ArrayList;
import java.util.Objects;

class ComicsDiffCallback extends DiffUtil.Callback {

    private ArrayList<Comic> mOldList;
    private ArrayList<Comic> mNewList;

    public ComicsDiffCallback(ArrayList<Comic> oldList, ArrayList<Comic> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }
    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // add a unique ID property on Contact and expose a getId() method
        return Objects.equals(mOldList.get(oldItemPosition).getId(), mNewList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Comic oldComic = mOldList.get(oldItemPosition);
        Comic newComic = mNewList.get(newItemPosition);

        return Objects.equals(oldComic.getTitle(), newComic.getTitle());
    }
}

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    MutableLiveData<ArrayList<Comic>> mComics = new MutableLiveData<>();

    public ComicAdapter(ArrayList<Comic> comics) {
        mComics.setValue(comics);
    }


    public void swapItems(ArrayList<Comic> comics) {
        // compute diffs
        final ComicsDiffCallback diffCallback = new ComicsDiffCallback(this.mComics.getValue(), comics);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        // clear contacts and add
        Objects.requireNonNull(this.mComics.getValue()).clear();
        this.mComics.getValue().addAll(comics);

        diffResult.dispatchUpdatesTo(this); // calls adapter's notify methods after diff is computed
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_comic, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("COMIC ADAPAYER", "comics ");
        holder.comicNameTextView.setText(mComics.getValue().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return this.mComics.getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comicNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comicNameTextView = itemView.findViewById(R.id.comicName);
        }

        public TextView getComicName() {
            return comicNameTextView;
        }
    }
}
