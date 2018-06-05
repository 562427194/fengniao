package mybase.library.model;

/**
 * Created by zhenghai on 2016/12/21.
 */

public abstract class BaseEvent<T> {
    public static final int TYPE_DELETE = 1;

    public static final int TYPE_UPDATE= 2;

    public static final int TYPE_ADD= 3;


    private int type;

    private T t;

    public BaseEvent(int type, T  t){
        this.type = type;
        this.t = t;
    }

    public int getType(){
        return type;
    }
    public T getT(){
        return t;
    }
}
