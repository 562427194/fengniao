package com.visionet.fengniao.core.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.visionet.fengniao.FNApplication;
import com.visionet.fengniao.R;

import mybase.library.activity.BaseActivity;


/**
 * Created by zhenghai on 2016/12/20.
 */

public class FNBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBgColor(getResources().getColor(R.color.colorPrimary));
        setStatusBarTextColor(true);
        getWskApplication().addActivity(this);
    }

    public FNApplication getWskApplication() {
        return (FNApplication) getApplication();
    }


}


