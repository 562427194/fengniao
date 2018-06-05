package mybase.library.activity.selectimg;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trello.rxlifecycle.LifecycleTransformer;

import java.io.File;
import java.util.ArrayList;

import mybase.library.R;
import mybase.library.activity.ActionBarBaseActivity;
import mybase.library.util.PermissionUtil;

/**
 * 多图选择 Created by Nereo on 2015/4/7. Updated by nereo on 2016/1/19.
 */
public class MultiImageSelectorActivity extends ActionBarBaseActivity implements
        MultiImageSelectorFragment.Callback, ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (permissions[0]) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImages();
                } else {
                    finish();
                }
                break;
            case Manifest.permission.CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (misFragment != null) {
                        misFragment.showCameraAction();
                    }
                }
                break;

        }


    }

    private ArrayList<String> resultList = new ArrayList<String>();
    private int mDefaultCount;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        initData();
    }

    @Override
    public void onViewClick(View view) {

    }

    public void initData() {
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra( EXTRA_SELECT_COUNT, 9 );
        int mode = intent.getIntExtra( EXTRA_SELECT_MODE, MODE_MULTI );
        boolean isShow = intent.getBooleanExtra( EXTRA_SHOW_CAMERA, true );
        if (mode == MODE_MULTI && intent.hasExtra( EXTRA_DEFAULT_SELECTED_LIST )) {
            resultList = intent
                    .getStringArrayListExtra( EXTRA_DEFAULT_SELECTED_LIST );
        }
        bundle = new Bundle();
        bundle.putInt( MultiImageSelectorFragment.EXTRA_SELECT_COUNT,
                mDefaultCount );
        bundle.putInt( MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode );
        bundle.putBoolean( MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow );
        bundle.putStringArrayList(
                MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST,
                resultList );
        PermissionUtil carUtil = new PermissionUtil( this );
        boolean hasWriteContactsPermission = carUtil
                .validateHasPermissions( true, Manifest.permission.READ_EXTERNAL_STORAGE );
        if (hasWriteContactsPermission) {
            showImages();
        }
    }

    private MultiImageSelectorFragment misFragment;

    private void showImages() {
        misFragment = (MultiImageSelectorFragment) Fragment.instantiate( this, MultiImageSelectorFragment.class.getName(), bundle );
        getFragmentManager()
                .beginTransaction()
                .replace( R.id.image_grid, misFragment ).commitAllowingStateLoss();
    }

    @Override
    public View createContentView() {
        View view = getLayoutInflater().inflate( R.layout.wskcss_activity_multiimageselector, null );
        return view;
    }

    private Menu menu;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_img_select, menu);
        this.menu = menu;
        MenuItem menuItem = menu.findItem(R.id.action_finish);
        updateDoneText();
        menuItem.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                result();
                return true;
            }
        } );
        return super.onCreateOptionsMenu(menu);
    }

    private void updateDoneText() {
        MenuItem menuItem = menu.findItem(R.id.action_finish);
        if (resultList == null || resultList.size() <= 0) {
            menuItem.setVisible(false);
        } else {
            menuItem.setTitle(String.format("%s(%d/%d)", getString(R.string.finish), resultList.size(), mDefaultCount));
            menuItem.setVisible(true);
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
        resultList.add( path );
        result();
    }

    private void result() {
        Intent data = new Intent();
        data.putStringArrayListExtra( EXTRA_RESULT, resultList );
        setResult( RESULT_OK, data );
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains( path )) {
            resultList.add( path );
        }
        // 有图片之后，改变按钮状态
        updateDoneText();
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains( path )) {
            resultList.remove( path );
        }
        updateDoneText();
    }

    @Override
    public void onCameraShot(File imageFile) {

        if (imageFile != null) {
            // notify system
            sendBroadcast( new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile( imageFile ) ) );
            Intent data = new Intent();
            resultList.add( imageFile.getAbsolutePath() );
            data.putStringArrayListExtra( EXTRA_RESULT, resultList );
            setResult( RESULT_OK, data );
            finish();
        }
    }

    public void click(View v) {
        if (resultList != null && resultList.size() > 0) {
            // 返回已选择的图片数据
            Intent data = new Intent();
            data.putStringArrayListExtra( EXTRA_RESULT, resultList );
            setResult( RESULT_OK, data );
            finish();
        }
    }

}
