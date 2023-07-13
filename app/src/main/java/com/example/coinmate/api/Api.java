package com.example.coinmate.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.coinmate.Domain.Comic;
import com.example.coinmate.activities.SomeCustomListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Api {
    private static final String TAG = "Api";
    private static Api instance = null;
    private final RequestQueue requestQueue;
    private final String api = "https://gateway.marvel.com:443/v1/public/comics?";
    private final String query = "&dateDescriptor=thisWeek&offset=0&limit=12&orderBy=issueNumber";


    public Api(Context context) {
        requestQueue = Volley.newRequestQueue(context);

    }

    public static synchronized Api getInstance(Context context) {
        if (null == instance) instance = new Api(context);
        return instance;
    }

    public static synchronized Api getInstance() {
        if (null == instance) {
            throw new IllegalStateException(Api.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    private String getAuthorizationString() {
        String ts = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()).getTime());
        String auth = "ts=%1$s&apikey=%2$s&hash=%3$s";
        String marvelPublicApiKey = "73225e6e8cf745bf63eb46f1d3aba751";
        String marvelPrivateApiKey = "67d2d6228016f3c2109a8c47e37a4d850c98a7f2";
        String hashedKey = getMD5(ts + marvelPrivateApiKey + marvelPublicApiKey);
        return String.format(auth, ts, marvelPublicApiKey, hashedKey);
    }

    private String getMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e("API-ADAPTER", String.valueOf(e));
        }
        return null;
    }

    public void getComics(Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError) {
        String reqUrl = api + getAuthorizationString() + query;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, reqUrl, null, listenerResponse, listenerError);
        requestQueue.add(req);
    }

    public void someGetRequestReturningComics(final SomeCustomListener<JSONObject> listener)
    {
        String reqUrl = api + getAuthorizationString() + query;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, reqUrl, null,
                response -> {
                    Log.d(TAG + ": ", "somePostRequest Response : " + response.toString());
                    if(null != response.toString()) {
                        try {
                            listener.getResult(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    if (null != error.networkResponse)
                    {
                        Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
//                            listener.getResult(false);
                    }
                });

        requestQueue.add(request);
    }
}
