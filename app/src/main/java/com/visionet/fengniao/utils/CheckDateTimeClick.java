/**
 *
 */
package com.visionet.fengniao.utils;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.visionet.fengniao.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mybase.library.util.DateFormatUtil;


/**
 * 日期时间选择控件 使用方法： private EditText inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012年9月3日 14:44",//初始日期时间值 在点击事件中使用：
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 *           dateTimePicKDialog=new
 *           DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 *           dateTimePicKDialog.dateTimePicKDialog(inputDate);
 *
 *           } });
 *
 * @author
 */
public class CheckDateTimeClick implements OnClickListener {
	public static final String DEF_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm";
	public static final String FA_TA = "dateTimePicker";

	private Dialog mDialog;
	private TextView tv;
	private String format = DEF_FORMAT;
	private String timeFormat = TIME_FORMAT;
	private String dateFormat = DATE_FORMAT;
	private boolean showTime = true;
	private boolean showDate = true;
	private String cancelString;

	public void setCheckDateTimeListener(CheckDateTimeListener checkDateTimeListener) {
		this.checkDateTimeListener = checkDateTimeListener;
	}


	private CheckDateTimeListener checkDateTimeListener;

	public CheckDateTimeClick(TextView tv,
			boolean showDate, boolean showTime) {
		this.tv = tv;
		this.showDate = showDate;
		this.showTime = showTime;
		init();
	}

	public void init() {
		if (TextUtils.isEmpty(tv.getText()))
			setText(Calendar.getInstance());
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


	public void setCancelString(String cancelString) {
		this.cancelString = cancelString;
	}


	public void setTv(TextView tv) {
		this.tv = tv;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setShowTime(boolean showTime) {
		this.showTime = showTime;
	}

	public void setShowDate(boolean showDate) {
		this.showDate = showDate;
	}

	@Override
	public void onClick(View v) {
		mDialog = createPicker();
		mDialog.show();
	}

	public Dialog createPicker() {
		if(mDialog!=null){
			return mDialog;
		}
		Dialog dialog =  new Dialog(tv.getContext(), R.style.bottom_dialog_style );
		LinearLayout dateTimeLayout = (LinearLayout) LayoutInflater.from( tv.getContext() )
				.inflate(R.layout.layout_date_time_pick, null);
		final DatePicker datePicker = (DatePicker) dateTimeLayout
				.findViewById( R.id.datepicker);
		datePicker.setVisibility(showDate ? View.VISIBLE : View.GONE);
		final TimePicker timePicker = (TimePicker) dateTimeLayout
		.findViewById(R.id.timepicker);
		timePicker.setVisibility(showTime ? View.VISIBLE : View.GONE);
		timePicker.setIs24HourView(true);
		initDateTimePicker(datePicker, timePicker, tv.getText().toString());
		dialog.setContentView(dateTimeLayout);
		TextView btConfirm = (TextView) dateTimeLayout
				.findViewById(R.id.tv_confirm);
		btConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth(),
						timePicker.getCurrentHour(),
						timePicker.getCurrentMinute());
				setText(calendar);
				mDialog.dismiss();
				if(checkDateTimeListener!=null){
					checkDateTimeListener.onConfirm(calendar);
				}
			}
		});
		TextView btCancel = (TextView) dateTimeLayout.findViewById(R.id.tv_cancel);
		if (cancelString != null) {
			btCancel.setText(cancelString);
		}
		btCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
				if(checkDateTimeListener!=null){
					Calendar calendar = Calendar.getInstance();
					calendar.set(datePicker.getYear(), datePicker.getMonth(),
							datePicker.getDayOfMonth(),
							timePicker.getCurrentHour(),
							timePicker.getCurrentMinute());
					checkDateTimeListener.onCancel(calendar);
				}
			}
		});
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity( Gravity.BOTTOM );
		dialogWindow.setWindowAnimations( mybase.library.R.style.bottom_dialog ); // 添加动画
		WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        lp.x = 0; // 新位置X坐标
//        lp.y = -20; // 新位置Y坐标
		lp.width = (int) tv.getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
////      lp.alpha = 9f; // 透明度
//        root.measure( 0, 0 );
//        lp.height = root.getMeasuredHeight();
		lp.alpha = 9f; // 透明度
		dialogWindow.setAttributes( lp );
		return dialog;
	}
	public void setText(Calendar calendar) {
		SimpleDateFormat sd = new SimpleDateFormat(getFmt());
		String text = sd.format(calendar.getTime());
		tv.setText(text);
	}

	private String getFmt() {
		String f = DEF_FORMAT;
		if ((!showDate) && showTime) {
			f = timeFormat;
		}
		if ((!showTime) && showDate) {
			f = dateFormat;
		}
		return f;
	}

	private void initDateTimePicker(DatePicker datePicker,
			TimePicker timePicker, String text) {
		Calendar calendar = getCalendarByText(text);
		datePicker.init(calendar.get(Calendar.YEAR),
				+calendar.get(Calendar.MONTH),
				+calendar.get(Calendar.DAY_OF_MONTH), null);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

	/**
	 * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
	 *
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	public Calendar getCalendarByText(String text) {
		Calendar calendar = Calendar.getInstance();
		if (!TextUtils.isEmpty(text)) {
			long time = DateFormatUtil.getDate(text, getFmt());
			calendar.setTimeInMillis(time);
		}
		return calendar;
	}
	public static interface CheckDateTimeListener{
		public void onConfirm(Calendar calendar);
		public void onCancel(Calendar calendar);
	}
}
