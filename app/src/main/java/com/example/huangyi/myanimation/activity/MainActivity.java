package com.example.huangyi.myanimation.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.huangyi.myanimation.BaseActivity;
import com.example.huangyi.myanimation.R;
import com.example.huangyi.myanimation.activity.fragment.TwoActivity;
import com.example.huangyi.myanimation.utils.DisplayUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {
FloatingActionButton mFOneloatBtn;
    FloatingActionButton mFTwoloatBtn;
    FloatingActionButton mFThreeloatBtn;
    public static final int CENTER=0x000000010;
    public static final int LEFT=0x000000020;
    public static final int RIGHT=0x000000040;
    public static final int TOP=0x000000060;
    public static final int BOTTOM=0x000000080;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initView() {
        mFOneloatBtn= (FloatingActionButton) findViewById(R.id.fOneBtn_main);
        mFTwoloatBtn= (FloatingActionButton) findViewById(R.id.fTwoBtn_main);
        mFThreeloatBtn= (FloatingActionButton) findViewById(R.id.fThreeBtn_main);
        //可以在规定时间实现透明度的渐变  先加速后减速  center
        Log.e("ceshi","宽度"+DisplayUtils.getScreenWidth(mContext)/2);
        ObjectAnimator objAnimatorX1=ObjectAnimator.ofFloat(mFOneloatBtn,"translationX",mFOneloatBtn.getTranslationX(),DisplayUtils.getScreenWidth(mContext)/2-50);
        ObjectAnimator objAnimatorY1=ObjectAnimator.ofFloat(mFOneloatBtn,"translationY",mFOneloatBtn.getTranslationY(),DisplayUtils.getScreenHeight(mContext)/2-50);
        AnimatorSet animSet1 = new AnimatorSet();
        //with表示同时进行   after表示按顺序执行
        animSet1.play(objAnimatorX1).with(objAnimatorY1);
        animSet1.setDuration(5000);
        animSet1.start();
//        left
        ObjectAnimator objAnimatorX2=ObjectAnimator.ofFloat(mFTwoloatBtn,"translationX",mFTwoloatBtn.getTranslationX(), DisplayUtils.getScreenWidth(mContext)/4-50);
        ObjectAnimator objAnimatorY2=ObjectAnimator.ofFloat(mFTwoloatBtn,"translationY",mFTwoloatBtn.getTranslationY(),DisplayUtils.getScreenHeight(mContext)/4-50);
        AnimatorSet animSet2 = new AnimatorSet();
        //with表示同时进行   after表示按顺序执行
        animSet2.play(objAnimatorX2).with(objAnimatorY2);
        animSet2.setDuration(5000);
        animSet2.start();
//        right
        ObjectAnimator objAnimatorX3=ObjectAnimator.ofFloat(mFThreeloatBtn,"translationX",mFThreeloatBtn.getTranslationX(), DisplayUtils.getScreenWidth(mContext)*3/4-50);
        ObjectAnimator objAnimatorY3=ObjectAnimator.ofFloat(mFThreeloatBtn,"translationY",mFThreeloatBtn.getTranslationY(),DisplayUtils.getScreenHeight(mContext)*3/4-50);
        AnimatorSet animSet3 = new AnimatorSet();
        //with表示同时进行   after表示按顺序执行
        animSet3.play(objAnimatorX3).with(objAnimatorY3);
        animSet3.setDuration(5000);
        animSet3.start();
        float density= getResources().getDisplayMetrics().density;//每英寸有多少个像素点
        float densityDpi= getResources().getDisplayMetrics().densityDpi;

        //实现数值的渐变    先加速后减速
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(5f,1f,3f,8f);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //拿到当前所对应的值
//                animation.getAnimatedValue()
                //拿到当前位置所占的百分比
//                getAnimatedFraction
                Log.e("ValueAnimator",""+animation.getAnimatedFraction()+Thread.currentThread().getName());
            }
        });
        setOnClickListeners(this,mFOneloatBtn,mFTwoloatBtn,mFThreeloatBtn);
        valueAnimator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fOneBtn_main:
                Intent intent=new Intent(mContext, TwoActivity.class);
                startActivity(intent);
                break;
            case R.id.fTwoBtn_main:
                break;
            case R.id.fThreeBtn_main:
                break;
        }
    }
}
