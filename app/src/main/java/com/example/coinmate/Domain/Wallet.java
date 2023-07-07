package com.example.coinmate.Domain;

public class Wallet {
    private String name;

    public Wallet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
