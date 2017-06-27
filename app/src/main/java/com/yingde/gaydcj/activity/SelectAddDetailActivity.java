package com.yingde.gaydcj.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;
import android.view.View.OnClickListener;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectAddDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.bt_start)
    TextView btStart;
    @BindView(R.id.startdate)
    EditText startdate;
    @BindView(R.id.bt_end)
    TextView btEnd;
    @BindView(R.id.enddate)
    EditText enddate;
    @BindView(R.id.his_name_search)
    EditText hisNameSearch;
    @BindView(R.id.his_idnumber_search)
    EditText hisIdnumberSearch;
    @BindView(R.id.search)
    Button search;

    // 时间标识
    private int timeType = 0;
    // 定义6个记录当前时间的变量
    private int query_year = 0;
    private int query_month = 0;
    private int query_day = 0;
    private int query_hours = 0;
    private int query_minute = 0;
    private int query_second = 0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_add_detail;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("采集查询");
    }

    @Override
    protected void initData() {
        missTime();
    }
    // 时间弹出框
    private void missTimeDialog() {
        Calendar c = Calendar.getInstance();
        query_year = c.get(Calendar.YEAR);
        query_month = c.get(Calendar.MONTH);
        query_day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(SelectAddDetailActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        if(timeType == 1){
                            startdate.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        }else if(timeType == 2){
                            enddate.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");}
                    }
                }, query_year, query_month, query_day);
        datePicker.show();
    }

    private void missTime() {
        // 开始时间
        startdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                timeType = 1;
                missTimeDialog();
            }
        });
        // 结束时间
        enddate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                timeType = 2;
                missTimeDialog();
            }
        });

    }
    @OnClick(R.id.search)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(SelectAddDetailActivity.this, SelectAddDetailListActivity.class);
        startActivity(intent);
    }
}
