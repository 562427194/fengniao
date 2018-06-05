package mybase.library.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

import static com.jakewharton.rxbinding.internal.Preconditions.checkNotNull;
import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

/**
 * Created by zhenghai on 2016/12/1.
 */

public class MRxView {

    @CheckResult
    @NonNull
    public static Observable<View> clicks(@NonNull View view) {
        checkNotNull( view, "view == null" );
        return Observable.create( new ViewClickOnSubscribe( view ) );
    }

    static final class ViewClickOnSubscribe implements Observable.OnSubscribe<View> {
        final View view;

        ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void call(final Subscriber<? super View> subscriber) {
            checkUiThread();

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext( view );
                    }
                }
            };
            view.setOnClickListener( listener );

            subscriber.add( new MainThreadSubscription() {
                @Override
                protected void onUnsubscribe() {
                    view.setOnClickListener( null );
                }
            } );
        }
    }
}
