package com.example.androidtest.helper;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Api {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public Call post(String url, String json, Callback callback) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }

    public Call postWithAuth(String url, String token, String json, Callback callback) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }

    public Call getWithAuth(String url, String token, Callback callback) {
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }

    public String payload (JSONObject jsonObject) throws JSONException {
        return jsonObject.toString();
    }
}
