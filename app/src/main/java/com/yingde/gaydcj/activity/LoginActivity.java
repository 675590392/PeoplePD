package com.yingde.gaydcj.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.alibaba.fastjson.JSON;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.topsec.sslvpn.IVPNHelper;
import com.topsec.sslvpn.OnAcceptResultListener;
import com.topsec.sslvpn.OnAcceptSysLogListener;
import com.topsec.sslvpn.datadef.BaseAccountInfo;
import com.topsec.sslvpn.datadef.BaseCaptchaInfo;
import com.topsec.sslvpn.datadef.BaseConfigInfo;
import com.topsec.sslvpn.datadef.BaseModule;
import com.topsec.sslvpn.datadef.BaseResourceInfo;
import com.topsec.sslvpn.datadef.LogLevel;
import com.topsec.sslvpn.datadef.ServiceAuthCfg;
import com.topsec.sslvpn.datadef.VPNStaus;
import com.topsec.sslvpn.datadef.WorkModel;
import com.topsec.sslvpn.datadef.eExtraCodeType;
import com.topsec.sslvpn.datadef.eLoginType;
import com.topsec.sslvpn.datadef.eOperateType;
import com.topsec.sslvpn.datadef.pf.ResourceInfoForConnect;
import com.topsec.sslvpn.lib.TrafficStatistic;
import com.topsec.sslvpn.lib.VPNService;
import com.topsec.sslvpn.util.FeatureCodeHelper;
import com.topsec.sslvpn.util.Loger;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.AppUpdateManager.AppUpdateManager;
import com.yingde.gaydcj.entity.AppDownload;
import com.yingde.gaydcj.entity.User;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.CommonUtil;
import com.yingde.gaydcj.util.ToastUtil;

import java.io.InvalidObjectException;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableRow;
import android.widget.Toast;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    //用户名
    @BindView(R.id.et_username)
    EditText etUsername;
    //密码
    @BindView(R.id.et_password)
    EditText etPassword;
    //登录按钮
    @BindView(R.id.btn_login)
    Button btnLogin;
    //操作手册
    @BindView(R.id.btn_operation)
    Button btnOperation;
    @BindView(R.id.linear_login)
    LinearLayout linearLogin;

    UserModelIml userModel;
    String appversion;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }


    @Override
    protected void initData() {
        userModel = new UserModelIml(mContext);
        register();//注册广播，监听是否息屏
        appversion = CommonUtil.getVersionName(LoginActivity.this);
        //VPN调用方法
//        downloadApp(appversion);
        VPNStartWay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VPNStartWay();
    }

    @OnClick({R.id.btn_login, R.id.btn_operation, R.id.linear_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//Android6.0需要动态获取权限
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////            toast("需要动态获取权限");
//                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
//                }else{
////            toast("不需要动态获取权限");
//                    TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//                    String deviceId  = TelephonyMgr.getDeviceId();
//                }
                //获取手机IMEI号，设备id
                TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
                String deviceId = tm.getDeviceId();
                String username = etUsername.getText().toString();
                String pwd = etPassword.getText().toString();

                if (!username.equals("") && !username.equals("")) {
                    //833521620800062  user03
                    //863784020442351  user02
                    //99000809487021  user04
                    userModel.login("833521620800062", username, pwd, new RequestListener<List<User>>() {
                        @Override
                        public void onSuccess(List<User> users, String token) {
                            Log.d("wh", "登录成功");
                            if (token != null && !token.equals(""))//保存ID
                                CommonUtil.setTokenId(mContext, token);
//                            Log.e("",users.get(0).getApplattice());
                            CommonUtil.setUserEntity(mContext, users.get(0));
                            CommonUtil.getUserEntity(mContext);
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);

                            //保存登记号
//                            userModel.setTokenId(token);
//                            userModel.getRegCode(new RequestListener<List<Map>>() {
//                                @Override
//                                public void onSuccess(List<Map> maps, String token) {
//                                    String regcode = (String) maps.get(0).get("REGCODE");//登记号
//                                    CommonUtil.setRegCode(mContext, regcode);
//                                }
//                            });
                        }
                    });
                } else {
                    ToastUtil.showToast(mContext, "请输入用户名密码");
                }
                break;
            case R.id.btn_operation:
                Intent intent1 = new Intent();
                intent1.setClass(LoginActivity.this, HelpActivity.class);
                startActivity(intent1);
                break;
            case R.id.linear_login:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }


    private void register() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        registerReceiver(mBatInfoReceiver, filter);
    }

    String TAG = "log";

    BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            Log.d(TAG, "onReceive");
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {// 开屏
                VPNStartWay();
                Log.d(TAG, "screen on");
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {// 锁屏
                closeVPNService();
                Log.d(TAG, "screen off");
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {// 解锁
                Log.d(TAG, "screen unlock");
            } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                Log.i(TAG, " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeVPNService();
            System.gc();
            finish();
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            closeVPNService();
        }
        return false;
    }

    //app升级
    private void downloadApp(String AppVersion) {
        userModel.downloadApp(AppVersion, new RequestListener() {
            @Override
            public void onSuccess(Object o, String token) {
                String json = JSON.toJSONString(o);
                List<AppDownload> AppDownload = JSON.parseArray(json, AppDownload.class);
                AppDownload.get(0).getAppAddress();
                Log.d("wh", "json=" + json);
                if (AppDownload.get(0).getAppAddress() != null) {
                    AppUpdateManager manager = new AppUpdateManager(
                            LoginActivity.this, AppDownload.get(0));
                    // 检查软件更新
                    manager.checkUpdate();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
