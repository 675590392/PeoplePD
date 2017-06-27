package com.yingde.gaydcj.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.topsec.sslvpn.IVPNHelper;
import com.topsec.sslvpn.OnAcceptResultListener;
import com.topsec.sslvpn.OnAcceptSysLogListener;
import com.topsec.sslvpn.datadef.BaseAccountInfo;
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
import com.topsec.sslvpn.datadef.VPNStaus;
import com.yingde.gaydcj.R;
import com.yingde.gaydcj.http.CommonApi;
import com.yingde.gaydcj.http.RetrofitClient;
import com.yingde.gaydcj.util.ActivityCollector;
import com.yingde.gaydcj.util.MyToolbar;

import java.io.InvalidObjectException;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements OnAcceptSysLogListener, OnAcceptResultListener {

    protected MyToolbar toolbar;
    protected T viewDataBinding;
    protected CommonApi commonApi;
    protected Context mContext;

    //VPN调用
    String TAG = "log";
    public static IVPNHelper m_ihVPNService = null;

    ServiceAuthCfg m_sacAuthCfgInfo = null;
    public static int m_iWorkModule = BaseModule.SSLVPN_NETACCESS;
    public static BaseResourceInfo[] m_briArrayResInfo = null;
    public static ResourceInfoForConnect[] m_rifcpArrayConnectResInfo = null;
    VPNStaus m_iVPNStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutResID());
        ButterKnife.bind(this);//在setContentView后执行

        mContext = this;
        commonApi = RetrofitClient.getCommonApi();

        toolbar = (MyToolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTitle();
//        VPNStartWay();
    }

    protected abstract int getLayoutResID();

    protected abstract void initData();

    /**
     * 子activity用此方法代替
     * getSupportActionBar().setTitle("");
     */
    protected void initTitle() {
        if (toolbar != null) {
            toolbar.setTitle("");
//            toolbar.setNavigationIcon(0);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
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

    protected void closeVPNService() {
        m_ihVPNService.closeService();
    }

    /**
     * 检查是否有网络
     *
     * @return
     */
    protected boolean checkNetwork() {
        ConnectivityManager cwjManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        boolean flag = info != null && info.isAvailable();
        if (!flag)
//            ToastUtil.showToast(this, "无法连接网络，请检查设置");
            new AlertDialog.Builder(this)
                    .setTitle("小贴士")
                    .setMessage("网络异常，请检查网络")
                    .setPositiveButton("确定", null)
                    .show();

        return flag;
    }

    /**
     * 判断为空
     */
    protected String queryIsEmpty(String textValue) {
        if (!TextUtils.isEmpty(textValue)) {
            return textValue;
        } else {
            return "";
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            closeVPNService();
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    protected void VPNStartWay() {

        //第一步，使用VPNService.getVPNInstance(this.getApplicationContext())初始化全局唯一实例，建议在Application类中实例化；
        m_ihVPNService = VPNService.getVPNInstance(this.getApplicationContext());

        //第二步，设置2个侦听，分别实现执行结果处理和日志处理功能
        m_ihVPNService.setOnAcceptResultListener(this);
        m_ihVPNService.setOnAcceptSysLogListener(this);
        //第三步，调用setConfigInfo设置VPN基本配置信息，系统随后会根据当前配置获取服务器的基本参数；如果为证书认证，则需要
        //设置证书类型、密码等相关信息（具体请参考demo中MainActivity的OnClick函数），如果需要验证码，请获取验证码；
        //设置VPN配置信息
        BaseConfigInfo bciInfo = new BaseConfigInfo();
        //LogLevel.LOG_LEVEL_DEBUG,LogLevel.LOG_LEVEL_NONE;
        bciInfo.m_iLogLevel = LogLevel.LOG_LEVEL_DEFAULT;//发布版本请把日志级别设置为LogLevel.LOG_LEVEL_NONE或不设置
        bciInfo.m_blAutoReConnect = true;
        bciInfo.m_iRetryCount = 10;
        bciInfo.m_iTimeOut = 5;//sencond
        bciInfo.m_iEnableModule = m_iWorkModule;//配置工作模块-端口转发(SSLVPN_PORTFORWARDING)/全网接入(SSLVPN_NETACCESS)等

        bciInfo.m_strVPNIP = "220.248.16.154";
        bciInfo.m_iServerPort = 443;
        bciInfo.m_iWorkMode = WorkModel.WORKMODE_DEFAULT;//默认参数 WorkModel.WORKMODE_DEFAULT
        m_ihVPNService.setConfigInfo(bciInfo);
        //第四步，调用loginVOne登录VPN系统；退出对应logoutVOne
        BaseAccountInfo arg0 = new BaseAccountInfo();
        String strPackageName = "";
        //if(android.os.Build.VERSION.SDK_INT >= 19) {
        strPackageName = mContext.getPackageName();
        //	}

        //请根据不同需求进行 注释or解注释，目前只能同时存在1种登录方式。
        /**************************************************************************************/
//				//1、用户名口令
        arg0.m_iLoginType = eLoginType.LOGIN_TYPE_CODEWORD.value();
        /**************************************************************************************/
                /* （3.4.170320.0.4）以前在证书认证的时候没考虑用户名，因此证书路径和用户名复用，现额外增加属性-证书路径
                 * 如果网关要求输入用户名，设置属性m_strAccount，没有请留空，证书名称或路径请设置m_strCerPath
				 */
        arg0.m_strAccount = "test004";
        //arg0.m_strCerPath="/mnt/sdcard/sslvpn/zfl.p12";//文件证书的绝对路径
        arg0.m_strLoginPasswd = "test004";
        arg0.m_strCerPasswd = "";
        arg0.m_strExtraCode = "";
        if (0 < arg0.m_strExtraCode.length()) {
            arg0.m_iExtraCodeType = eExtraCodeType.EXTRA_CODE_NONE.value();

        }

        if (null == arg0.m_strPhoneFeatureCode) {
            arg0.m_strPhoneFeatureCode = FeatureCodeHelper.getPhoneFeatureCode(getApplicationContext());
        }
        m_ihVPNService.loginVOne(arg0);
//       登出操作 退出对应logoutVOne
//        m_ihVPNService.logoutVOne();

    }

    @Override
    public void onAcceptExecResultListener(int iOperationID, int iRetValue, Object objExtralInfo, Object objReserved) {
        //Log.w(TAG, "onAcceptExecResultListener exec!");
//        boolean VPNstatus= m_iVPNStatus.IsUserLoggedin(iRetValue);
//        Log.w(TAG+"VPNstatus", VPNstatus+"");
        String strNotice = null;

        switch (eOperateType.valueOf(iOperationID)) {
            case OPERATION_GET_RESOURCE: {
                if (0 != iRetValue) {
//                    strNotice="获取资源数据失败，返回"+m_ihVPNService.getErrorInfoByCode(iRetValue);
                } else {
                    //返回了端口转发、全网接入等资源包括TopConnect资源
                    m_briArrayResInfo = (BaseResourceInfo[]) objExtralInfo;
                    //返回的是TopConnect配置信息-不使用TopConnect的请忽略
                    m_rifcpArrayConnectResInfo = (ResourceInfoForConnect[]) objReserved;

                    int iCount = 0;
                    //TODO:过滤计算指定类型的资源
                    if (null != m_briArrayResInfo) {
                        String strKey = "default_null";
                        if (BaseModule.SSLVPN_PORTFORWARDING == m_iWorkModule) {
                            strKey = "pf";//这种是端口转发类型的资源
                        } else {
                            strKey = "na";//这种是全网接入的资源
                        }
                        for (int ii = 0; ii < m_briArrayResInfo.length; ii++) {
                            if (strKey.equalsIgnoreCase(m_briArrayResInfo[ii].m_strModule))
                                iCount++;
                        }
                    }
                    /*TopConnect的资源可以通过端口转发处理，无需额外配置端口转发资源；
                     * 如果隧道是全网接入的，那么还需要配置全网接入的资源（其他资源已经配置过可以忽略）。
					 */
                    if (BaseModule.SSLVPN_PORTFORWARDING == m_iWorkModule && null != m_rifcpArrayConnectResInfo) {
                        iCount += m_rifcpArrayConnectResInfo.length;
                    }
                    if (0 < iCount) {
                        strNotice = "获取资源数据成功，总共" + iCount + "个";

                        //        第六步,，调用startService，启动VPN服务；关闭对应closeService
                        if (BaseModule.SSLVPN_NETACCESS == m_iWorkModule) {
                            //调用该函数启动全网接入服务
                            //第一个参数用于接收用户返回选择结果的Activity，用户需要在该类中实现onActivityResult函数
                            //第二个参数用于管理的Activity,Android5以后无法使用，可以不配置
                            //m_ihVPNService.startService(this,DemoBrowser.class);
                            m_ihVPNService.startService(this, null);
                        } else {
                            m_ihVPNService.startService();
                        }
                    } else {
                        strNotice = "暂无可用资源数据";
                    }
                }
            }
            break;
            case OPERATION_AUTH_LOGININFO: {
                if (iRetValue == 0) {
                    strNotice = "验证用户账户信息成功! ";
                    if (null != objReserved && 0 < ((String) objReserved).length()) {
                        Toast.makeText(getBaseContext(), "Token:" + (String) objReserved, Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (-40039 == iRetValue || -40077 == iRetValue) {
                        return;
                    }

                    strNotice = "非法的验证信息，返回：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
                }
            }
            break;
            case OPERATION_LOGIN_SYSTEM: {
                if (iRetValue == 0) {
                    strNotice = "成功登入VPN系统! ";
                    String strCertContent = m_ihVPNService.getCertificateContentInSyncMode();
                    Log.i(TAG, "证书内容：" + strCertContent);
                    //第五步，调用requestVPNResInfo，获取资源数据；
                    m_ihVPNService.requestVPNResInfo();
                } else {
                    //不支持多点登录，可以踢掉其他用户
                    if (-40077 == iRetValue) {
//                        showKickUserDialog();
                        return;
                    }

                    //需要短信认证码
                    if (-40039 == iRetValue) {
//                        showSMSDialog();
                        return;
                    }

                    if (null != m_sacAuthCfgInfo) {
                        if (ServiceAuthCfg.eCaptchaType.GID_TYPE_OFF != m_sacAuthCfgInfo.m_ectCaptchaType) {
                            m_ihVPNService.requestCaptcha();
                        }
                    }

                    strNotice = "登入VPN系统失败，返回：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
                }
            }
            break;
            case OPERATION_START_SERVICE: {
                if (iRetValue == 0) {
                    strNotice = "启动VPN服务成功! ";
                    if (BaseModule.SSLVPN_PORTFORWARDING == m_iWorkModule) {
                        String strExData = m_ihVPNService.getExchangeDataFromMode(WorkModel.WORKMODE_MASTER);
                        Log.i(TAG, "data:" + strExData);
                    }
                    TrafficStatistic tsTmp = m_ihVPNService.getTrafficStatisticInstance();
                    iRetValue = tsTmp.syncTrafficStatisticData();
                    Log.i(TAG, "syncTrafficStatisticData ret:" + iRetValue);
                    iRetValue = tsTmp.getSendPacketCount();
                    Log.i(TAG, "getSendPacketCount ret:" + iRetValue);
                    Log.i(TAG, "getRecvBytes ret:" + tsTmp.getRecvBytes());
                } else {
                    strNotice = "启动VPN服务失败，返回：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
                }
            }
            break;
            case OPERATION_CLOSE_SERVICE: {
                if (iRetValue == 0) {
                    strNotice = "VPN服务已成功关闭! ";
                    m_ihVPNService.logoutVOne();
                } else {
                    strNotice = "关闭VPN失败，原因：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
                }
            }
            break;
            case OPERATION_LOGOUT_SYSTEM: {
                if (iRetValue == 0) {
                    strNotice = "成功登出VPN系统! ";
                    if (null != m_sacAuthCfgInfo) {
                        if (ServiceAuthCfg.eCaptchaType.GID_TYPE_OFF != m_sacAuthCfgInfo.m_ectCaptchaType) {
                            m_ihVPNService.requestCaptcha();
                        }
                    } else {
                        m_ihVPNService.requestCaptcha();
                    }
                } else {
                    strNotice = "登出VPN系统失败，" + m_ihVPNService.getErrorInfoByCode(iRetValue);
                }
            }
            break;
            case OPERATION_GET_KEEPSTATUS: {
                strNotice = "当前VPN状态：";
                strNotice += VPNStaus.IsUserLoggedin(iRetValue) ? "用户已成功登入VPN系统" : "用户尚未登入VPN系统";
                strNotice += "，";
                strNotice += VPNStaus.IsVPNServiceRunning(iRetValue) ? "VPN服务正在运行" : "VPN服务尚未启动";
                strNotice += ("(当前系统返回：" + iRetValue + ")");
            }
            break;
            case OPERATION_CHECK_NETSTATUS: {
                strNotice = "网络状态检测：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
            }
            break;
            case OPERATION_TRYFIX_VPNTUNNEL: {
                //TODO:如果走到这里，说明隧道已经出现问题，系统自己尝试重试已经很困难，你可以选择重启服务或继续等待系统自动重试（只要服务器网络环境正常，重试成功几率不高）
                //重启服务：先关闭，在开启；
                strNotice = "修复隧道失败：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
            }
            break;
            case OPERATION_NETCONFIG_NETACCESS:
                return;

            default: {
                if (iRetValue >= 0) {
                    strNotice = "执行操作" + iOperationID + "成功完成! ";
                } else {
                    strNotice = "执行操作" + iOperationID + "失败，返回：" + m_ihVPNService.getErrorInfoByCode(iRetValue);
                }
            }
            break;
        }
        if (0 > iRetValue && null != objReserved && !"".equals(objReserved)) {
            strNotice += "&";
            try {
                strNotice += m_ihVPNService.getErrorInfoByCode(Integer.parseInt((String) objReserved));
            } catch (NumberFormatException ex) {
                strNotice += (String) objReserved;
            }

        }
        Toast.makeText(getApplicationContext(), strNotice, Toast.LENGTH_SHORT).show();
        writeLog(Log.DEBUG, TAG, "Ret:" + iRetValue + "&data:" + objExtralInfo + "");
    }

    @Override
    public void onAcceptSysLogInfo(int i, String s, String s1) {
        Log.i(s, s1);
    }

    public static void writeLog(int iLogLevel, String strTagName, String strContent) {
        try {
            Loger.WriteLog(iLogLevel, strTagName, strContent);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO:可以在resultCode返回成功调用(仅限全网接入)
        m_ihVPNService.toGrantStartVpnService(resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
