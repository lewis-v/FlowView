package com.lewis_v.widget.viewflow;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * auth: lewis-v
 * time: 2018/1/31.
 */

public class YWFlowViewPager<T extends View> extends ViewPager {
    private final int START_POSITION_DOUBLE = 500;
    private final int MAX_MOVE = 100;//超过这个值认为是移动了

    private boolean enabled = true;//是否可以手动滑动,不影响滚动
    private YWFlowViewPagerAdapter<T> adapter;
    private ArrayList<T> list = new ArrayList<>();
    private boolean isAutoFlow = true;//是否自动循环流动
    private FlowThread threadFlow;//滚动的线程
    private int flowTime = 5000;//滚动切换时间,默认3秒
    private int flowPosition = 0;//目前所在的位置,默认一开始在0,列表位置
    private boolean isPause = false;//是否停止轮播
    private boolean isFlow = false;//是否在滚动
    private boolean isMove = false;//是否有触控移动,用于点击事件的判断
    private final Object lock = new Object();
    private OnPageClickListener onPageClickListener;
    private Point downPoint;//点击的坐标

    public YWFlowViewPager(Context context) {
        super(context);
    }

    public YWFlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (downPoint == null){
                    downPoint = new Point();
                }
                downPoint.set((int) event.getRawX(),(int) event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (MAX_MOVE < Math.abs(event.getRawY()-downPoint.y)
                        ||MAX_MOVE < Math.abs(event.getRawX()-downPoint.x) ) {

                    isMove = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isMove && onPageClickListener != null){
                    onPageClickListener.onPageClick(list.get(getPosition()),getPosition());
                }
                isMove = false;
                break;
        }
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            try {
                return super.onInterceptTouchEvent(event);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 开始执行
     * @param isAutoFlow 是否自动滚动
     */
    public void start(boolean isAutoFlow){
        this.isAutoFlow = isAutoFlow;
        adapter = new YWFlowViewPagerAdapter<>(list);
        setAdapter(adapter);
        if (flowPosition == 0){
            flowPosition = list.size();
        }
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {//滑动状态改变,在滑动是不进行滚动
                synchronized (lock) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {//无动作
                        isFlow = false;
                    } else {
                        isFlow = true;
                    }
                }
            }
        });
        setCurrentItem(flowPosition*START_POSITION_DOUBLE);
        initRunThread();
    }

    /**
     * 启动滚动线程
     */
    private void initRunThread(){
        if (threadFlow != null){
            threadFlow.interrupt();
        }
        threadFlow = new FlowThread();
        threadFlow.start();
    }

    /**
     * 滚动线程
     */
    class FlowThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    sleep(flowTime);
                } catch (InterruptedException e) {
                    return;
                }
                if (isAutoFlow) {
                    //多次同步,只是为了不在用户滑动时自动滚动
                    if (!isPause && !isFlow) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isPause && !isFlow) {
                                    synchronized (lock) {
                                        if (!isPause && !isFlow) {
                                            setCurrentItem(getCurrentItem() + 1);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 暂停轮播,建议在onPause调用
     */
    public void pause(){
        synchronized (lock) {
            isPause = true;
        }
    }

    /**
     * 唤醒轮播,建议在onResume调用
     */
    public void resume(){
        synchronized (lock) {
            isPause = false;
            if (threadFlow ==null || threadFlow.isAlive()){//若恢复时,由于低内存原因,线程被回收了,重新开启(一般不会出现这情况)
                initRunThread();
            }
        }
    }

    /**
     * 资源回收
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            if (threadFlow != null && threadFlow.isAlive()) {
                threadFlow.interrupt();
                threadFlow = null;
            }
        }catch (Exception e){        }
    }

    public interface OnPageClickListener{
        void onPageClick(View view, int position);
    }

    /**
     * 设置page点击事件
     * @param onPageClickListener
     * @return
     */
    public YWFlowViewPager setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
        return this;
    }

    /**
     * 设置是否可以滑动
     * @param enabled
     */
    public YWFlowViewPager setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * 添加滚动控件
     * @param view
     */
    public YWFlowViewPager addFlowView(T view){
        list.add(view);
        return this;
    }

    /**
     * 设置滚动控件列表
     * @param list
     * @return
     */
    public YWFlowViewPager setFlowView(ArrayList<T> list){
        this.list = list;
        return this;
    }

    /**
     * 设置滚动切换时间
     * @param flowTime
     * @return
     */
    public YWFlowViewPager setFlowTime(int flowTime) {
        this.flowTime = flowTime;
        return this;
    }

    /**
     * 设置初始位置
     * @param flowPosition
     * @return
     */
    public YWFlowViewPager setFlowPosition(int flowPosition) {
        this.flowPosition = flowPosition;
        return this;
    }

    public YWFlowViewPager setAutoFlow(boolean autoFlow) {
        isAutoFlow = autoFlow;
        return this;
    }

    /**
     * 获取显示的列表位置
     * @return
     */
    public int getPosition(){
        return getCurrentItem()%list.size();
    }
}
