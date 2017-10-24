package com.example.a10953.blackcard.Listener;

import com.android.volley.VolleyError;

/**
 * Created by 10953 on 2017/10/24.
 */
public interface HttpCallBackListener {
    void onSuccess(String response);
    void onFail(VolleyError volleyError);
}
