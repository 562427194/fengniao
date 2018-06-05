package mybase.library.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import mybase.library.R;
import mybase.library.view.photoview.PhotoView;
import mybase.library.view.photoview.PhotoViewAttacher;


public class ShowImageActivity extends BaseActivity {

	public static final String KEY_IMG_ARRAY = "KEY_IMG_ARRAY";
	public static final String KEY_POSITION = "KEY_POSITION";
	private String url;
	private ArrayList<String> urls;
	private int position;
	private ViewPager mVp;
	boolean init = true;
	private PagerAdapter ipAdapter;
	private TextView tvCount;
	private int count;

	public void initData() {
		urls = getIntent().getStringArrayListExtra(KEY_IMG_ARRAY);
		position = getIntent().getIntExtra(KEY_POSITION, 1);
		count = urls.size();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBgColor( Color.TRANSPARENT );
		initData();
		initLayout();
	}


	public void initLayout() {
		setContentView( R.layout.wskcss_activity_show_img );
		mVp = (ViewPager) findViewById(R.id.pager);
		tvCount = (TextView) findViewById(R.id.tv_count);
		tvCount.setText((+1)+"/"+count);
		tvCount.setVisibility( count>1?View.VISIBLE:View.GONE );
		ipAdapter = new ImagePagerAdapter(getSupportFragmentManager());
		mVp.setAdapter(ipAdapter);
		mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				tvCount.setText((position=arg0)+1+"/"+count);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mVp.setCurrentItem(position);
	}
	private class ImagePagerAdapter extends FragmentStatePagerAdapter {


		public ImagePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return urls.size();
		}

		@Override
		public Fragment getItem(int position) {
			String imgUrl = urls.get(position);
			Fragment fragment = new ImageFragment();
			Bundle bundle = new Bundle(  );
			bundle.putString( "url",imgUrl );
			fragment.setArguments(bundle);
			return fragment;
		}
	}
	@SuppressLint("ValidFragment")
	public static class ImageFragment extends Fragment {
		private String url;
		private PhotoView mImageView;
		private ProgressBar progressBar;
		private ImageView ivReload;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			url= getArguments().getString( "url" );
			final View v = inflater.inflate(R.layout.wskcss_image_detail_fragment, container, false);
			progressBar = (ProgressBar) v.findViewById( R.id.progress_bar );
			mImageView = (PhotoView)v.findViewById(R.id.image);
			mImageView.setOnViewTapListener( new PhotoViewAttacher.OnViewTapListener() {
				@Override
				public void onViewTap(View view, float x, float y) {
					getActivity().finish();
				}
			} );
			ivReload = (ImageView) v.findViewById( R.id.iv_reload );
			loadImg();
			ivReload.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					loadImg();
				}
			} );
			return v;
		}
		private void loadImg(){
			progressBar.setVisibility( View.VISIBLE );
			ivReload.setVisibility( View.GONE );
			Glide.with(getActivity()).load(url).listener( new RequestListener<String, GlideDrawable>() {
				@Override
				public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
					ivReload.setVisibility( View.VISIBLE );
					progressBar.setVisibility( View.GONE );
					return false;
				}
				@Override
				public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
					progressBar.setVisibility( View.GONE );
					ivReload.setVisibility( View.GONE );
					return false;
				}
			} ).into( mImageView );
		}
	}

	public static void showImages(Activity activity,ArrayList urls,String url){
		int position = urls.indexOf(url);
		showImages( activity,urls,position );
	}
	public static void showImages(Activity activity,ArrayList urls){
		showImages( activity,urls,0 );
	}
	public static void showImage(Activity activity,String url){
		ArrayList<String> urls = new ArrayList<String>();
		urls.add(url);
		showImages(activity,urls,url);
	}
	public static void showImages(Activity activity,ArrayList urls,int position){
		Intent intent = new Intent(activity,ShowImageActivity.class);
		intent.putExtra(KEY_IMG_ARRAY, urls);
		intent.putExtra(KEY_POSITION, position);
		activity.startActivity(intent);
	}
}
