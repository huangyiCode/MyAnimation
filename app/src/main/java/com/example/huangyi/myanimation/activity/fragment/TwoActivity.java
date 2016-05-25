package com.example.huangyi.myanimation.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.huangyi.myanimation.BaseActivity;
import com.example.huangyi.myanimation.reciver.MyListernTimeReciver;

/**
 * Created by huangyi on 16/5/13.
 */
public class TwoActivity extends BaseActivity {
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter=new IntentFilter(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        broadcastReceiver=new MyListernTimeReciver();
        registerReceiver(broadcastReceiver,filter);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
