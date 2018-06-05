package com.visionet.fengniao.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visionet.fengniao.R;
import com.visionet.fengniao.adapter.AdViewPagerAdapter;
import com.visionet.fengniao.core.fragment.TabFragment;
import com.visionet.fengniao.utils.CheckDateTimeClick;
import com.visionet.fengniao.utils.ImageLoadUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabAchievementFragment extends TabFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Unbinder unbinder;
    @BindView(R.id.rb_today)
    RadioButton rbToday;
    @BindView(R.id.rb_day)
    RadioButton rbDay;
    @BindView(R.id.rb_month)
    RadioButton rbMonth;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.rl_line)
    RelativeLayout rlLine;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private int sw;

    private List<View> views = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab_achievement, container, false);
        unbinder = ButterKnife.bind(this, view);
        rgTab.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
              rlLine.getLayoutParams().width = (sw=rgTab.getWidth()/3);
              rlLine.requestLayout();
            }
        } );
        View report = inflater.inflate(R.layout.layout_today_report_form,null,false);
        views.add(report);
         report = inflater.inflate(R.layout.layout_day_report_form,null,false);
         TextView tvTime = (TextView) report.findViewById(R.id.tv_time);
        CheckDateTimeClick checkDateTimeClick = new CheckDateTimeClick(tvTime,true,false);
        tvTime.setOnClickListener(checkDateTimeClick);
        views.add(report);

        report =inflater.inflate(R.layout.layout_month_report_form,null,false);
        TextView tvMonth = (TextView) report.findViewById(R.id.tv_month);
        checkDateTimeClick = new CheckDateTimeClick(tvMonth,true,false);
        checkDateTimeClick.setDateFormat("yyyy-MM");
        checkDateTimeClick.setText(Calendar.getInstance());
        tvMonth.setOnClickListener(checkDateTimeClick);
        views.add(report);
        PagerAdapter pagerAdapter = new AdViewPagerAdapter(getContext(),views);
        viewpager.setAdapter(pagerAdapter);
        setClick(rbDay,rbToday,rbMonth);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                rlLine.setTranslationX(sw*position+sw*positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rgTab.check(R.id.rb_today);
                        break;
                    case 1:
                        rgTab.check(R.id.rb_day);
                        break;
                    case 2:
                        rgTab.check(R.id.rb_month);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.rb_today:
                viewpager.setCurrentItem(0);
                break;
            case R.id.rb_day:
                viewpager.setCurrentItem(1);
                break;
            case R.id.rb_month:
                viewpager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
