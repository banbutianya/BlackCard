package com.example.a10953.blackcard.z_work;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10953.blackcard.R;

public class dingdanActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView countdown;
    private TextView didian;
    private String didian_text;
    private TextView dates;
    private String dates_text;
    private TextView time;
    private String time_text;
    private TextView money_pay;
    private String money_pay_text;
    private LinearLayout weixin;
    private LinearLayout zhifubao;
    private Button commit;

    private CountDownTimer timer;
    //判断支付方式，0的话是微信支付，1的话是支付宝支付。默认为0。
    private int TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);

        countdown = (TextView)findViewById(R.id.countdown);
        didian = (TextView)findViewById(R.id.didian);
        dates = (TextView)findViewById(R.id.dates);
        time = (TextView)findViewById(R.id.time);
        money_pay = (TextView)findViewById(R.id.money_pay);
        weixin = (LinearLayout)findViewById(R.id.weixin);
        zhifubao = (LinearLayout)findViewById(R.id.zhifubao);
        commit = (Button)findViewById(R.id.commit);

        //倒计时时间，15*60*1000毫秒,每1000毫秒更新一次
        timer = new MyCount(900000,1000);
        timer.start();

        //intent获取数据，显示
        Intent intent = getIntent();
        didian_text = intent.getStringExtra("");
        dates_text = intent.getStringExtra("");
        time_text = intent.getStringExtra("");
        money_pay_text = intent.getStringExtra("");

        didian.setText(didian_text);
        dates.setText(dates_text);
        time.setText(time_text);
        money_pay.setText(money_pay_text);

        //点击事件
        weixin.setOnClickListener(this);
        zhifubao.setOnClickListener(this);
        commit.setOnClickListener(this);

        //默认微信支付
        weixin.performClick();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.weixin:
                TYPE = 0;
                weixin.setBackgroundResource(R.drawable.iv_xuanzhong);
                zhifubao.setBackgroundResource(R.drawable.moren);
                Toast.makeText(this,"点击了微信支付按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.zhifubao:
                TYPE = 1;
                Toast.makeText(this,"该功能即将上线，敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.commit:
                Toast.makeText(this,"点击了提交按钮",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            //倒计时结束操作
            countdown.setText("倒计时结束");
        }
        @Override
        public void onTick(long millisUntilFinished) {
            //倒计时显示，秒数是个位数的话，在前面加个0
            int miao = (int)(millisUntilFinished/1000)%60;
            if(miao<10){
                countdown.setText(millisUntilFinished/1000/60 + ":0" + (millisUntilFinished/1000)%60);
            }else {
                countdown.setText(millisUntilFinished/1000/60 + ":" + (millisUntilFinished/1000)%60);
            }
        }
    }
}
