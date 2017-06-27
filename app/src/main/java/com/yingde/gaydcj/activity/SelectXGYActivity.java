package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 协管员
 */
public class SelectXGYActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.Spinner_xgy)
    Spinner SpinnerXgy;
    @BindView(R.id.btn_critem)
    Button btnCritem;
    private ArrayAdapter<String> adapter_xgy;
    private List<String> xgyss;
    private int x;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_xgy;
    }

    @Override
    protected void initData() {
        xgyss = new ArrayList<String>();
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.xgy);
        adapter_xgy = new ArrayAdapter<String>(this,R.layout.spinner_style,
                mItems);
        adapter_xgy
                .setDropDownViewResource(R.layout.spinner_dropdown_item);
        SpinnerXgy.setAdapter(adapter_xgy);
        SpinnerXgy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                x = position;
                position++;
                // 点击处理事件
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("协管员");

    }

    @OnClick({R.id.btn_critem})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_critem:
                Intent intent=new Intent();
                intent.setClass(SelectXGYActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
