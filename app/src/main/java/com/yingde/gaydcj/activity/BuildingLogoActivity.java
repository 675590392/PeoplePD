package com.yingde.gaydcj.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.entity.PersonLabel;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.util.MyToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BuildingLogoActivity extends BaseActivity {
    CommonAdapter adapter;
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.edt_build_logo_bm)
    EditText edtBuildLogoBm;
    @BindView(R.id.edt_build_logo_address)
    EditText edtBuildLogoAddress;
    @BindView(R.id.btn_reseach)
    Button btnReseach;
    @BindView(R.id.rv_build_logo_list)
    XRecyclerView rvBuildLogoList;
    private List<PersonLabel> personLabel;
    HouseModel houseModel;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_building_logo;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("房屋标识查询");
    }

    @Override
    protected void initData() {
        houseModel = new HouseModelIml(mContext);
    }

    @OnClick(R.id.btn_reseach)
    public void onClick() {
        personLabel = new ArrayList<PersonLabel>();
        rvBuildLogoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvBuildLogoList.setItemAnimator(new DefaultItemAnimator());
        rvBuildLogoList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<PersonLabel>(mContext, R.layout.listview_build_logo_list_item, personLabel) {
            @Override
            protected void convert(ViewHolder holder, PersonLabel personLabel, int position) {
                holder.setText(R.id.text_select_name,queryIsEmpty(personLabel.getDM()));
                holder.setText(R.id.text_select_sex,queryIsEmpty(personLabel.getNAME()));
                holder.setText(R.id.text_select_idCard,queryIsEmpty(personLabel.getJZDM()));
                holder.setText(R.id.text_select_address, queryIsEmpty(personLabel.getPCS6()));
                holder.setText(R.id.text_select_info_input, queryIsEmpty(personLabel.getTYPE()));
            }
        };
        rvBuildLogoList.setAdapter(adapter);
        queryHouseLabel();
    }
    /**
     * 获取房屋标识
     */
    private void queryHouseLabel() {
        houseModel.getHouseLabel(new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                personLabel.clear();
                personLabel.addAll(JSON.parseArray(json, PersonLabel.class));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
