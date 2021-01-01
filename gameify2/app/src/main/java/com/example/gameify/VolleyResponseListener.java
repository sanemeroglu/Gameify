package com.example.gameify;

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(Object response);
}
