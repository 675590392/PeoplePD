package com.yingde.gaydcj.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *查看详情以及注销
 */
public class ComingSHPeopleDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_add_detail_list)
    XRecyclerView rvAddDetailList;
    @BindView(R.id.btn_detail_remove)
    Button btnDetailRemove;

    CommonAdapter adapter;
    private List<String> sfzs;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_coming_shpeople_detail;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("人员注销");
    }

    @Override
    protected void initData() {
        sfzs = new ArrayList<String>();
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        rvAddDetailList.setLayoutManager(new LinearLayoutManager(mContext));
        rvAddDetailList.setItemAnimator(new DefaultItemAnimator());
        rvAddDetailList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<String>(mContext, R.layout.listview_detail_and_remove_item, sfzs) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.text_detail_asterisk, "*");
                holder.setText(R.id.text_detail_name, "姓名");
                holder.setText(R.id.text_detail_srname, "张三");
            }
        };
        rvAddDetailList.setAdapter(adapter);
    }
    /**
     * 注销按钮
     */
    @OnClick(R.id.btn_detail_remove)
    public void onClick() {
    }
}