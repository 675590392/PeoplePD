package com.yingde.gaydcj.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.db.PeopleHandleWayDao;
import com.yingde.gaydcj.entity.db.LABELS;
import com.yingde.gaydcj.util.MyToolbar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 人房标识多选页面布局
 */
public class ComingSHPeopleAddMoreSelectActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.rv_add_more_select_list)
    RecyclerView rvAddMoreSelectList;
    @BindView(R.id.btn_more_select_confirm)
    Button btnMoreSelectConfirm;
    CommonAdapter adapter;
    private List<LABELS> labelsList;
    private PeopleHandleWayDao peopleHandleWayDao;
    int pos;
    String selectMoreBS;
    //标识
    private HashMap<Integer, String> hashMapLabel = new HashMap<>();
    private HashMap<Integer, String> hashMapLabelCode = new HashMap<>();
//    private HashMap<Integer, String> hashMapLabel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_coming_shpeople_add_more_select;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("多选项");

    }

    @Override
    protected void initData() {
        if (peopleHandleWayDao == null) {
            peopleHandleWayDao = new PeopleHandleWayDao();
        }
        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            pos = intent.getIntExtra("position", -1);
            selectMoreBS=intent.getStringExtra("selectMoreBS");
        }
        labelsList = new ArrayList<LABELS>();
        labelsList.clear();
        //房屋标识
        if(selectMoreBS.equals(PeopleApplication.SELECT_MORE_BS_HOUSE)){
            labelsList.addAll(peopleHandleWayDao.queryLABELSDictionaryHOUSELABLE());
            getLABELS();
        }else if(selectMoreBS.equals(PeopleApplication.SELECT_MORE_BS_PEOPLE)){
            //人员标识
            labelsList.addAll(peopleHandleWayDao.queryLABELSDictionaryPERSONLABLE());
            getLABELS();
        }
    }

    private void getLABELS() {

        rvAddMoreSelectList.setLayoutManager(new LinearLayoutManager(ComingSHPeopleAddMoreSelectActivity.this));
        rvAddMoreSelectList.setItemAnimator(new DefaultItemAnimator());
        rvAddMoreSelectList.addItemDecoration(new DividerItemDecoration(ComingSHPeopleAddMoreSelectActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<LABELS>(ComingSHPeopleAddMoreSelectActivity.this, R.layout.listview_add_more_select_item, labelsList) {
            @Override
            protected void convert(ViewHolder holder, final LABELS comingSHPeopleAdd, final int position) {
                CheckBox textSelect = holder.getView(R.id.chebox_item_more_select);
                //*号
                holder.setText(R.id.chebox_item_more_select, comingSHPeopleAdd.getNAME());
                textSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            hashMapLabel.put(position, comingSHPeopleAdd.getNAME());
                            hashMapLabelCode.put(position, comingSHPeopleAdd.getDM());
                        } else {
                            hashMapLabel.remove(position);
                            hashMapLabelCode.remove(position);
                        }
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvAddMoreSelectList.setAdapter(adapter);
    }
    @OnClick(R.id.btn_more_select_confirm)
    public void onClick() {
        //获取选择的名称
        Iterator iterator = hashMapLabel.keySet().iterator();
        String datas = "";
        while (iterator.hasNext()) {
            int id = (int) iterator.next();
            String strAnswer = hashMapLabel.get(id);
            datas = strAnswer + "&" + datas;
        }
        Log.e("datas",datas);
        //截取最后一个字符
        String datasLast = datas.substring(datas.length() - 1, datas.length());
        String datasStr = null;
        if (datasLast.equals("&")) {
            // 最后一个字符被截取掉
            datasStr = datas.substring(0, datas.length() - 1);
        }
        Log.e("datasStr",datasStr);

        //获取选择的编码
        Iterator iteratorCode = hashMapLabelCode.keySet().iterator();
        String datasCode = "";
        while (iteratorCode.hasNext()) {
            int id = (int) iteratorCode.next();
            String strAnswer = hashMapLabelCode.get(id);
            datasCode = strAnswer + "&" + datasCode;
        }

        Log.e("datasCode",datasCode);
        //截取最后一个字符
        String datasLastCode = datasCode.substring(datasCode.length() - 1, datasCode.length());
        String datasStrCode = null;
        if (datasLastCode.equals("&")) {
            // 最后一个字符被截取掉
            datasStrCode = datasCode.substring(0, datasCode.length() - 1);
        }
        Log.e("datasStrCode",datasStrCode);
        Intent intent = new Intent();
        intent.putExtra("position", pos);
        intent.putExtra("name", datasStr);
        intent.putExtra("code", datasStrCode);
        setResult(PeopleApplication.SELECT_MORE_BACK, intent);
        finish();
    }
}
