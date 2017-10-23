package com.example.a10953.blackcard.z_work;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 10953 on 2017/10/19.
 */

public class DemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        initView();
    }

    private void initView() {

    }


    @Override
    protected void onPause() {
        //被挂起的时候加载数据，
        super.onPause();
        initData();
        initClick();
    }

    private void initData() {

    }

    private void initClick() {

    }

    @Override
    protected void onDestroy() {
        //退出的时候释放内存
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        //重新启动
        super.onRestart();
    }
}

