package com.lewis_v.widget.viewflow;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * auth: lewis-v
 * time: 2018/1/31.
 */

public class YWFlowViewPagerAdapter <T extends View> extends PagerAdapter {
    private List<T> list;

    public YWFlowViewPagerAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container.getChildCount() > 3) {//防止其删除完了,然后就显示空白了
            container.removeView(list.get(position % list.size()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position%list.size());
        container.removeView(view);
        container.addView(view);
        return view;
    }
}