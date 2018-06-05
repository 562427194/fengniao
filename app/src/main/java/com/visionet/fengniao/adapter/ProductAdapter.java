package com.visionet.fengniao.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.visionet.fengniao.R;
import com.visionet.fengniao.model.Product;
import com.visionet.fengniao.utils.ImageLoadUtil;
import com.visionet.fengniao.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mybase.library.adapter.BaseListAdapter;

public class ProductAdapter extends BaseListAdapter<Product,ProductAdapter.ProductViewHolder> {


    public ProductAdapter(List<Product> data) {
        super(R.layout.item_product, data);
    }

    @Override
    protected void convert(ProductViewHolder viewHolder, Product product) {
        super.convert(viewHolder, product);
        ImageLoadUtil.loadImage(mContext,product.getIcon(),viewHolder.ivImage);
        viewHolder.tvName.setText(product.getName());
        viewHolder.tvPrice.setText(product.getPrice()+"");
        viewHolder.tvSpec.setText("规格："+product.getSpec());
        viewHolder.btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.toastDemoERR(mContext);
            }
        });
    }

    static class ProductViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_spec)
        TextView tvSpec;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.bt_buy)
        TextView btBuy;


        public ProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
