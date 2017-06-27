package com.yingde.gaydcj.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.entity.AppDownload;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.CommonUtil;
import com.yingde.gaydcj.AppUpdateManager.AppUpdateManager;
import com.yingde.gaydcj.util.Constant;
import com.yingde.gaydcj.util.SharedPreferencesUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置界面
 */
public class SetupActivity extends BaseActivity {

    @BindView(R.id.bt_update)
    Button bt_update;
    @BindView(R.id. bt_function)
    Button  bt_function;
    @BindView(R.id.bt_exit)
    Button  bt_exit;
    @BindView(R.id.app_version)
    TextView tv_vesion;
    @BindView(R.id.bt_text)
    TextView bt_text;
    @BindView(R.id.sp_scope)
    Spinner sp_scope;
    UserModelIml userModel;
    String appversion;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    private static final String FILE_NAME = "PEOPLE_PD";
    private int sp_index;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_setup;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("系统设置");
    }

    @Override
    protected void initData(){
        list.add("20");
        list.add("50");
        list.add("100");
        userModel = new UserModelIml(mContext);
        appversion= CommonUtil.getVersionName(SetupActivity.this);
        tv_vesion.setText("版本："+CommonUtil.getVersionName(this));
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        //第四步：将适配器添加到下拉列表上
        sp_scope.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        sp_index = sp.getInt("sp_index",0);
        //设置默认选中值
        sp_scope.setSelection(sp_index,true);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        sp_scope.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
                Log.d("wh","选择时间为="+list.get(arg2));
                SharedPreferencesUtil.setSetting(FILE_NAME,mContext,"scope",list.get(arg2));
                SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("scope", list.get(arg2));
                editor.putInt("sp_index",arg2 );
                editor.commit();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });
    }


    @OnClick({R.id.bt_update,R.id.bt_function,R.id.bt_exit,R.id.bt_text})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_update:
                downloadApp(appversion);
                break;
            case R.id.bt_function:
                Log.d("wh","功能介绍");
//                intent.setClass(SetupActivity.this, HelpActivity.class);
                intent=new Intent(SetupActivity.this,CommonScanActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_exit:
                Log.d("wh","退出");
                Intent MyIntent = new Intent(Intent.ACTION_MAIN);
                MyIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(MyIntent);
                android.os.Process.killProcess(android.os.Process.myPid());
                break;

            case R.id.bt_text:
                Log.d("wh","测试");
                intent=new Intent(SetupActivity.this,CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
                startActivity(intent);
                break;
        }

    }

    //app升级
    private void downloadApp(String AppVersion){
        userModel.downloadApp(AppVersion, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                List<AppDownload> AppDownload = JSON.parseArray(json, AppDownload.class);
                AppDownload.get(0).getAppAddress();
                Log.d("wh", "json=" + json);
                if (AppDownload.get(0).getAppAddress() != null) {
                    AppUpdateManager manager = new AppUpdateManager(
                            SetupActivity.this, AppDownload.get(0));
                    // 检查软件更新
                    manager.checkUpdate();
                }
            }
        });
    }
}
