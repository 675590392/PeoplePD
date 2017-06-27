package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectAddDetailListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_add_detail_list)
    XRecyclerView rvAddDetailList;

    CommonAdapter adapter;
    private List<String> sfzs;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_add_detail_list;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("采集人列表");
    }

    @Override
    protected void initData() {
        sfzs = new ArrayList<String>();
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        rvAddDetailList.setLayoutManager(new LinearLayoutManager(mContext));
        rvAddDetailList.setItemAnimator(new DefaultItemAnimator());
        rvAddDetailList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<String>(mContext, R.layout.listview_detail_list_item, sfzs) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                holder.setText(R.id.textview_item_detail_name, "详情" + position);
                holder.setText(R.id.TextView_item_detail_idcard, "详情" + position);
                holder.setText(R.id.TextView_item_detail_cjr, "详情" + position);
                holder.setText(R.id.TextView_item_detail_cj_time, "详情" + position);


            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.setClass(SelectAddDetailListActivity.this, SelectAddDetailInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddDetailList.setAdapter(adapter);

    }
}
