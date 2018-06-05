package com.visionet.fengniao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.visionet.fengniao.R;
import com.visionet.fengniao.core.activity.FNBaseActivity;
import com.visionet.fengniao.core.fragment.TabFragment;
import com.visionet.fengniao.fragment.TabAchievementFragment;
import com.visionet.fengniao.fragment.TabInsuranceFragment;
import com.visionet.fengniao.fragment.TabProductFragment;
import com.visionet.fengniao.fragment.TabSearchFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FNBaseActivity {

    public static final String EXTRA_TAB_ID = "EXTRA_TAB_ID";

    @BindView(R.id.tab_product)
    RadioButton tabProduct;
    @BindView(R.id.tab_insurance)
    RadioButton tabInsurace;
    @BindView(R.id.tab_search)
    RadioButton tabSarch;
    @BindView(R.id.tab_achievement)
    RadioButton tabAchievement;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;

    private HashMap<Integer, Class<? extends TabFragment>> tabMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTab();
        rgTab.setOnCheckedChangeListener(tabCCListener);
        tabProduct.setChecked(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWskApplication().cleanEelseActivity( this );
    }


    private RadioGroup.OnCheckedChangeListener tabCCListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Class<? extends Fragment> fragmentClass = tabMap.get( checkedId );
            switchFragment( R.id.fl_content, fragmentClass );
        }
    };

    private void initTab() {
        tabMap = new HashMap();
        tabMap.put( R.id.tab_product, TabProductFragment.class );
        tabMap.put( R.id.tab_insurance, TabInsuranceFragment.class );
        tabMap.put( R.id.tab_search, TabSearchFragment.class );
        tabMap.put( R.id.tab_achievement, TabAchievementFragment.class );
    }

    @Override
    public void onBackPressed() {
        runInBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static final String APK_URL = "APK_URL";
    private static final String EXTRA_PREMISSIONS = "EXTRA_PREMISSIONS";

    public static void toMain(Context context, String apkUrl, int tabId) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( APK_URL, apkUrl );
        if (tabId != 0) {
            intent.putExtra( MainActivity.EXTRA_TAB_ID, tabId );
        }
        context.startActivity( intent );
    }
}
