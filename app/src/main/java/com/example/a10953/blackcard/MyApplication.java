package com.example.a10953.blackcard;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 10953 on 2017/10/10.
 */

public class MyApplication extends Application {
    private static Context context;
    public static RequestQueue queue;


    @Override
    public void onCreate(){
        context = getApplicationContext();
        queue = Volley.newRequestQueue(context);
    }


    public static Context getContext(){
        return context;
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }
}
