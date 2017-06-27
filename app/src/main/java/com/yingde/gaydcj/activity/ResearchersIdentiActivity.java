package com.yingde.gaydcj.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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

/**
 * 人员标识查询
 */
public class ResearchersIdentiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.edt_reseach_identi_name)
    EditText edtReseachIdentiName;
    @BindView(R.id.edt_reseach_identi_idCard)
    EditText edtReseachIdentiIdCard;
    @BindView(R.id.rv_reseach_identi_list)
    XRecyclerView rvReseachIdentiList;
    @BindView(R.id.ly_reseach_identi)
    LinearLayout lyReseachIdenti;
    @BindView(R.id.btn_reseach)
    Button btnReseach;

    CommonAdapter adapter;
    private List<PersonLabel> personLabel;
    HouseModel houseModel;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_researchers_identi;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("人员标识查询");
    }

    @Override
    protected void initData() {
        houseModel = new HouseModelIml(mContext);
    }

    @OnClick({R.id.ly_reseach_identi, R.id.btn_reseach})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_reseach_identi:
                break;
            case R.id.btn_reseach:
                personLabel = new ArrayList<PersonLabel>();
                rvReseachIdentiList.setLayoutManager(new LinearLayoutManager(mContext));
                rvReseachIdentiList.setItemAnimator(new DefaultItemAnimator());
                rvReseachIdentiList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                adapter = new CommonAdapter<PersonLabel>(mContext, R.layout.listview_research_people_list_item, personLabel) {
                    @Override
                    protected void convert(ViewHolder holder, PersonLabel personLabel, int position) {
                        holder.setText(R.id.text_select_name,queryIsEmpty(personLabel.getDM()));
                        holder.setText(R.id.text_select_sex, queryIsEmpty(personLabel.getNAME()));
                        holder.setText(R.id.text_select_idCard,queryIsEmpty(personLabel.getJZDM()));
                        holder.setText(R.id.text_select_address,queryIsEmpty(personLabel.getPCS6()));
                        holder.setText(R.id.text_select_info_input,queryIsEmpty(personLabel.getTYPE()));
                    }
                };
                rvReseachIdentiList.setAdapter(adapter);
                queryPersonLabel();
                break;
        }
    }
    /**
     * 获取人员标识
     */
    private void queryPersonLabel() {
        houseModel.getPersonLabel(new RequestListener() {
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
