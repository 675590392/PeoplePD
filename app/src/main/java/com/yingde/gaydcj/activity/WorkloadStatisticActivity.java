package com.yingde.gaydcj.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.util.MyToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkloadStatisticActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.ly_simple_workload_statistics)
    LinearLayout lySimpleWorkloadStatistics;
    @BindView(R.id.ly_village_workload_statistics)
    LinearLayout lyVillageWorkloadStatistics;
    @BindView(R.id.ly_village_average_workload_statistics)
    LinearLayout lyVillageAverageWorkloadStatistics;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_workload_statistic;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("工作统计");


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ly_simple_workload_statistics, R.id.ly_village_workload_statistics, R.id.ly_village_average_workload_statistics})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ly_simple_workload_statistics:
                intent = new Intent(mContext, BarChartActivity.class);
                intent.putExtra(PeopleApplication.WORKLOADSTATISTICS, PeopleApplication.SIMPLE_WORK_STATIS_1005);
                startActivity(intent);
                break;
            case R.id.ly_village_workload_statistics:
                 intent = new Intent(mContext, BarChartActivity.class);
                intent.putExtra(PeopleApplication.WORKLOADSTATISTICS, PeopleApplication.VILLAGE_WORK_STATIS_1006);
                startActivity(intent);
                break;
            case R.id.ly_village_average_workload_statistics:
                 intent = new Intent(mContext, BarChartActivity.class);
                intent.putExtra(PeopleApplication.WORKLOADSTATISTICS, PeopleApplication.SIMPLE_AVERAGE_WORK_STATIS_1007);
                startActivity(intent);
                break;
        }
    }
}
