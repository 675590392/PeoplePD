package com.yingde.gaydcj.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAddDetailInfoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;

    CommonAdapter adapter;
    @BindView(R.id.rv_add_detail_info_list)
    XRecyclerView rvAddDetailInfoList;
    private List<String> sfzs;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_add_detail_info;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("采集人详情");
    }

    @Override
    protected void initData() {
        sfzs = new ArrayList<String>();
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        rvAddDetailInfoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvAddDetailInfoList.setItemAnimator(new DefaultItemAnimator());
        rvAddDetailInfoList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<String>(mContext, R.layout.listview_add_detail_item, sfzs) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.text_num, position+"");
            }
        };
        rvAddDetailInfoList.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
