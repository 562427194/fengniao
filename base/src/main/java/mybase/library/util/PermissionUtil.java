package mybase.library.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

@TargetApi(23)
public class PermissionUtil {
	 Context context;

	public PermissionUtil(Context context) {
		this.context = context;
	}

	public  boolean validateHasPermissions(String... permissions) {
//		boolean result = false;
//		
//		if (Build.VERSION.SDK_INT >= 23) {
//			if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
//				result = true;
//			}
//		} else {
//			PackageManager pm = context.getPackageManager();
//			if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
//				result = true;
//			}
//		}
//		return result;
		return validateHasPermissions(false,permissions);
	}
	public  boolean getRequestPermissionRationale(String permission) {
		return ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
				permission);
	}
	public boolean validateHasPermissions(boolean requestPermission,String... permissions){
		boolean result = true;
		PackageManager pm = context.getPackageManager();
		ArrayList<String> list = new ArrayList();
		for (String permission : permissions){
			if (pm.checkPermission(permission, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
				result = false;
				list.add(permission);
			}
		}
		if(requestPermission&&!result){
			requestPermissions(list.toArray(new String[]{}));
		}
		return result;
	}
	public void requestPermissions(String... permissions){
		requestPermissions(0,permissions);
	}
	public void requestPermissions(int code,String... permissions){
		ActivityCompat	.requestPermissions((Activity) context,
				permissions,
				code);
	}
}
