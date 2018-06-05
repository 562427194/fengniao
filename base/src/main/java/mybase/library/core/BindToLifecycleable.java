package mybase.library.core;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by zhenghai on 2016/12/8.
 */

public interface BindToLifecycleable {
    public <T> LifecycleTransformer<T> bindToLifecycle();
}
