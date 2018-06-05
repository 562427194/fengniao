package mybase.library.adapter;

import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhenghai on 2016/12/18.
 */

public abstract class BaseListAdapter<T,K extends BaseViewHolder> extends BaseQuickAdapter<T,K> {
    public BaseListAdapter(int layoutResId, List<T> data) {
        super( layoutResId, data );
    }

    public BaseListAdapter(List<T> data) {
        super( data );
    }

    @Override
    protected void convert(final K helper, final T item) {
        helper.getConvertView().setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListenner!=null)
                onItemClickListenner.onItemClick( helper.getOldPosition(), item );
            }
        } );
    }

    private OnItemClickListenner onItemClickListenner;

    public void setOnItemClickListenner(OnItemClickListenner<T> onItemClickListenner) {
        this.onItemClickListenner = onItemClickListenner;
    }

    public OnItemClickListenner getOnItemClickListenner() {
        return onItemClickListenner;
    }

    public interface OnItemClickListenner<T> {

        public void onItemClick(int position, T t);

    }
}
