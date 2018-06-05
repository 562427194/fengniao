package mybase.library.view;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import mybase.library.R;
import mybase.library.util.PermissionUtil;


@SuppressLint("NewApi")
public class RecordButton extends Button  {
	private String savePath;
	public RecordButton(Context context) {
		super(context);
		init(context);
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setSavePath(String path) {
		savePath = path;
	}
	private boolean hasPermission;
	public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
		finishedListener = listener;
	}

	private String mFileName = null;

	private OnFinishedRecordListener finishedListener;

	private static final int MIN_INTERVAL_TIME = 1000;// 2s
	private long startTime;


	/**
	 * 取消语音发送
	 */
	private Dialog recordIndicator;

	private static int[] res = { R.mipmap.wskcss_record_volum1, R.mipmap.wskcss_record_volum2,
		R.mipmap.wskcss_record_volum3, R.mipmap.wskcss_record_volum4,R.mipmap.wskcss_record_volum5,R.mipmap.wskcss_record_volum6,R.mipmap.wskcss_record_volum7,R.mipmap.wskcss_record_volum8};

	private  View view;
	
	private  ImageView ivVolum,ivRecording;

	private TextView recordingMsg;
	
	private MediaRecorder recorder;

	private ObtainDecibelThread thread;

	private Handler volumeHandler;
	
	public final static int   MAX_TIME =60;//一分钟
	private PermissionUtil permissionUtil;
	private void init(Context context) {
		volumeHandler = new ShowVolumeHandler();
		permissionUtil = new PermissionUtil(context);
		savePath = context.getFilesDir().getPath();
	}
	private boolean cancel = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mFileName = savePath + 	"/" + System.currentTimeMillis() + ".amr";
			hasPermission = permissionUtil.validateHasPermissions(true,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if(hasPermission) {
				initDialogAndStartRecord();
			}else{
				return false;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float mx = this.getX();
			float my = this.getY();
			float ex = event.getX();
			float ey = event.getY();
			
			boolean c = (ex<mx)||((my-ey)>100);
			
			if(c){
				if(c!=cancel){
					recordingMsg.setText(R.string.wskcss_loosen_finger_cancel_record);
					this.setText(R.string.wskcss_loosen_finger_cancel_record);
					ivRecording.setImageResource(R.mipmap.wskcss_img_record_cancel );
					ivVolum.setVisibility(View.GONE);
					cancel = c;
				}
			}else{
				if(c!=cancel){
					recordingMsg.setText(R.string.wskcss_slide_cancel_record);
					ivRecording.setImageResource(R.mipmap.wskcss_img_recording );
					ivVolum.setVisibility(View.VISIBLE);
					this.setText(R.string.wskcss_loosen_finger_to_send);
					cancel = c;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(hasPermission){
				if(cancel){
					cancelRecord();
				}else{
					finishRecord();
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:// 当手指移动到view外面，会cancel
			cancelRecord();
			break;
		}

		return true;
	}

	private void initDialogAndStartRecord() {
			setText(R.string.wskcss_loosen_finger_to_send);
			recordIndicator = new Dialog(getContext(),R.style.like_toast_dialog_style);
			view = LayoutInflater.from(getContext()).inflate(R.layout.wskcss_layout_record_dialog, null);
			ivVolum = (ImageView) view.findViewById(R.id.iv_record_volume);
			ivRecording = (ImageView) view.findViewById(R.id.iv_record_main);
			recordingMsg = (TextView) view.findViewById(R.id.tv_recording_msg);
			recordIndicator.setContentView(view, new LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			recordIndicator.setOnDismissListener(onDismiss);
			LayoutParams lp = recordIndicator.getWindow().getAttributes();
			lp.gravity = Gravity.CENTER;
			startTime = System.currentTimeMillis();
			startRecording();
			recordIndicator.show();
			duration();


	}
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			if(temp==0){
				finishRecord();
				return;
			}
			if(!cancel)
			recordingMsg.setText(getResources().getString(R.string.wskcss_record)+temp+getContext().getString(R.string.wskcss_minuts_than_send));
		}
	};
	private int temp;
	private void duration(){
		temp = 60;
		Runnable run = new Runnable(){

			@Override
			public void run() {
				while(recording&&temp!=-1){
					if(temp<=10)
					mHandler.sendEmptyMessage(temp);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					temp--;
				}
			}
			
		};
		new Thread(run).start();
	}
	private void finishRecord() {
		this.setText(R.string.wskcss_touch_to_record);
		stopRecording();
		recordIndicator.dismiss();
		long intervalTime = System.currentTimeMillis() - startTime;
		if (intervalTime < MIN_INTERVAL_TIME) {
			Toast.makeText(getContext().getApplicationContext(),R.string.wskcss_under_time, Toast.LENGTH_SHORT).show();
			File file = new File(mFileName);
			file.delete();
			return;
		}
		if (finishedListener != null)
			finishedListener.onFinishedRecord(mFileName,(int) (intervalTime/1000));
	}

	private void cancelRecord() {
		this.setText(R.string.wskcss_touch_to_record);
		stopRecording();
		recordIndicator.dismiss();
		Toast.makeText(getContext().getApplicationContext(), R.string.wskcss_cancel_record, Toast.LENGTH_SHORT).show();
		File file = new File(mFileName);
		file.delete();
	}

	private void startRecording() {
		recording = true;
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setAudioChannels(1);
		recorder.setAudioEncodingBitRate(4000);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(mFileName);

		try {
			recorder.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}

		recorder.start();
		thread = new ObtainDecibelThread();
		thread.start();

	}
	private boolean recording;
	private void stopRecording() {
		recording = false;
		if (thread != null) {
			thread.exit();
			thread = null;
		}
		if (recorder != null) {
			recorder.stop();
			recorder.release();
			recorder = null;
		}
	}

	private class ObtainDecibelThread extends Thread {

		private volatile boolean running = true;

		public void exit() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (recorder == null || !running) {
					break;
				}
				int x = recorder.getMaxAmplitude();
				if (x != 0) {
					int f = (int) (10 * Math.log(x) / Math.log(10));
					if(f<26){
						volumeHandler.sendEmptyMessage(0);
					}else if (f < 32)
						volumeHandler.sendEmptyMessage(1);
					else if (f < 38)
						volumeHandler.sendEmptyMessage(2);
					else if (f < 44)
						volumeHandler.sendEmptyMessage(3);
					else if (f < 50)
						volumeHandler.sendEmptyMessage(4);
					else if (f < 56)
						volumeHandler.sendEmptyMessage(5);
					else if (f < 62)
						volumeHandler.sendEmptyMessage(6);
					else
						volumeHandler.sendEmptyMessage(7);

				}

			}
		}

	}

	private OnDismissListener onDismiss = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			stopRecording();
		}
	};

	class ShowVolumeHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			ivVolum.setImageResource(res[msg.what]);
		}
	}

	public interface OnFinishedRecordListener {
		public void onFinishedRecord(String audioPath, int time);
	}

}
