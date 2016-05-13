package com.example.huangyi.myanimation.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huangyi.myanimation.model.ApiService;
import com.example.huangyi.myanimation.model.GetIpInfoResponse;
import com.example.huangyi.myanimation.R;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
FloatingActionButton mFloatBtn;
    WebView mWebView;
    public static final int CENTER=0x000000010;
    public static final int LEFT=0x000000020;
    public static final int RIGHT=0x000000040;
    public static final int TOP=0x000000060;
    public static final int BOTTOM=0x000000080;
    public ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mFloatBtn= (FloatingActionButton) findViewById(R.id.float_btn);
        mWebView= (WebView) findViewById(R.id.web);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //可以在规定时间实现透明度的渐变  先加速后减速
        ObjectAnimator objAnimatorX=ObjectAnimator.ofFloat(mFloatBtn,"translationX",mFloatBtn.getTranslationX(),+500);
        ObjectAnimator objAnimatorY=ObjectAnimator.ofFloat(mFloatBtn,"translationY",mFloatBtn.getTranslationY(),+500);
        AnimatorSet animSet = new AnimatorSet();
        //with表示同时进行   after表示按顺序执行
        animSet.play(objAnimatorX).with(objAnimatorY);
        animSet.setDuration(5000);
        animSet.start();
//       TextView tv= (TextView) findViewById(R.id.aaaaaaa);
//        tv.setText("哈哈");
       Log.e("ceshi","数值CENTER"+((CENTER|BOTTOM)&CENTER));
        Log.e("ceshi","数值LEFT"+((CENTER|BOTTOM)&LEFT));
        Log.e("ceshi","数值RIGHT"+((CENTER|BOTTOM)&RIGHT));
        Log.e("ceshi","数值TOP"+((CENTER|BOTTOM)&TOP));
        Log.e("ceshi","数值BOTTOM"+((CENTER|BOTTOM)&BOTTOM));
       float density= getResources().getDisplayMetrics().density;//每英寸有多少个像素点
       float densityDpi= getResources().getDisplayMetrics().densityDpi;
        Log.e("ceshi","density===="+density);
        Log.e("ceshi","densityDpi===="+densityDpi);

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
        valueAnimator.start();
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ip.taobao.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);

//                mProgressBar.setVisibility(View.VISIBLE);

                Call<GetIpInfoResponse> call = apiService.getIpInfo("63.223.108.42");
                call.enqueue(new Callback<GetIpInfoResponse>() {
                    @Override
                    public void onResponse(Response<GetIpInfoResponse> response, Retrofit retrofit) {

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        //
    }
}
