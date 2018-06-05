package com.visionet.fengniao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> list;

    public AdViewPagerAdapter(Context context, List<View> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));// 删除页卡
    }

    // 这个方法用来实例化页卡
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));// 添加页卡
         return list.get(position);    
    }
    
    // 返回页卡的数量
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;// 官方提示这样写
    }
}