package com.yingde.gaydcj.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.ChartEntity;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.UserModel;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.MyDateUtil;
import com.yingde.gaydcj.util.MyToolbar;
import com.yingde.gaydcj.util.ToastUtil;
import com.yingde.gaydcj.util.XYMarkerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BarChartActivity extends DemoBase {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.edt_chart_startdate)
    EditText edtChartStartdate;
    @BindView(R.id.edt_chart_enddate)
    EditText edtChartEnddate;
    @BindView(R.id.btn_chart_cirtem)
    Button btnChartCirtem;

    @BindView(R.id.chart1)
    BarChart mChart;
    // 时间标识
    private int timeType = 0;
    // 定义6个记录当前时间的变量
    private int query_year = 0;
    private int query_month = 0;
    private int query_day = 0;
    private int query_hours = 0;
    private int query_minute = 0;
    private int query_second = 0;

    private String workloadStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        ButterKnife.bind(this);
        if (toolbar != null) {
            initTitle();
        }
        //时间选择
        missTime();
        if (getIntent().getExtras() != null) {
            workloadStatistics = getIntent().getExtras().getString(PeopleApplication.WORKLOADSTATISTICS, "");
        }
        Calendar c = Calendar.getInstance();
        query_year = c.get(Calendar.YEAR);
        query_month = c.get(Calendar.MONTH);
        query_day = c.get(Calendar.DAY_OF_MONTH);
        // 转日期格式并传值到后台
        String startdateSZ = MyDateUtil.getMyDate(query_year+"年"+query_month+"月"+query_day+"日");//开始时间
        // 转日期格式并传值到后台
        String enddateSZ = MyDateUtil.getMyDate(query_year+"年"+(query_month+1)+"月"+query_day+"日");//结束时间
        if (TextUtils.isEmpty(startdateSZ)) {
            startdateSZ = "";
        }//12.118.190.99
        if (TextUtils.isEmpty(enddateSZ)) {
            enddateSZ = "";
        }
        if (workloadStatistics.equals(PeopleApplication.SIMPLE_WORK_STATIS_1005)) {
            //二十六、个人工作量统计—--根据时间段返回每一天的采集数
            simpleWorkRecordsByDate(startdateSZ,enddateSZ);
        } else if (workloadStatistics.equals(PeopleApplication.VILLAGE_WORK_STATIS_1006)) {
            // 二十七、居村委工作量统计—--根据时间段返回每一天的采集数
            workRecordsByDate(startdateSZ,enddateSZ);
        } else if (workloadStatistics.equals(PeopleApplication.SIMPLE_AVERAGE_WORK_STATIS_1007)) {
            // 二十八、居村委平均工作量统计—--根据时间段返回每一天的平均采集数
            workRecordsPerByDate(startdateSZ,enddateSZ);
        }
        //二十六、个人工作量统计—--根据时间段返回每一天的采集数
//        simpleWorkRecordsByDate("20170407","20170420");
        // 二十七、居村委工作量统计—--根据时间段返回每一天的采集数
//        workRecordsByDate("","");
        // 二十八、居村委平均工作量统计—--根据时间段返回每一天的平均采集数
//        workRecordsPerByDate("","");

    }

    // 时间弹出框
    private void missTimeDialog() {
        Calendar c = Calendar.getInstance();
        query_year = c.get(Calendar.YEAR);
        query_month = c.get(Calendar.MONTH);
        query_day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(BarChartActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        if (timeType == 1) {
                            edtChartStartdate.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        } else if (timeType == 2) {
                            edtChartEnddate.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        }
                    }
                }, query_year, query_month, query_day);
        datePicker.show();
    }

    private void missTime() {
        // 开始时间
        edtChartStartdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                timeType = 1;
                missTimeDialog();
            }
        });
        // 结束时间
        edtChartEnddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                timeType = 2;
                missTimeDialog();
            }
        });

    }

    private void initChart(final List<ChartEntity> chartEntities) {
        //柱状图阴影
        mChart.setDrawBarShadow(false);
        //值是否在柱状图上面
        mChart.setDrawValueAboveBar(true);
        //右方标注是否显示
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        //最大显示值数目
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        //拉伸
        mChart.setPinchZoom(false);
        //是否显示网状背景
        mChart.setDrawGridBackground(false);
        mChart.setDoubleTapToZoomEnabled(false);//双击缩放
//         mChart.setDrawYLabels(false);
        //格式化下方值
        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return chartEntities.get((int) value - 1).getYYYYMMDD();
            }
        };
//X轴的所有属性
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //字体
        xAxis.setTypeface(mTfLight);
        //网格状线
        xAxis.setDrawGridLines(false);
        //从1开始
        xAxis.setGranularity(1f); // only intervals of 1 day
        //标签数
        xAxis.setLabelCount(7);
        //X轴显示内容的格式化
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setEnabled(false);//设置轴是否被绘制。默认绘制,false不会被绘制。
//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        //左边Y轴属性
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        //距离顶部的间距
        leftAxis.setSpaceTop(15f);
        //y轴最小值
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        mChart.getAxisRight().setEnabled(false);//不显示Y
        //右边Y轴
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//柱子
        Legend l = mChart.getLegend();
        //从底部开始
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //从左边
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //横向
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //画在里面
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
//XY标记
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        //
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

    }


    private void setData(List<ChartEntity> chartEntities) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 1; i <= chartEntities.size(); i++) {
            ChartEntity chartEntity = chartEntities.get(i - 1);
            yVals1.add(new BarEntry(i, chartEntity.getCOUNT()));
        }
        BarData datas = new BarData();
        try{
            mChart.setData(datas);
            BarDataSet set1;
            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
                set1.setValues(yVals1);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                set1 = new BarDataSet(yVals1, "统计图");
                set1.setDrawIcons(false);
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTfLight);
                data.setBarWidth(0.9f);

                mChart.setData(data);
            }
        }catch(Exception e){
        }

    }


    /**
     * 二十六、个人工作量统计—--根据时间段返回每一天的采集数
     */
    private void simpleWorkRecordsByDate(String startdateSZ, String enddateSZ) {
        UserModel userModel = new UserModelIml(this);
        userModel.simpleWorkRecordsByDate(startdateSZ.toString().trim(), enddateSZ.toString().trim(), new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                ArrayList chartEntities=new ArrayList<ChartEntity>();
                chartEntities.clear();
                chartEntities.addAll(JSON.parseArray(json, ChartEntity.class));
                if(chartEntities.size()==0){
                    ToastUtil.showToast(BarChartActivity.this, "无数据");
                }
                initChart(chartEntities);
                setData(chartEntities);
                mChart.invalidate();
                ;

            }
        });
    }

    /**
     * 二十七、居村委工作量统计—--根据时间段返回每一天的采集数
     */
    private void workRecordsByDate(String startdateSZ, String enddateSZ) {
        UserModel userModel = new UserModelIml(this);
        userModel.workRecordsByDate(startdateSZ.toString().trim(), enddateSZ.toString(), new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                ArrayList chartEntities=new ArrayList<ChartEntity>();
                chartEntities.addAll(JSON.parseArray(json, ChartEntity.class));
                if(chartEntities.size()==0){
                    ToastUtil.showToast(BarChartActivity.this, "无数据");
                }
                initChart(chartEntities);
                setData(chartEntities);
                mChart.invalidate();
            }
        });
    }


    /**
     * 二十八、居村委平均工作量统计—--根据时间段返回每一天的平均采集数
     */
    private void workRecordsPerByDate(String startdateSZ, String enddateSZ) {
        UserModel userModel = new UserModelIml(this);
        userModel.workRecordsPerByDate(startdateSZ.toString().trim(), enddateSZ.toString(), new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                ArrayList chartEntities=new ArrayList<ChartEntity>();
                chartEntities.addAll(JSON.parseArray(json, ChartEntity.class));
                if(chartEntities.size()==0){
                    ToastUtil.showToast(BarChartActivity.this, "无数据");
                }
                initChart(chartEntities);
                setData(chartEntities);
                mChart.invalidate();
            }
        });
    }


    /**
     * 子activity用此方法代替
     * getSupportActionBar().setTitle("");
     */
    protected void initTitle() {
        if (toolbar != null) {
            toolbar.setTitle("统计图");
//            toolbar.setNavigationIcon(0);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            return;
        }
    }

    @OnClick(R.id.btn_chart_cirtem)
    public void onClick() {
        // 转日期格式并传值到后台
        String startdateSZ = MyDateUtil.getMyDate(edtChartStartdate
                .getText().toString().trim());//开始时间
        String enddateSZ = MyDateUtil.getMyDate(edtChartEnddate
                .getText().toString().trim());//结束时间
        if (TextUtils.isEmpty(startdateSZ)) {
            startdateSZ = "";
        }
        if (TextUtils.isEmpty(enddateSZ)) {
            enddateSZ = "";
        }
        if (workloadStatistics.equals(PeopleApplication.SIMPLE_WORK_STATIS_1005)) {
            //二十六、个人工作量统计—--根据时间段返回每一天的采集数
            simpleWorkRecordsByDate(startdateSZ, enddateSZ);
        } else if (workloadStatistics.equals(PeopleApplication.VILLAGE_WORK_STATIS_1006)) {
            // 二十七、居村委工作量统计—--根据时间段返回每一天的采集数
            workRecordsByDate(startdateSZ, enddateSZ);
        } else if (workloadStatistics.equals(PeopleApplication.SIMPLE_AVERAGE_WORK_STATIS_1007)) {
            // 二十八、居村委平均工作量统计—--根据时间段返回每一天的平均采集数
            workRecordsPerByDate(startdateSZ, enddateSZ);
        }
    }
}
