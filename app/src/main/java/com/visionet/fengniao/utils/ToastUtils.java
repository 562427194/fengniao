package com.visionet.fengniao.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;
    public static void toastDemoERR(Context context){
        toast(context,"暂不提供实际操作功能");
    }
    public static void toast(Context context, String s){
        if(toast==null){
            toast = Toast.makeText(context,s,Toast.LENGTH_LONG);
        }else{
            toast.setText(s);
        }
        toast.show();

    };
}
