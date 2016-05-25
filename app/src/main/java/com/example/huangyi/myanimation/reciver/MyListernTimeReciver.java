package com.example.huangyi.myanimation.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.huangyi.myanimation.AppManager;
import com.example.huangyi.myanimation.utils.ToastUtil;

import java.util.Calendar;

/**
 * Created by huangyi on 16/5/13.
 */
public class MyListernTimeReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar=Calendar.getInstance();
       int min= calendar.get(Calendar.MINUTE);
//        if(min%3==0){
            ToastUtil.showToastDefault(context,"三的倍数  处理");
            AppManager.getInstance().AppExit(context);
//        }
    }
}
