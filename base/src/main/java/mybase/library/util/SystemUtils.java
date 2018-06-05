package mybase.library.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;

/**
 * Created by admin on 2017/7/4.
 */

public class SystemUtils {
    /**
     * 发邮邮件
     *
     * @param activity
     * @param address
     */
    public static void showAddress(Activity activity, String address) {
        Uri mUri = Uri.parse("geo:0,0?q=" + address);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        activity.startActivity(mIntent);
    }

    /**
     * 拨打电话界面
     *
     * @param context
     * @param phone   电话号码
     */
    public static void dial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    public static void getImageFromCamera(Activity activity, String saveImagePath, int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(saveImagePath)));
            activity.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(activity, "未找到存储卡", Toast.LENGTH_LONG).show();
        }
    }

    public static void getImageFromCamera(Activity activity, int requestCode) {
        getImageFromCamera(activity, null, requestCode);
    }

    public static void getImageFromAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void cutImage(Activity activity, Uri uri, int requestCodeCut) {
        cutImage(activity, uri, requestCodeCut, 200, 200);
    }

    public static void cutImage(Activity activity, Uri uri, int requestCodeCut, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCodeCut);
    }
    public static void toWebApp(Context context,String url){
        Uri uri = Uri.parse( url );
        Intent intent = new Intent( Intent.ACTION_VIEW, uri );
        context.startActivity( intent );
    }
}
