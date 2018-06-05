package com.visionet.fengniao.core.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visionet.fengniao.R;

import butterknife.BindView;
import mybase.library.activity.BaseActivity;


public abstract class ActionBarBaseActivity extends FNBaseActivity {
    public static final String EXTRAL_TITLE = "EXTRAL_TITLE";
    private ViewGroup vgContent;
    public Toolbar toolbar;
    public AppBarLayout appbar;
    public TextView tvTitle;
    public LinearLayout llMain;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    public void initView(){
        setContentView( mybase.library.R.layout.activity_base);
        tvTitle = (TextView) findViewById( mybase.library.R.id.tv_title );
        tvTitle.setText( getTitle());
        String title = getIntent().getStringExtra(EXTRAL_TITLE);
        if(title!=null){
            setTitle(title);
        }
        llMain = (LinearLayout) findViewById( mybase.library.R.id.ll_main );
        vgContent = (ViewGroup) findViewById( mybase.library.R.id.ll_content);
        vgContent.addView(createContentView(),new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        toolbar = (Toolbar) findViewById( mybase.library.R.id.toolbar);
        appbar = (AppBarLayout) findViewById( mybase.library.R.id.app_bar );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled( false );
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText( title );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public abstract View createContentView();

}
