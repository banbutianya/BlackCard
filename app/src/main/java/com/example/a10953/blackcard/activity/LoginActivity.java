package com.example.a10953.blackcard.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a10953.blackcard.MyApplication;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.GsonUtil;
import com.example.a10953.blackcard.Util.Md5Util;
import com.example.a10953.blackcard.bean.LoginResponse;

import java.util.HashMap;
import java.util.Map;

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

    private void volleyPost(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "post请求失败" + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> map = new HashMap<>();
                map.put("cardid", username);
                map.put("pwd", md5password);
                return map;
            }
        };
        MyApplication.getHttpQueue().add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                md5password = Md5Util.MD5Encode(mPassword.getText().toString());
                volleyPost();
                break;
        }
    }
}
