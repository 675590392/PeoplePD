package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.House;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;
import com.yingde.gaydcj.model.iml.HouseModelIml;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.MyToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yingde.gaydcj.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 房屋列表
 */
public class ComingSHPeopleListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_list)
    XRecyclerView rvList;
    CommonAdapter adapter;
    private List<String> sfzs;
    List<House> houses = new ArrayList<House>();
    ;//房屋类
    HouseModel houseModel;
    //    人员类
    UserModelIml userModel;
    private String mlpbm;
    private String gisOrPzType;
    private String selectNowStreets;
    private String edtSelectChshliveraddr;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_coming_shpeople_list;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("房屋列表");
    }

    @Override
    protected void initData() {
        houseModel = new HouseModelIml(mContext);
        userModel = new UserModelIml(mContext);
        //门派编码
        mlpbm = getIntent().getStringExtra("mlpbm");
        selectNowStreets = getIntent().getStringExtra("selectNowStreets");
        edtSelectChshliveraddr = getIntent().getStringExtra("edtSelectChshliveraddr");
        gisOrPzType = getIntent().getStringExtra("gisOrPzType");

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<House>(mContext, R.layout.listview_fwnjzr_item, houses) {
            @Override
            protected void convert(ViewHolder holder, final House houses, int position) {
//                ImageView checkDetail = holder.getView(R.id.img_check_detail);
                holder.setText(R.id.textview_item_mnph, queryIsEmpty(houses.getFWBM()));
                holder.setText(R.id.TextView_item_srdizhi, queryIsEmpty(houses.getMlphxx()));
                holder.setText(R.id.TextView_item_srrenshu, queryIsEmpty(houses.getSh()));
//                checkDetail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(ComingSHPeopleListActivity.this,
//                                ComingSHPeopleResidentialActivity.class);
//                        intent.putExtra("fwbm", houses.getFWBM());
//
//                        startActivity(intent);
//                    }
//                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(ComingSHPeopleListActivity.this,
                        ComingSHPeopleResidentialActivity.class);
                intent.putExtra("fwbm", houses.get(position-1).getFWBM());
                intent.putExtra("edtSelectChshliveraddr", edtSelectChshliveraddr);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvList.setAdapter(adapter);
        if (gisOrPzType.equals(PeopleApplication.GIS_TYPE_1004)) {
            // 根据门弄牌编码查找房屋列表
            if(mlpbm!=null){
                queryHouseListByMLP();
            }else{
                return;
            }

        } else if (gisOrPzType.equals(PeopleApplication.PZ_TYPE_1003)) {
            // 根据街路巷代码和门牌号查找房屋列表
            if (selectNowStreets!=null&&edtSelectChshliveraddr!=null) {
                queryHouseListByJLX();
            }else{
                return;
            }
        }

    }

    /**
     * 根据门弄牌编码查找房屋列表
     */
    private void queryHouseListByMLP() {
        houseModel.getHouseListByMLP(mlpbm, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                houses.clear();
                houses.addAll(JSON.parseArray(json, House.class));
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 根据街路巷代码和门牌号查找房屋列表
     */
    private void queryHouseListByJLX() {
        houseModel.getHouseListByJLX(selectNowStreets, edtSelectChshliveraddr, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                houses.clear();
                houses.addAll(JSON.parseArray(json, House.class));
                if(houses.size()==0){
                    ToastUtil.showToast(ComingSHPeopleListActivity.this, "无相关数据");
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


}
