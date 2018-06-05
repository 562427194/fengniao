package mybase.library.util;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mybase.library.R;

/**
 * Created by zhenghai on 2016/12/5.
 */

public class DialogUtils {
    public static SweetAlertDialog createProgressDialog(Context context,String msg){
        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText( msg );
        return  dialog;
    }
    public static SweetAlertDialog createConfirmDialog(Context context,String msg){
        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText( msg );
        dialog.setConfirmText( context.getString( R.string.confirm ) );
        dialog.setCancelText(context.getString(  R.string.cancel  ));
        return  dialog;
    }
    public static SweetAlertDialog createConfirmDialog(Context context,int msgId){
        return createConfirmDialog( context,context.getString( msgId ) );
    }
    public static SweetAlertDialog createProgressDialog(Context context,int msgId){
        return createProgressDialog( context,context.getString( msgId ) );
    }
    public static SweetAlertDialog createProgressDialog(Context context){
        return createProgressDialog( context,context.getString( R.string.zbase_please_wait ) );
    }
    public static Dialog getBomttomInputDialog(Context context,final OnConfirmListener comfirm) {
        final Dialog dialog = new Dialog( context, R.style.my_dialog );
        final LinearLayout root = (LinearLayout) LayoutInflater.from( context ).inflate( R.layout.layout_bottom_input, null );
        final EditText etContent = (EditText) root.findViewById( R.id.et_content );
        etContent.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                root.findViewById( R.id.tv_confirm ).setEnabled( !TextUtils.isEmpty( editable ) );
            }
        } );
        root.findViewById( R.id.tv_confirm ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comfirm.onConfirm( etContent.getText().toString() );
                etContent.setText( null );
                dialog.dismiss();
            }
        } );
        root.findViewById( R.id.tv_cancel ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
        dialog.setContentView( root );
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM );
        dialogWindow.setWindowAnimations( R.style.bottom_dialog ); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        lp.x = 0; // 新位置X坐标
//        lp.y = -20; // 新位置Y坐标
        lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
////      lp.alpha = 9f; // 透明度
//        root.measure( 0, 0 );
//        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes( lp );
        return dialog;
    }

    public static interface OnConfirmListener{
        public void onConfirm(String content);
    }


}
