package com.example.coinmate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinmate.Domain.Wallet;
import com.example.coinmate.R;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.Viewholder> {
    ArrayList<Wallet> wallets;

    public WalletAdapter(ArrayList<Wallet> wallets) {
        this.wallets = wallets;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_wallet, parent, false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.coinName.setText(wallets.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView coinName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.coinName);
        }
    }
}
