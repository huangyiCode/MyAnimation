package com.example.huangyi.myanimation.activity;

import android.os.Bundle;

import com.example.huangyi.myanimation.BaseActivity;

/**
 * Created by huangyi on 16/5/25.
 */
public class QuestionActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //TODO 自加问题
        int count=0;
        for (int i=0;i<10;i++){
//          count=count++;
            int temp=count;
            count++;
            count=temp;
        }
        //TODO 方法无法定向问题 首先会识别小的  同级别会抛出异常
        String sameString="sameString";
        sameMethod(sameString);

    }

    //TODO 方法无法定向问题
    private void sameMethod(String string){

    }
    //TODO 方法无法定向问题
    private void sameMethod(String... str){

    }
}
