package com.example.coinmate.activities;

import org.json.JSONException;

public interface SomeCustomListener<T> {
    void getResult(T object) throws JSONException;
}
