package mybase.library.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import mybase.library.R;


public abstract class ActionBarBaseActivity extends BaseActivity {
    private ViewGroup vgContent;
    public Toolbar toolbar;
    public AppBarLayout appbar;
    public TextView tvTitle;
    public LinearLayout llMain;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_base);
        llMain = (LinearLayout) findViewById( R.id.ll_main );
        vgContent = (ViewGroup) findViewById( R.id.ll_content);
        vgContent.addView(createContentView(),new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.app_bar );
        tvTitle = (TextView) findViewById(R.id.tv_title );
        tvTitle.setText( getTitle());
        initActionBar();
    }
    public void initActionBar(){
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
