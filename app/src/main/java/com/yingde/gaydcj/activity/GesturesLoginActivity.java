package com.yingde.gaydcj.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.User;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.iml.UserModelIml;
import com.yingde.gaydcj.util.CommonUtil;
import com.yingde.gaydcj.util.Constant;
import com.yingde.gaydcj.util.SharedPreferencesUtil;
import com.yingde.gaydcj.util.ToastUtil;
import com.yingde.gaydcj.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tianditu.engine.TiandituSoftParam.mContext;

/**
 * 登录界面
 */
public class GesturesLoginActivity extends BaseActivity {
    //用户名
    @BindView(R.id.et_account)
    EditText etUsername;
    //密码
    @BindView(R.id.et_pswd)
    EditText etPassword;
    //登录按钮
    @BindView(R.id.login_urs)
    Button btnLogin;

    UserModelIml userModel;
    String appversion;
    private static final String FILE_NAME = "PEOPLE_PD";
    public static final int LOGIN_OK = 0;
    public static final int LOGIN_FAILED = 1;
    private SharedPreferences mSP;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_OK:
                    SharedPreferences.Editor editor = mSP.edit();
                    editor.putBoolean(Constant.LOGIN_STATE, true);
                    editor.commit();
                    //如果是登录成功的，之前未设置过手势锁的需要提示进入设置，之前设置过的也无需再次解锁。
                    String alp = mSP.getString(Constant.ALP, null);
                    if (TextUtils.isEmpty(alp)) {
                        createLockerView();
                    } else {
                        //打开主界面
                        openMainActivityAndFinish();
                    }
                    break;
                case LOGIN_FAILED:
                    Toast.makeText(GesturesLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        }
    });
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_gestureslogin;
    }


    @Override
    protected void initData() {
        userModel = new UserModelIml(GesturesLoginActivity.this);
        register();//注册广播，监听是否息屏
        VPNStartWay();
        appversion = CommonUtil.getVersionName(GesturesLoginActivity.this);

        mSP = getSharedPreferences(Constant.CONFIG_NAME, MODE_PRIVATE);
        etUsername = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_pswd);

        boolean bRemeber = mSP.getBoolean(Constant.LOGIN_STATE, false);
        if (bRemeber) {
            //已经是登录状态，如果之前未设置过手势锁则进入设置页面，如果之前设置过则进入解锁页面。
            OpenUnlockActivityAndWait();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        VPNStartWay();
    }


    //用户登录
    private void Login(final String username, String pwd,String deviceId) {
        if (!username.equals("") && !username.equals("")) {
            userModel.login(deviceId, username, pwd, new RequestListener<List<User>>() {
                @Override
                public void onSuccess(List<User> users, String token) {
                    Log.d("wh", "登录成功");
                    Message msg = Message.obtain();
                    msg.what = LOGIN_OK;
                    handler.sendMessage(msg);
                    if (token != null && !token.equals(""))//保存ID
                        CommonUtil.setTokenId(GesturesLoginActivity.this, token);
                    CommonUtil.setUserEntity(GesturesLoginActivity.this, users.get(0));
                    CommonUtil.getUserEntity(GesturesLoginActivity.this);
                    SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", username);
                    editor.commit();

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
            Message msg = Message.obtain();
            msg.what = LOGIN_FAILED;
            handler.sendMessage(msg);
            ToastUtil.showToast(GesturesLoginActivity.this, "请输入用户名密码");
        }
    }


    public void createLockerView() {
        PeopleApplication.getInstance().getLockPatternUtils().clearLock();
        Intent intent = new Intent(GesturesLoginActivity.this, CreateGestureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // 打开新的Activity
        startActivityForResult(intent, Constant.REQ_CREATE_PATTERN);
    }

    private void OpenUnlockActivityAndWait() {
        //登录成功后，如果没有设置过alp，则弹窗设置。否则则弹窗验证。
        String alp = mSP.getString(Constant.ALP, null);
        if (TextUtils.isEmpty(alp)) {
            PeopleApplication.getInstance().getLockPatternUtils().clearLock();
            Intent intent = new Intent(GesturesLoginActivity.this, CreateGestureActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // 打开新的Activity
            startActivityForResult(intent, Constant.REQ_CREATE_PATTERN);
        } else {
            Intent intent = new Intent(GesturesLoginActivity.this, UnlockGestureActivity.class);
            intent.putExtra("pattern", alp);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // 打开新的Activity
            startActivityForResult(intent, Constant.REQ_COMPARE_PATTERN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQ_CREATE_PATTERN:
                //创建手势
                switch (resultCode) {
                    case RESULT_OK:
                        byte[] pattern = data.getByteArrayExtra("pattern");
                        if (pattern != null) {
                            StringBuffer buffer = new StringBuffer();
                            for (byte c : pattern) {
                                buffer.append(c);
                            }
                            //保存创建的手势图
                            SharedPreferences.Editor editor = mSP.edit();
                            editor.putString(Constant.ALP, buffer.toString());
                            editor.commit();
                            openMainActivityAndFinish();
                        }
                        break;
                    case RESULT_CANCELED:
//                        Log.i(TAG, "user cancelled");
                        break;
                    default:
                        break;
                }

                break;
            case Constant.REQ_COMPARE_PATTERN:
                /*
                 * 注意！有四种可能出现情况的返回结果
		         */
                switch (resultCode) {
                    case RESULT_OK:
                        //用户通过验证，登录成功 打开主界面
                        openMainActivityAndFinish();
                        break;
                    case Constant.RESULT_FAILED:
                        //用户多次失败
//                        Log.i(TAG, "user failed");
                        break;
                    case Constant.RESULT_FORGOT_PATTERN:
                        // The user forgot the pattern and invoked your recovery Activity.
                        SharedPreferences.Editor editor = mSP.edit();
                        editor.putString(Constant.ALP, null);
                        editor.commit();
                        etPassword.setText("");
                        break;
                    case Constant.RESULT_ERRORS:
                        Util.toast(this, "发生异常，请重新登录");
                        break;
                    case Constant.RESULT_CHANGE_USER:
                        etUsername.setText("");
                        etPassword.setText("");
                        break;
                    default:
                        break;
                }

                break;
        }
    }

    private void openMainActivityAndFinish() {
        //打开主界面
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @OnClick({R.id.login_urs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_urs:
                Log.d("wh", "登录");
                //获取手机IMEI号，设备id
//                TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
//                String deviceId = tm.getDeviceId();
                Log.d("wh", "登录");
                //833521620800062  user03
                //863784020442351  user02
                //99000809487021  user04
                String deviceId = "833521620800062";
//        String username = etUsername.getText().toString();
//        String pwd = etPassword.getText().toString();
                String username = "user02";
                String pwd = "user02";
                Login(username, pwd,deviceId);
                break;
//            case R.id.btn_operation:
//                Intent intent1 = new Intent();
//                intent1.setClass(GesturesLoginActivity.this, HelpActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.linear_login:
//                InputMethodManager imm = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                break;
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

}
