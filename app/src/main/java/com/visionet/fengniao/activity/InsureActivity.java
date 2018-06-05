package com.visionet.fengniao.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.visionet.fengniao.R;
import com.visionet.fengniao.core.activity.ActionBarBaseActivity;
import com.visionet.fengniao.model.Insurance;
import com.visionet.fengniao.utils.CheckDateTimeClick;
import com.visionet.fengniao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsureActivity extends ActionBarBaseActivity {

    @BindView(R.id.tv_insurance_num_type)
    TextView tvInsuranceNumType;
    @BindView(R.id.sp_insurance_num_type)
    Spinner spInsuranceNumType;
    @BindView(R.id.tv_insurance_to_num_type)
    TextView tvInsuranceToNumType;
    @BindView(R.id.sp_insurance_to_num_type)
    Spinner spInsuranceToNumType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.bt_search)
    Button btSearch;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_insurance_relation)
    TextView tvInsuranceRelation;
    @BindView(R.id.sp_insurance_relation)
    Spinner spInsuranceRelation;
    private Insurance insurance;

    @Override
    public View createContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_insure, null);
        ButterKnife.bind(this, view);
        insurance = getIntent().getParcelableExtra("insurance");
        init();
        return view;
    }

    private void init() {
        setTitle(R.string.insure);
        CheckDateTimeClick checkDateTimeClick = new CheckDateTimeClick(tvTime, true, false);
        tvTime.setText(null);
        tvTime.setOnClickListener(checkDateTimeClick);
        tvSpListener(tvInsuranceNumType, spInsuranceNumType);
        tvSpListener(tvInsuranceToNumType, spInsuranceToNumType);
        tvSpListener(tvInsuranceRelation,spInsuranceRelation);
        setClick(btSearch);
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.bt_search:
                ToastUtils.toastDemoERR(this);
                break;
        }
    }

    private void tvSpListener(final TextView tv, final Spinner sp) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sp.performClick();
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
                                       long id) {
                tv.setText(sp.getSelectedItem().toString());
                switch (sp.getId()) {
                    case R.id.sp_insurance_num_type:
                        break;
                    case R.id.sp_insurance_to_num_type:
                        break;
                    case R.id.sp_insurance_relation:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public static void toInsure(Context context, Insurance insurance) {
        Intent intent = new Intent(context, InsureActivity.class);
        intent.putExtra("insurance", insurance);
        context.startActivity(intent);
    }
}
