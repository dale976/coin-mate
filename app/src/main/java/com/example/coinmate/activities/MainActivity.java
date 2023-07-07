package com.example.coinmate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coinmate.Adapter.WalletAdapter;
import com.example.coinmate.Domain.Wallet;
import com.example.coinmate.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewItems();
    }

    private void recyclerViewItems() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.itemList);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Wallet> wallets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            wallets.add(new Wallet("coin name " + i));
        }

        adapter = new WalletAdapter(wallets);
        recyclerView.setAdapter(adapter);
    }
}