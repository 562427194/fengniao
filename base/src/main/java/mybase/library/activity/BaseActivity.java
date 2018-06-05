package mybase.library.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mybase.library.core.BindToLifecycleable;
import mybase.library.util.MRxView;
import mybase.library.util.SystemBarTintManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public abstract class BaseActivity extends RxAppCompatActivity implements BindToLifecycleable {

    public static final String EXTRA_STATUSBAR_TEXT_COLOR_IS_DARK = "EXTRA_STATUSTBAR_TEXT_COLOR_IS_DARK";
    public static final String EXTRA_STATUSBAR_COLOR = "EXTRA_STATUSBAR_COLOR";

    private Toast toast;
    public void showToast(String msg){
        if(toast==null){
            toast = Toast.makeText(this,msg,Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        boolean isDark = getIntent().getBooleanExtra( EXTRA_STATUSBAR_TEXT_COLOR_IS_DARK ,false);
        setStatusBarTextColor( isDark );
        int color = getIntent().getIntExtra( EXTRA_STATUSBAR_COLOR,0 );
        if(color!=0){
            setStatusBgColor( color );
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            setStatusBarAlpha(0);
        }
    }


    /**
     *
     * @param isDark 状态栏字体颜色是否为深色
     */
    public void setStatusBarTextColor(boolean isDark){
        if(isDark){
            setStatusBarTextColorDark();
        }
        setMeizuStatusBarTextColor(isDark);
        setMiuiStatusBarTextColor( isDark );
    }
    private void setStatusBarTextColorDark() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
        }
    }

    private boolean setMeizuStatusBarTextColor(boolean isDark) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField( "MEIZU_FLAG_DARK_STATUS_BAR_ICON" );
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField( "meizuFlags" );
            darkFlag.setAccessible( true );
            meizuFlags.setAccessible( true );
            int bit = darkFlag.getInt( null );
            int value = meizuFlags.getInt( lp );
            if (isDark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt( lp, value );
            getWindow().setAttributes( lp );
            result = true;
        } catch (Exception e) {
        }
        return result;
    }

    private boolean setMiuiStatusBarTextColor(boolean isDark) {
        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName( "android.view.MiuiWindowManager$LayoutParams" );
            Field field = layoutParams.getField( "EXTRA_FLAG_STATUS_BAR_DARK_MODE" );
            darkModeFlag = field.getInt( layoutParams );
            Method extraFlagField = clazz.getMethod( "setExtraFlags", int.class, int.class );
            extraFlagField.invoke( getWindow(), isDark ? darkModeFlag : 0, darkModeFlag );
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    private SystemBarTintManager tintManager;

    public void setStatusBgColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager == null) {
                setTranslucentStatus( true );
                tintManager = new SystemBarTintManager( this );
            }
            tintManager.setStatusBarTintEnabled( true );
            tintManager.setStatusBarTintColor( color );
        }
    }
    public void setNavigationBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager == null) {
                setTranslucentStatus( true );
                tintManager = new SystemBarTintManager( this );
            }
            tintManager.setNavigationBarTintEnabled( true );
            tintManager.setNavigationBarTintColor( color );
        }
    }

    public void setNavigationBarEnable(boolean b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager == null) {
                setTranslucentStatus( true );
                tintManager = new SystemBarTintManager( this );
            }
            tintManager.setNavigationBarTintEnabled( b );
        }
    }

    public void settNavigationAlpha(int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager == null) {
                tintManager = new SystemBarTintManager( this );
                tintManager.setNavigationBarAlpha( alpha );
            } else {
                tintManager.setNavigationBarAlpha( alpha );
            }
        }
    }

    public void setStatusBarAlpha(float alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (tintManager == null) {
                tintManager = new SystemBarTintManager( this );
                tintManager.setStatusBarAlpha( alpha );
            } else {
                tintManager.setStatusBarAlpha( alpha );
            }
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes( winParams );
    }

    public void setClick(View... views) {
        for (View view : views) {
            if (view != null) {
                MRxView.clicks( view )
//                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe( new Action1<View>() {
                            @Override
                            public void call(View view) {
                                onViewClick( view );
                            }
                        } );
            }
        }
    }

    private Fragment showFragment;

    public Fragment switchFragment(int containerId,
                                   Class<? extends Fragment> clazz) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment userFragment = fm.findFragmentByTag( clazz.getName() );
        if (userFragment == null) {
            try {
                userFragment = clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (showFragment != null && showFragment != userFragment) {
            ft.hide( showFragment );
        }
        if (!userFragment.isAdded()) {
            ft.add( containerId, userFragment, clazz.getName() );
        } else {
            ft.show( userFragment );
        }
        ft.commitAllowingStateLoss();
        showFragment = userFragment;
        return userFragment;
    }

    public Fragment getShowFragment() {
        return showFragment;
    }

    public Fragment findFragment(Class<? extends Fragment> clazz) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment userFragment = fm.findFragmentByTag( clazz
                .getName() );
        return userFragment;
    }

    /**
     * 后台运行
     */
    public void runInBackground(){
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(
                new Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME), 0);
        ActivityInfo ai = homeInfo.activityInfo;
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
    }
    public void onViewClick(View view) {

    }
}
