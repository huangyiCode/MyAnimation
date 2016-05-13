package com.example.huangyi.myanimation.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huangyi.myanimation.utils.DisplayUtils;

import java.util.ArrayList;

/** 柱状图one
 * Created by huangyi on 16/5/3.
 */
public class MyRunningView extends ViewPager {
    private Context mContext;
    private MyPagerAdapter mMyPagerAdapter;
    private ArrayList<int[]> mListData;//数据
    private int PagerHeight;
    private Handler handler;
    private Paint mPaint;


    public MyRunningView(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public MyRunningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();

    }
    private void init(){
        mPaint=new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(12);
        mMyPagerAdapter=new MyPagerAdapter();
        mListData=new ArrayList<>();

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.PagerHeight=h;
        setAdapter(mMyPagerAdapter);

    }

    private boolean FLAG_REFRESH;
    public void setData(ArrayList<int[]> mListData){
        if(handler!=null){
            handler.removeMessages(0);
        }
        handler=new MyHandler();
        this.mListData=mListData;
        FLAG_REFRESH=true;
        mMyPagerAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(0,10);

    }
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(FLAG_REFRESH){
                if(getCurrentItem()==mListData.size()-1){
                    handler.removeMessages(0);
                    FLAG_REFRESH=false;
                }else{
                    setCurrentItem(mListData.size()-1);
                    handler.sendEmptyMessageDelayed(0,10);
                }

            }
        }
    }


    private   class MyPagerAdapter extends android.support.v4.view.PagerAdapter{
        @Override
        public int getCount() {
            return mListData.size()==0?0:mListData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object==view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MyView view=new MyView(mContext,position);
            container.addView(view);
            //此处需要渲染数据
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

   //展示每页的数据  里面共有48个子View
     private   class MyView extends LinearLayout{
       private  int position;
       private int[] data;
       private double maxY;//轴最大值
       private double maxData;//最大值
       private float touchPosition=-1;//触摸的位置
       private TextView mTvShowView;
       public ArrayList<ObjectAnimator> animList;

       public MyView(Context context,int position) {
           super(context);
           this.position=position;
           data=mListData.get(position);
           initMyView();
       }

       public MyView(Context context, AttributeSet attrs) {
           super(context, attrs);
           initMyView();
       }

       public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
           super(context, attrs, defStyleAttr);
           initMyView();
       }

       //初始化48个View所对应的View
      private void initMyView(){

          setBackgroundColor(Color.parseColor("#1899b6"));
          animList=new ArrayList<>();
          setOrientation(HORIZONTAL);
          setGravity(Gravity.BOTTOM);
          setPadding(40,0,40,0);
          LayoutParams paramsParent=new LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
          setLayoutParams(paramsParent);
          mTvShowView=new TextView(mContext);
          mTvShowView.setGravity(Gravity.BOTTOM|Gravity.CENTER);
          LayoutParams paramsShow=new LayoutParams(150,60);
          mTvShowView.setLayoutParams(paramsShow);
          mTvShowView.setBackgroundColor(Color.argb(100,254,254,254));
          for(int i=0;i<48;i++){
              LinearLayout linearLayout=new LinearLayout(mContext);

              if(i<data.length){//有数据
                  Log.e("ceshi","位置=="+position+"有");
                  //此处需要计算实际高度与 百分比高度 得到比例高度

                  LayoutParams params=new LayoutParams(0, (int) (data[i]*getSizeHeight()),1);
                  params.setMargins(2,0,2,0);
                  linearLayout.setBackgroundColor(Color.parseColor("#60cee5"));
                  linearLayout.setLayoutParams(params);
                  ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(linearLayout,"translationY",linearLayout.getTranslationY()+500,linearLayout.getTranslationX());
                  objectAnimator.setDuration(2000);
                  animList.add(objectAnimator);
              }else{//没有数据
                  Log.e("ceshi","位置=="+position+"无");
                  LayoutParams params=new LayoutParams(0, ViewPager.LayoutParams.MATCH_PARENT,1);
                  params.setMargins(2,0,2,0);
                  linearLayout.setLayoutParams(params);
              }
              Log.e("ceshi","重回");
              addView(linearLayout);
          }
          addView(mTvShowView);
      }

       @Override
       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
           super.onSizeChanged(w, h, oldw, oldh);
           //进行动画
           for (ObjectAnimator objectAnimato:animList
                   ) {
               objectAnimato.start();
           }
       }

       @Override
       protected void onDraw(Canvas canvas) {
           super.onDraw(canvas);
           //判断触摸的区间  以及是否需要显示文字
           float screenWidth= DisplayUtils.getScreenWidth(mContext);
           float trueWidth=screenWidth-80;
           float piceWidth=trueWidth/48;
           //40-trueWidth
//           canvas.drawRect(0,0,0,0,mPaint);

           canvas.drawText(""+(int)maxY,5, (float) (PagerHeight-(maxY)*getSizeHeight())+14,mPaint);//Y最高点
           canvas.drawText(""+(int)maxData,5, (float) (PagerHeight-(maxData)*getSizeHeight()+7),mPaint);//Y最高点
           //绘出区分区间的
           int times=5;
           for(int i=0;i<times;i++){//通过此时的最大值区分纵轴精度
               canvas.drawText(""+(int)(maxData*i/times),5, (float) (PagerHeight-(float) ((maxData/times)*(i)*getSizeHeight())-14),mPaint);//Y最高点
           }
           if(touchPosition!=-1){
               int a= (int) (touchPosition/piceWidth);
               Log.e("ceshi","a==="+a);
               a-=2;
               if(!(a>data.length-1)){//有数据
                   canvas.drawText("时区"+a+"\n"+data[a],piceWidth*a+10, (float) (PagerHeight-(data[a])*getSizeHeight())-20,mPaint);//Y最高点
                   mTvShowView.layout((int) (piceWidth*a+10), 10, (int) ((int) screenWidth-(screenWidth-150)),60);
               }

           }
       }

       private  double getSizeHeight(){
           int min=999999999;
           for(int i=0;i<data.length;i++){
               if(data[i]>maxData){
                   maxData=data[i];
               }
               if(data[i]<min){
                   min=data[i];
               }
           }
           if(maxData==0){
               maxData=PagerHeight;
           }if(min==999999999){
               min=0;
           }
           //此为最高点
           maxY= ((min+maxData)*0.2+(min+maxData));
           return PagerHeight/maxY;
       }

       @Override
       public boolean onTouchEvent(MotionEvent event) {

         float x=  event.getX();
           float y=  event.getY();
           touchPosition=x;
           Log.e("测试","x="+x+"   y="+y);
           switch (event.getAction()){
               case MotionEvent.ACTION_DOWN:
                   //绘制
                   postInvalidate();//调用onDrawo
                   break;
               case MotionEvent.ACTION_UP:
               case MotionEvent.ACTION_CANCEL:
                   //移除
                   touchPosition=-1;
                   postInvalidate();
                   break;
           }
           return super.onTouchEvent(event);
       }
   }



}
