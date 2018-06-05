package com.visionet.fengniao.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.visionet.fengniao.R;
import com.visionet.fengniao.activity.InsuranceDetailActivity;
import com.visionet.fengniao.activity.InsureActivity;
import com.visionet.fengniao.model.Insurance;
import com.visionet.fengniao.utils.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mybase.library.adapter.BaseListAdapter;
import mybase.library.view.RoundAngleImageView;

public class InsuranceAdapter extends BaseListAdapter<Insurance,InsuranceAdapter.InsuranceViewHolder> {

    public InsuranceAdapter(List<Insurance> data) {
        super(R.layout.item_insurance, data);
    }

    @Override
    protected void convert(InsuranceViewHolder viewHolder, final Insurance insurance) {
        super.convert(viewHolder, insurance);
        ImageLoadUtil.loadImage(mContext,insurance.getIcon(),viewHolder.rivCustomer);
        viewHolder.setText(R.id.tv_name, insurance.getName());
        viewHolder.setText(R.id.tv_price, "￥" + insurance.getMinPrice());
        viewHolder.btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsuranceDetailActivity.toInsuranceDetailActivity(mContext,insurance);
            }
        });
    }

    static class InsuranceViewHolder extends BaseViewHolder{
        @BindView(R.id.riv_customer)
        RoundAngleImageView rivCustomer;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age_range)
        TextView tvAgeRange;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.bt_buy)
        TextView btBuy;


        public InsuranceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
