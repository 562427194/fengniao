package com.visionet.fengniao.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visionet.fengniao.Data;
import com.visionet.fengniao.R;
import com.visionet.fengniao.activity.ProductDetailActivity;
import com.visionet.fengniao.adapter.InsuranceAdapter;
import com.visionet.fengniao.adapter.ProductAdapter;
import com.visionet.fengniao.core.fragment.TabFragment;
import com.visionet.fengniao.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mybase.library.adapter.BaseListAdapter;


public class TabProductFragment extends TabFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_load)
    SwipeRefreshLayout srlLoad;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Unbinder unbinder;

    ProductAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager llm = new LinearLayoutManager( getContext() );
        llm.setOrientation( LinearLayoutManager.VERTICAL );
        rvList.setLayoutManager( llm );
        rvList.setItemAnimator( new DefaultItemAnimator() );
        adapter = adapter = new ProductAdapter(Data.PRODUCTS);
        adapter.addHeaderView(inflater.inflate(R.layout.layout_demo_text,null));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListenner(new BaseListAdapter.OnItemClickListenner<Product>() {
            @Override
            public void onItemClick(int position, Product product) {
                ProductDetailActivity.toProductDetailActivity(getContext(),product);
            }
        });
        srlLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srlLoad.setRefreshing(false);
                    }
                },300);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
