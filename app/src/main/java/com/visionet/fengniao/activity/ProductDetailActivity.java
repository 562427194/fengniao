package com.visionet.fengniao.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.visionet.fengniao.R;
import com.visionet.fengniao.core.activity.ActionBarBaseActivity;
import com.visionet.fengniao.core.activity.FNBaseActivity;
import com.visionet.fengniao.model.Product;
import com.visionet.fengniao.utils.ImageLoadUtil;
import com.visionet.fengniao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends ActionBarBaseActivity {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.iv_center)
    ImageView ivCenter;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.iv_bottom)
    ImageView ivBottom;

    private Product product;

    @Override
    public View createContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_product_detail, null);
        ButterKnife.bind(this,view);
        product = getIntent().getParcelableExtra("product");
        tvName.setText(product.getName());
        tvSpec.setText("规格："+product.getSpec());
        ImageLoadUtil.loadImage(this,product.getImageTop(),ivTop);
        ImageLoadUtil.loadImage(this,product.getImageBottom(),ivBottom);
        ImageLoadUtil.loadImage(this,product.getImageCenter(),ivCenter);
        setTitle(product.getName());
        setClick(btBuy);
        return view;
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.bt_buy:
                ToastUtils.toastDemoERR(this);
                break;
        }
    }

    public static void toProductDetailActivity(Context context, Product product){
        Intent intent = new Intent(context,ProductDetailActivity.class);
        intent.putExtra("product",product);
        context.startActivity(intent);
    }
}
