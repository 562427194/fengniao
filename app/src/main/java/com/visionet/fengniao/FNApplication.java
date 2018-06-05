package com.visionet.fengniao;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;


import java.util.ArrayList;

/**
 * Created by zhenghai on 2016/11/11.
 */
public class FNApplication extends MultiDexApplication {

    private static FNApplication mContext;
    public static FNApplication getAppContext(){
        return mContext;
    }

    public SharedPreferences getDefautSharePreferences(){
        return PreferenceManager.getDefaultSharedPreferences( this );
    }

    ArrayList<Activity> activities = new ArrayList<>(  );
    public void addActivity(Activity activity){
        this.activities.add( activity );
    }
    public void cleanEelseActivity(Activity activity){
        for(Activity a :activities){
            if(!a.equals(activity )){
                a.finish();
            }
        }
    }
}
