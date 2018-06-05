/**
 *
 */
package com.visionet.fengniao.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 *
 * @author 郑锟斤拷
 * @version 1.0 锟斤拷锟斤拷时锟斤拷2015-5-18 锟斤拷锟斤拷11:27:42
 */
@SuppressLint("NewApi")
public class MyTabHost extends LinearLayout {

	public MyTabHost(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyTabHost(Context context) {
		super(context);
		init(context);
	}

	private String[] mts;
	private int[] bgs;
	private int sw, sh;

	private int tabTextColorId;
	private ViewPager mViewPager;
	private RadioGroup rg;
	private View sv;
	private LinearLayout llTop;
	private List<Map<String, Object>> mlist;
	public static final String TAB_TEXT = "TAB_TEXT";
	public static final String TAB_BG = "TAB_BG";
	private OnPageChangeListener mOpc;
	public void setOPC(OnPageChangeListener opc){
		this.mOpc = opc;
	}
	public void setTabTextColor(int textColorId){
		this.tabTextColorId = textColorId;
	}
	private void init(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		rg = new RadioGroup(context);
		rg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		rg.setOrientation(LinearLayout.HORIZONTAL);
		llTop = new LinearLayout(context);
		llTop.setOrientation(LinearLayout.VERTICAL);
		llTop.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		llTop.addView(rg);
		this.addView(llTop);
		sv = new View(context);
		llTop.addView(sv);
		mViewPager = new ViewPager(context);
		mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int indexEnd = (position)*sw;
				sv.setTranslationX(indexEnd);
				rg.check(position);
				if(mOpc!=null){
					mOpc.onPageSelected(position);
				}
			}


			@Override
			public void onPageScrollStateChanged(int arg0) {
				if(mOpc!=null){
					mOpc.onPageScrollStateChanged(arg0);
				}
			}



			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				sv.setTranslationX(sw*arg0+sw*arg1);
				if(mOpc!=null){
					mOpc.onPageScrolled(arg0, arg1, arg2);
				}
			}
		});
//		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//				mViewPager.setCurrentItem(checkedId);
//			}
//		});
		this.addView(mViewPager);
	}
	private int current = 0;
	public void setCurrent(int current){
		this.current = current;
		mViewPager.setCurrentItem(current);
		rg.check(current);
	}
	public View getSv() {
		return sv;
	}

	public void setSv(View sv) {
		this.sv = sv;
	}

	public ViewPager getmViewPager() {
		return mViewPager;
	}

	public void setmViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
	}

	public RadioGroup getRg() {
		return rg;
	}
	private OnClickListener rbClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(v.getId());
		}
	};
	public void setRg(RadioGroup rg) {
		this.rg = rg;
	}

//	private void initStartAnimation(int index,int indexEnd){
//		TranslateAnimation animation = new TranslateAnimation(
//				index ,
//				indexEnd, 0f, 0f);
//		animation.setInterpolator(new LinearInterpolator());
//		animation.setDuration(100);
//		animation.setFillAfter(true);
//		sv.startAnimation(animation);
//	}

	public void setTabBgs(int[] bs) {
		this.bgs = bs;
		mlist = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		int i = 0;
		for (int bg : bs) {
			map = new HashMap<String, Object>();
			map.put(TAB_BG, bg);
			if (mts != null) {
				map.put(TAB_TEXT, mts[i]);
			}
			mlist.add(map);
			i++;
		}
		setTabs(mlist);
	}

	public void setTabs(List<Map<String, Object>> list) {
		setTabNum(list.size());
		int id = 0;
		for (Map map : list) {
			addRadioButtom((String) map.get(TAB_TEXT),
					(Integer) map.get(TAB_BG),id);
			id++;
		}
		setCurrent(current);
	}

	private void addRadioButtom(String title, Integer bg,int id) {
		RadioButton rb = new RadioButton(getContext());
		if(bg!=null){
			Drawable top = getResources().getDrawable(bg);
			rb.setCompoundDrawablesRelativeWithIntrinsicBounds(null, top, null, null);
		}
		rb.setGravity(Gravity.CENTER);
		rb.setButtonDrawable(R.color.transparent);
		rb.setId(id);
		rb.setOnClickListener(rbClick);
		int p = (int) dip2px(getContext(), 5);
		rb.setPadding(p, p, p, p);
		rb.setCompoundDrawablePadding((int) dip2px(getContext(), -20));
		rb.setLayoutParams(new LayoutParams(sw, LayoutParams.WRAP_CONTENT));
		rg.addView(rb);
	}
	private void setTabNum(int num) {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		int swidth = wm.getDefaultDisplay().getWidth();
		sw = swidth / num;
		int height = sh == 0 ? 3 : sh;
		sv.setLayoutParams(new LayoutParams(sw, (int) dip2px(
				getContext(), height)));
	}

	public void setSVHeight(int height) {
		sh = height;
		sv.setLayoutParams(new LayoutParams(sw, (int) dip2px(
				getContext(), height)));
	}

	public LinearLayout getLlTop() {
		return llTop;
	}

	public void setLlTop(LinearLayout llTop) {
		this.llTop = llTop;
	}

	private int dip2px(Context context,float dpValue){
		float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dpValue*scale+0.5f);
	}


}
