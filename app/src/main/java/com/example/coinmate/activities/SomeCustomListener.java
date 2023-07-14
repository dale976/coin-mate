package com.example.coinmate.activities;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

public interface SomeCustomListener<T> {
    void getResult(T object) throws JSONException, IOException;
}
