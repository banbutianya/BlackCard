package com.example.a10953.blackcard.Util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.MyApplication;

import java.util.Map;

/**
 * Created by 10953 on 2017/10/23.
 */

public class HttpUtil {
    public static void sendStringRequestByPost(String url, final Map<String, String> params,
                                                   final HttpCallBackListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        listener.onSuccess(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    //Log.e("请求出错。。。", volleyError.getMessage());
                    listener.onFail(volleyError);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params == null ? super.getParams() : params;
            }

        };
        MyApplication.getHttpQueue().add(stringRequest);
    }
}
