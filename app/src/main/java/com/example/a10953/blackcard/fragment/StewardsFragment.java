package com.example.a10953.blackcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.activity.ZhuyeActivity;

/**
 * Created by 10953 on 2017/10/10.
 */

public class StewardsFragment extends Fragment implements View.OnClickListener{

    //登录ID
    private String uid;
    //登录Token
    private String token;

    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_black_stewards, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        button.setOnClickListener(this);
    }

    private void initView(View view) {

        button = (Button) view.findViewById(R.id.button);
        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                Toast.makeText(getContext(),"点击了按钮",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), ZhuyeActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("token",token);

                startActivity(intent);
                break;
        }

    }

}
