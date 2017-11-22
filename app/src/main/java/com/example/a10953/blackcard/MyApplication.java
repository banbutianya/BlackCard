package com.example.a10953.blackcard;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 10953 on 2017/10/10.
 */

public class MyApplication extends Application {
    private static Context context;
    public static RequestQueue queue;

    //OSS的Bucket
    public static final String OSS_BUCKET = "black-card";
    //设置OSS数据中心域名或者cname域名
    public static final String OSS_BUCKET_HOST_ID = "oss-cn-beijing.aliyuncs.com";
    //Key
    private static final String accessKey = "LTAIuHdXXz6KVXZK";
    private static final String screctKey = "B3NHAMXz09723UyyDOpyzXb2XMS4n2";
    public static OSS oss;

    @Override
    public void onCreate(){
        context = getApplicationContext();
        queue = Volley.newRequestQueue(context);
        initOSSConfig();
    }


    public static Context getContext(){
        return context;
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }

    private void initOSSConfig(){
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKey, screctKey);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        if(BuildConfig.DEBUG){
            OSSLog.enableLog();
        }
        oss = new OSSClient(getApplicationContext(), MyApplication.OSS_BUCKET_HOST_ID, credentialProvider, conf);
    }

}
