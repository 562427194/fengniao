package com.visionet.fengniao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.visionet.fengniao.R;
import com.visionet.fengniao.core.fragment.TabFragment;
import com.visionet.fengniao.utils.CheckDateTimeClick;
import com.visionet.fengniao.utils.ToastUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabSearchFragment extends TabFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Unbinder unbinder;
    @BindView(R.id.rb_insurnce_of)
    RadioButton rbInsurnceOf;
    @BindView(R.id.rb_insurnce_to)
    RadioButton rbInsurnceTo;
    @BindView(R.id.rg_insurancer)
    RadioGroup rgInsurancer;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private CheckDateTimeClick checkDateTimeClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        setClick(btSearch,tvTime);
        checkDateTimeClick = new CheckDateTimeClick(tvTime,true,false);
        checkDateTimeClick.setCheckDateTimeListener(new CheckDateTimeClick.CheckDateTimeListener() {
            @Override
            public void onConfirm(Calendar calendar) {

            }
            @Override
            public void onCancel(Calendar calendar) {
                tvTime.setText(null);
            }
        });
        tvTime.setText(null);
        rgInsurancer.check(R.id.rb_insurnce_of);
        return view;
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.bt_search:
                ToastUtils.toastDemoERR(getContext());
                break;
            case R.id.tv_time:
                checkDateTimeClick.onClick(view);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
