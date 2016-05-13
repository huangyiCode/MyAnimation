package com.example.huangyi.myanimation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by huangyi on 16/5/6.
 * 柱状图two
 */
public class HYHistogramView extends ViewPager {
    private int pagerWidth;
    private int pagerHeight;
    public HYHistogramAdapter mHYHistogramAdapter;
    public ArrayList<int[]> mData;
    private Context mContext;
    private boolean FLAG_REFRESH;
    private Handler handler;
    private int drawXCounts;//横坐标数量
    private boolean drawYText=true;//默认绘制y轴文字
    private boolean touchText=true;//默认绘制触摸展示文字
    private static final int TYPE_TOP_TEXT_ON_HISTOGRAM=0x10000000;//柱子顶部文字
    private static final int TYPE_TOP_VIEW_ON_HISTOGRAM=0x20000000;//柱子顶部View
    private static final int TYPE_TOP_ON_CENTER=0x40000000;//中间文字
    private int TOUCH_SHOW_TYPE=TYPE_TOP_TEXT_ON_HISTOGRAM;

    public HYHistogramView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HYHistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    /**
     * 初始化
     */
    private void init() {
        handler = new MyHandler();
        mHYHistogramAdapter = new HYHistogramAdapter();
        mData = new ArrayList<>();
        setAdapter(mHYHistogramAdapter);
    }
    /**
     * warning:you must call this method before setPagerContent()
     * @param drawYText 是否绘制y轴坐标
     * @param touchText 触摸是否展示文字
     * @return
     */
    public HYHistogramView isDrawYTextAndTouch (boolean drawYText,boolean touchText){
        this.drawYText = drawYText;
        this.touchText=touchText;
        return this;
    }

    /**
     * warning:you must call this method before setPagerContent()
     * @param touchType  设置触摸展示方式
     * @return
     */
    public HYHistogramView setTouchType(int touchType){
        TOUCH_SHOW_TYPE=touchType;
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        pagerWidth = w;
        pagerHeight = h;
    }

    /**
     *
     * @param mData data
     * @param drawXCounts  histogram count
     * @return
     */
    public HYHistogramView setPagerContent(ArrayList<int[]> mData,int drawXCounts) {
        this.mData = mData;
        this.drawXCounts=drawXCounts;
        FLAG_REFRESH=true;
        mHYHistogramAdapter.notifyDataSetChanged();
        //展示最后一张
        handler.sendEmptyMessageDelayed(0,10);
        return this;
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (FLAG_REFRESH) {
                if (getCurrentItem() == mData.size() - 1) {
                    handler.removeMessages(0);
                    FLAG_REFRESH = false;
                } else {
                    setCurrentItem(mData.size() - 1);
                    handler.sendEmptyMessageDelayed(0, 10);
                }

            }
        }
    }


    private class HYHistogramAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mData.size() == 0 ? 0 : mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PagerView pagerView = new PagerView(mContext, position);
            container.addView(pagerView);
            return pagerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
              container.removeView((View) object);
        }
    }

    private class PagerView extends View {
        private int[] data;
        private int position;
        private Paint mRectPaint;
        private Paint mTextPaint;
        private Paint mLinePaint;
        private float maxData;//最大值
        private  float maxY;//纵坐标最大值
        private int paddingLeftAndRight=60;//左右边距
        private ArrayList<Rect> rectList=new ArrayList<>();
        private Rect touchRect;//触摸区域
        private int clictPosition;//触摸位置
        public PagerView(Context context, int position) {
            super(context);
            this.position = position;
            initData();
        }

        public PagerView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initData();
        }

        public PagerView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initData();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

        }

        /**
         * 初始化数组数据
         */
        private void initData() {
            data = mData.get(position);
            mRectPaint = new Paint();
            mRectPaint.setColor(Color.parseColor("#60cee5"));
            mRectPaint.setTextSize(12);
            mRectPaint.setStyle(Paint.Style.FILL);
            // 消除锯齿
            mRectPaint.setAntiAlias(true);
            mTextPaint=new Paint();
            mTextPaint.setColor(Color.parseColor("#ffffff"));
            mTextPaint.setTextSize(12);
            mTextPaint.setStyle(Paint.Style.FILL);
            // 消除锯齿
            mTextPaint.setAntiAlias(true);

            mLinePaint=new Paint();
            mLinePaint.setColor(Color.parseColor("#fba206"));
            mLinePaint.setTextSize(12);
            mLinePaint.setStyle(Paint.Style.FILL);
            // 消除锯齿
            mLinePaint.setAntiAlias(true);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            rectList.clear();
            Paint backPaint=new Paint();
            backPaint.setStyle(Paint.Style.FILL);
            backPaint.setColor(Color.parseColor("#1899b6"));
            canvas.drawRect(0,0, pagerWidth,pagerHeight,backPaint);
            drawHistogram(canvas);
            drawYText(canvas);
            drawTouchShowText(canvas);
        }

        /**
         * 绘制柱子
         */
        private void drawHistogram(Canvas canvas){
            for(int i=0;i<drawXCounts;i++){ //绘制柱子
                if(i<data.length){
                    int histogramCount=data[i];//柱子的实际数值
                    float histogramHeight=getAPartSizeToData()*histogramCount;//柱子的绘制高度
                    float piceWidth=(pagerWidth-paddingLeftAndRight)/drawXCounts;

                    /**
                     * 1.矩形左边的x坐标
                     * 2.矩形顶部的y坐标
                     * 3.矩形右边的x坐标
                     * 4.矩形底部的y坐标
                     */
                    Rect rect=new Rect((int) ((i)*piceWidth)+2+paddingLeftAndRight/2, (int) (pagerHeight-histogramHeight), (int) ((i+1)*piceWidth)-2+paddingLeftAndRight/2,pagerHeight);
                    canvas.drawRect(rect, mRectPaint);
                    rectList.add(rect);//用于触摸检测
                }
            }
        }
        /**
         * 触摸检测
         * @param event
         * @return
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    touchTest(event.getX(),event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchTest(event.getX(),event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL://取消位置绘制
                    touchRect=null;
                    postInvalidate();
                    break;
            }
            return true;
        }

        /**
         *
         * @param x 坐标x
         * @param y 坐标y
         *
         */
        private void touchTest(float x, float y){
            for(int i=0;i<rectList.size();i++){
                if(rectList.get(i).contains((int) x,(int)y)){
                    touchRect=rectList.get(i);
                    clictPosition=i;
                    postInvalidate();
                    return;
                }
            }
        }


        /**
         *
         * @return 柱子在View上展示的高度
         */
        private float getAPartSizeToData(){
            int min=(2<<30)-1;
            for (int aData : data) {
                if (aData > maxData) {
                    maxData = aData;
                }
                if (aData < min) {
                    min = aData;
                }
            }
            if(maxData==0){
                maxData=pagerHeight;
            }if(min==(2<<30)-1){
                min=0;
            }
            //此为最高点
            maxY= (float) ((min+maxData)*1.2);
            return pagerHeight/maxY;
        }
        /**
         * 绘制y轴坐标
         */
        private void drawYText(Canvas canvas){
            if(!drawYText){
                return;
            }


        }
        /**
         * 绘制触摸展示的文字 或者View
         */
        private void drawTouchShowText(Canvas canvas){
            if(!touchText){
                return;
            }
            if(touchRect==null){
                return;
            }
            if((TOUCH_SHOW_TYPE&TYPE_TOP_TEXT_ON_HISTOGRAM)!=0){
                canvas.drawText(""+data[clictPosition],touchRect.left-30,touchRect.top-10, mTextPaint);
                canvas.drawLine(0,touchRect.top,pagerWidth,touchRect.top,mLinePaint);//横向的线
            }
            if((TOUCH_SHOW_TYPE&TYPE_TOP_VIEW_ON_HISTOGRAM)!=0){

            }if((TOUCH_SHOW_TYPE&TYPE_TOP_ON_CENTER)!=0){

            }
        }
    }


}
