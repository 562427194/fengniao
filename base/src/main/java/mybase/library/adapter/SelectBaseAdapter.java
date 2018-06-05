package mybase.library.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhenghai on 2016/12/16.
 */

public abstract class SelectBaseAdapter<T extends SelectBaseAdapter.EquastBean> extends BaseListAdapter<T,BaseViewHolder> {
    private ArrayList<T> selecteds = new ArrayList();
    private int cbId;

    public SelectBaseAdapter(int layoutResId,int checkBoxId, List<T> data) {
        super( layoutResId, data );
        this.cbId = checkBoxId;
    }

    public void setSelecteds(ArrayList<T> selecteds){
        if(selecteds!=null){
            this.selecteds = selecteds;
        }
    }
    public void addSelected(T t){
        if(t!=null){
            this.selecteds.add( t );
            notifyDataSetChanged();
        }
    }
    public void removeSelected(T t){
        if(t!=null){
            this.selecteds.remove( t );
            notifyDataSetChanged();
        }
    }

    public ArrayList<T> getSelecteds() {
        return selecteds;
    }

    @Override
    protected void convert(BaseViewHolder holder,final T t) {
        super.convert( holder,t );
        CheckBox checkBox = holder.getView(cbId);
        checkBox.setChecked(selecteds.contains( t ));
        checkBox.setClickable( false );
        holder.getConvertView().setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selecteds.contains( t )){
                    removeSelected( t );
                }else{
                    addSelected( t );
                }
            }
        } );
    }
    public static interface EquastBean{
        public boolean equals(Object o);
    }
}
