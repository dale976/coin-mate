package com.example.coinmate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinmate.Domain.Comic;
import com.example.coinmate.R;

import java.util.ArrayList;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    MutableLiveData<ArrayList<Comic>> mComics = new MutableLiveData<>(new ArrayList<>());
    ViewGroup mParent;

    public ComicAdapter(ArrayList<Comic> comics) {
        mComics.setValue(comics);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mParent = parent;
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_comic, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comic comic = mComics.getValue().get(position);
        holder.comicNameTextView.setText(comic.getTitle());
        Glide.with(mParent.getContext())
                .load(comic.getImg())
                .into(holder.comicImageImageView);
    }

    @Override
    public int getItemCount() {
        return this.mComics.getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comicNameTextView;
        public ImageView comicImageImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comicNameTextView = itemView.findViewById(R.id.comicName);
            comicImageImageView = itemView.findViewById(R.id.comicImage);
        }

        public TextView getComicName() {
            return comicNameTextView;
        }
        public ImageView getComicImage() {return comicImageImageView; }
    }
}
