/**
 * 
 */
package mybase.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import mybase.library.R;


/**
 * 
 * @author 郑海
 * @version 1.0 创建时间2015-2-6 下午2:12:34
 */
public class WebViewActivity extends ActionBarBaseActivity {

	public final static String EXTRA_URL = "EXTRA_URL";
	public final static String EXTRA_TITLE = "EXTRA_TITLE";

	private String url,title;
	private WebViewFragment mFragment;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		url = getIntent().getStringExtra(EXTRA_URL);
		title = getIntent().getStringExtra(EXTRA_TITLE);
		mFragment = new WebViewFragment(url);
		getFragmentManager().beginTransaction()
				.replace( R.id.fl_content, mFragment).commit();
		setTitle( title );
	}

	@Override
	public void onViewClick(View view) {

	}

	/**
	 * @param context
	 * @param url
	 * @param title
     * @param isDark 状态栏字体颜色是否为深色
     */
	public static void toWebViewActivity(Context context, String url, String title,boolean isDark,int statusBarColor){
		Intent intent = new Intent(context,WebViewActivity.class);
		intent.putExtra(EXTRA_URL, url);
		intent.putExtra(EXTRA_TITLE, title);
		intent.putExtra( EXTRA_STATUSBAR_TEXT_COLOR_IS_DARK,isDark );
		intent.putExtra(EXTRA_STATUSBAR_COLOR,statusBarColor  );
		context.startActivity(intent);
	}
	@Override
	public void onBackPressed() {
		if(!mFragment.canBack()){
			super.onBackPressed();
		}
	}

	@Override
	public View createContentView() {
		View view =  getLayoutInflater().inflate(R.layout.wskcss_activity_web,null);
		LinearLayout.LayoutParams layoutParams =
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(layoutParams);
		return view;
	}
}
