package com.lewis_v.widget.viewflow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lewis_v.widget.viewflow.YWFlowViewPager.OnPageClickListener;

import java.util.ArrayList;

/**
 * auth: lewis-v
 * time: 2018/2/1.
 */

public class YWFlowView extends FrameLayout {
    private YWFlowViewPager flowViewPager;
    private LinearLayout ll_point;
    private int pointResFocus = R.drawable.circle_red_focus,pointResUnFocus = R.drawable.circle_red_un_focus;
    private int pointWidth = 40,pointHeight = 40;//圆点的大小,默认为40*40
    private int pointMargins = 15;//圆点间隔,默认15

    public YWFlowView(Context context) {
        this(context,null);
    }

    public YWFlowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public YWFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化控件
     * @param context
     * @returnYWFlowViewPager
     */
    public YWFlowView init(Context context){
        LayoutInflater.from(context)
                .inflate(R.layout.layout_flow,this,true);
        flowViewPager = findViewById(R.id.yw_view);
        ll_point = findViewById(R.id.ll_point);
        return this;
    }

    public void start(final Context context, final boolean isAutoFlow){
        int size = flowViewPager.getList().size();
        for (int num = 0;num < size;num++){
            ImageView view = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointWidth, pointHeight);
            params.setMargins(pointMargins,pointMargins,pointMargins,pointMargins);//设置控件间距
            view.setLayoutParams(params);//设置控件的大小
            if (num == flowViewPager.getFlowPosition()){//默认开始的位置为对应图标
                view.setBackgroundResource(pointResFocus);
            }else {
                view.setBackgroundResource(pointResUnFocus);
            }
            ll_point.addView(view);
        }
        flowViewPager.addOnPageSelectedListener(new YWFlowViewPager.OnPageSelected() {
            @Override
            public void onSelected(int position) {
                pointChange(position);
            }
        });
        flowViewPager.start(isAutoFlow);
    }

    /**
     * 更改点的焦点位置
     * @param position
     */
    public void pointChange(int position){
        for (int childCount = ll_point.getChildCount(),num = 0;num < childCount;num++){
            if (position == num) {
                ll_point.getChildAt(num).setBackgroundResource(pointResFocus);
            }else {
                ll_point.getChildAt(num).setBackgroundResource(pointResUnFocus);
            }
        }
    }

    public YWFlowView addFlowView(View view){
        flowViewPager.addFlowView(view);
        return this;
    }

    public YWFlowView setFlowViewList(final ArrayList<View> viewList){
        flowViewPager.setFlowView(viewList);
        return this;
    }

    public YWFlowView setFlowViewTime(int time){
        flowViewPager.setFlowTime(time);
        return this;
    }

    public int getPosition(){
        return flowViewPager.getPosition();
    }

    public YWFlowView setAutoFlow(boolean isAuto){
        flowViewPager.setAutoFlow(isAuto);
        return this;
    }

    public YWFlowView setFlowDefultPosition(int position){
        flowViewPager.setFlowPosition(position);
        return this;
    }

    public YWFlowView setPagingEnabled(boolean enabled){
        flowViewPager.setPagingEnabled(enabled);
        return this;
    }

    public YWFlowView setOnPageClickListener(final OnPageClickListener onPageClickListener){
        flowViewPager.setOnPageClickListener(onPageClickListener);
        return this;
    }

    public void pause(){
        flowViewPager.pause();
    }

    public void resume(){
        flowViewPager.resume();
    }

    public YWFlowView setPointResFocus(int pointResFocus) {
        this.pointResFocus = pointResFocus;
        return this;
    }

    public YWFlowView setPointResUnFocus(int pointResUnFocus) {
        this.pointResUnFocus = pointResUnFocus;
        return this;
    }

    /**
     * 设置圆点大小
     * @param width
     * @param height
     * @return
     */
    public YWFlowView setPointSize(int width,int height){
        this.pointWidth = width;
        this.pointHeight = height;
        return this;
    }

    /**
     * 设置圆点间隔
     * @param pointMargins
     * @return
     */
    public YWFlowView setPointMargins(int pointMargins) {
        this.pointMargins = pointMargins;
        return this;
    }

    /**
     * 设置圆点父布局的高度
     * @param height
     * @return
     */
    public YWFlowView setPointLayoutHeight(int height){
        ViewGroup.LayoutParams pa = ll_point.getLayoutParams();
        pa.height = height;
        ll_point.setLayoutParams(pa);
        return this;
    }

    /**
     * 设置圆点父布局的背景
     * @param res
     * @return
     */
    public YWFlowView setPointLayoutBackground(int res){
        ll_point.setBackgroundResource(res);
        return this;
    }

    /**
     * 设置圆点所在的位置,默认center
     * @param gravity
     * @return
     */
    public YWFlowView setPointGravity(int gravity){
        ll_point.setGravity(gravity);
        return this;
    }
}
