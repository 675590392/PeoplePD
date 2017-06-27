package com.yingde.gaydcj.activity;

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

/**
 * 预警信息
 */
public class WarningInfoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_warning_info_list)
    XRecyclerView rvWarningInfoList;

    CommonAdapter adapter;
    private List<String> sfzs;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_warning_info;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("预警信息");
    }

    @Override
    protected void initData() {
        sfzs = new ArrayList<String>();
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        sfzs.add("aa");
        rvWarningInfoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvWarningInfoList.setItemAnimator(new DefaultItemAnimator());
        rvWarningInfoList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<String>(mContext, R.layout.listview_reminder_work_item, sfzs) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.text_num, position+"");
//                holder.setText(R.id.text_name, "张三"+position);
//                holder.setText(R.id.text_content, "通知公告内容"+position);
//                holder.setText(R.id.text_state, "已阅读"+position);
            }
        };

        rvWarningInfoList.setAdapter(adapter);

    }
}
