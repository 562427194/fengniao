package com.visionet.fengniao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.visionet.fengniao.Data;
import com.visionet.fengniao.R;
import com.visionet.fengniao.core.activity.ActionBarBaseActivity;
import com.visionet.fengniao.model.Insurance;
import com.visionet.fengniao.utils.ImageLoadUtil;
import com.visionet.fengniao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsuranceDetailActivity extends ActionBarBaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_center)
    ImageView ivCenter;
    @BindView(R.id.iv_bottom)
    ImageView ivBottom;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.tv_min_age_range)
    TextView tvMinAgeRange;
    @BindView(R.id.tv_min_price)
    TextView tvMinPrice;
    @BindView(R.id.tv_max_age_range)
    TextView tvMaxAgeRange;
    @BindView(R.id.tv_max_price)
    TextView tvMaxPrice;
    @BindView(R.id.tv_age_range_desc)
    TextView tvAgeRangeDesc;
    @BindView(R.id.tv_insurance_period)
    TextView tvInsurancePeriod;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    @BindView(R.id.tvguarantee)
    TextView tvguarantee;
    @BindView(R.id.cb_agree)
    CheckBox cbAgree;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_tk)
    TextView tvTk;
    @BindView(R.id.tv_sm)
    TextView tvSm;
    @BindView(R.id.tv_lc)
    TextView tvLc;

    private Insurance insurance;

    @Override
    public View createContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_insurance_detail, null);
        ButterKnife.bind(this, view);
        insurance = getIntent().getParcelableExtra("insurance");
        showInsurance(insurance);
        return view;
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.bt_buy:
                if (cbAgree.isChecked()) {
                    InsureActivity.toInsure(this, insurance);
                } else {
                    ToastUtils.toast(this, "请同意《投保须知》《保险条款》《特别说明》《理赔流程》");
                }

                break;
            case R.id.tv_notice:
                NoticeActivity.toNoticeActivity(this, Data.xz, tvNotice.getText().toString());
                break;
            case R.id.tv_lc:
                NoticeActivity.toNoticeActivity(this, Data.lc, tvLc.getText().toString());
                break;
            case R.id.tv_tk:
                if ("母婴安康生育保险".equals(insurance.getName())) {
                    NoticeActivity.toNoticeActivity(this, Data.tk_my, tvTk.getText().toString());
                }else{
                    NoticeActivity.toNoticeActivity(this, Data.tk, tvTk.getText().toString());
                }
                break;
            case R.id.tv_sm:
                NoticeActivity.toNoticeActivity(this, Data.sm, tvSm.getText().toString());
                break;
        }
    }

    private void showInsurance(Insurance insurance) {
        ImageLoadUtil.loadImage(this, insurance.getImgTop(), ivTop);
        tvName.setText(insurance.getName());
        setTitle(insurance.getName());
        tvMinPrice.setText("￥" + insurance.getMinPrice());
        tvMaxPrice.setText("￥" + insurance.getMaxPrice());

        tvAgeRangeDesc.setText(insurance.getAgreed());
        String priceDesc = tvPriceDesc.getText().toString();
        priceDesc = priceDesc.replace("minPrice", insurance.getMinPrice() + "");
        priceDesc = priceDesc.replace("maxPrice", insurance.getMaxPrice() + "");
        tvPriceDesc.setText(priceDesc);
        tvguarantee.setText(insurance.getGuarantee());
        if ("母婴安康生育保险".equals(insurance.getName())) {
            tvMaxAgeRange.setText("投保范围：16-35周岁");
            tvMinAgeRange.setText("投保范围：小于16周岁或大于35周岁");
            tvInsurancePeriod.setText("本合同的保险起见自被保险人办理入院手续之时起至被保险人产后第15日的24时止");
            tvPriceDesc.setText("16-35周岁保费为800元人民币；小于16周岁或大于35周岁保费为1200元人民币");
        }
        setClick(btBuy, tvNotice,tvLc,tvSm,tvTk);
    }

    public static void toInsuranceDetailActivity(Context context, Insurance insurance) {
        Intent intent = new Intent(context, InsuranceDetailActivity.class);
        intent.putExtra("insurance", insurance);
        context.startActivity(intent);
    }
}
