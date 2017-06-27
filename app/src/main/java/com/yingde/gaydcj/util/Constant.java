package com.yingde.gaydcj.util;

/**
 * Created by 刘红亮 on 2015/9/24 14:08.
 */
public interface Constant {
    /**
     * 二维码请求的type
     */
    public static final String REQUEST_SCAN_TYPE="type";
    /**
     * 普通类型，扫完即关闭
     */
    public static final int REQUEST_SCAN_TYPE_COMMON=0;
    /**
     * 服务商登记类型，扫描
     */
    public static final int REQUEST_SCAN_TYPE_REGIST=1;


    /**
     * 扫描类型
     * 条形码或者二维码：REQUEST_SCAN_MODE_ALL_MODE
     * 条形码： REQUEST_SCAN_MODE_BARCODE_MODE
     * 二维码：REQUEST_SCAN_MODE_QRCODE_MODE
     *
     */
    public static final String REQUEST_SCAN_MODE="ScanMode";
    /**
     * 条形码： REQUEST_SCAN_MODE_BARCODE_MODE
     */
    public static final int REQUEST_SCAN_MODE_BARCODE_MODE = 0X100;
    /**
     * 二维码：REQUEST_SCAN_MODE_ALL_MODE
     */
    public static final int REQUEST_SCAN_MODE_QRCODE_MODE = 0X200;
    /**
     * 条形码或者二维码：REQUEST_SCAN_MODE_ALL_MODE
     */
    public static final int REQUEST_SCAN_MODE_ALL_MODE = 0X300;

    //配置文件名
    public static final String CONFIG_NAME = "lockdemo";
    //是否开启手势密码
    public static final String ALP_SWITCH_ON = "alpswitchon";
    //手势密码
    public static final String ALP = "Alp";

    public static final String LOGIN_STATE = "loginstate";


    //超时时间(单位：s)，当界面隐藏后超过该时间则再次打开需要手势密码
    public static final int TIMEOUT_ALP = 3;

    //后台保存的起始时间
    public static final String START_TIME = "StartTime";


    public static final int ON_LOGIN_OK = 0;
    public static final int ON_LOGIN_FAILED = 1;

    //请求创建一个新的图案
    public static final int REQ_CREATE_PATTERN = 1;

    //比较已有的图案
    public static final int REQ_COMPARE_PATTERN = 2;
    //超时手势验证请求
    public static final int REQ_COMPARE_PATTERN_TIMEOUT_CHECK = 3;

    public final static int RESULT_FAILED = 10;
    public final static int RESULT_FORGOT_PATTERN = 11;
    public final static int RESULT_ERRORS = 12;
    public final static int RESULT_CHANGE_USER = 13;
    //修改图案
    public static final int CHANGEPSWD_RET_CODE = 14;
    public static final int REQ_COMPARE_PATTERN_CHECK = 15;
    public static final int REQ_COMPARE_PATTERN_CHANGE = 16;
}
