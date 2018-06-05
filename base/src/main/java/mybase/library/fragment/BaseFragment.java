package mybase.library.fragment;



import android.view.View;

import com.trello.rxlifecycle.components.support.RxFragment;

import mybase.library.core.BindToLifecycleable;
import mybase.library.util.MRxView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by zhenghai on 2016/12/8.
 */

public class BaseFragment extends RxFragment implements BindToLifecycleable {
    public void setClick(View... views){
        for (View view : views){
            if(view!=null){
                MRxView.clicks( view )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe( new Action1<View>() {
                            @Override
                            public void call(View view) {
                                onViewClick( view );
                            }
                        } );
            }
        }
    }
    public void onViewClick(View view){

    }
}
