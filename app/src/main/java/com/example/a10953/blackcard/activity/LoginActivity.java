package com.example.a10953.blackcard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.GsonUtil;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.Util.HttpUtil;
import com.example.a10953.blackcard.Util.Md5Util;
import com.example.a10953.blackcard.bean.LoginResponse;


import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "LoginActivity";

    private EditText mUsername;
    private EditText mPassword;
    private Button login;
    private Button mForget_password;
    private Button mCode_login;
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=login";

    private String username;
    private String password;
    private String md5password;

    private String uid;
    private String token;

    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        mForget_password = (Button) findViewById(R.id.forget_password);
        mCode_login = (Button) findViewById(R.id.code_login);
        login.setOnClickListener(this);

    }

    private void startlogin(){
        //网络请求传递的参数
        HashMap<String, String> map = new HashMap<>();
        map.put("cardid", username);
        map.put("pwd", md5password);

        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                //执行具体逻辑
                LoginResponse res = GsonUtil.parseJsonWithGson(response, LoginResponse.class);
                result = res.getResult();

                if(result == 1){
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    uid = res.getData().getUid();
                    token = res.getData().getToken();
                    intent.putExtra("uid",uid);
                    intent.putExtra("token",token);
                    startActivity(intent);
                }else if (result != 1){
                    Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG,"返回值result = " + result);

                String s = response.toString();
                Log.i(TAG, "post请求成功" + s);
            }

            @Override
            public void onFail(VolleyError volleyError) {
                //处理异常情况
                Log.i(TAG, "post请求失败");
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                md5password = Md5Util.MD5Encode(mPassword.getText().toString());
                //登录
                startlogin();
                break;
            case R.id.forget_password:
                Toast.makeText(this, "点击了忘记密码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.code_login:
                Toast.makeText(this, "点击了验证码登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
