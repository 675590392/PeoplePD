package com.yingde.gaydcj.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.yingde.gaydcj.activity.UnlockGestureActivity;
import com.yingde.gaydcj.entity.db.DaoMaster;
import com.yingde.gaydcj.entity.db.DaoSession;
import com.yingde.gaydcj.util.Constant;
import com.yingde.gaydcj.util.LockPatternUtils;
import com.yingde.gaydcj.util.Util;

public class PeopleApplication extends Application {
    public static final String IMAG_NAME = "image";
    //拍照跳转
    public static final int IMAG_INTENT_NAME = 1001;
    public static final int IMAG_INTENT_BACK_NAME = 1002;
    // gisOrPzType:1001//拍照查询;
    // gisOrPzType:1002//定位查询;
    public static final String PZ_TYPE_1003 = "1003";
    public static final String GIS_TYPE_1004 = "1004";
    //工作量统计跳转
    public static final String SIMPLE_WORK_STATIS_1005= "1005";//个人工作量统计
    public static final String VILLAGE_WORK_STATIS_1006= "1006";//居村委工作量统计
    public static final String SIMPLE_AVERAGE_WORK_STATIS_1007= "1007";//居村委平均工作量统计

    //单选跳转
    public static final int SELECT_SIMPLE = 1008;
    public static final int SELECT_SIMPLE_BACK= 1009;

    //多选跳转
    public static final int SELECT_MORE = 1010;
    public static final int SELECT_MORE_BACK= 1011;
    public static final String SELECT_MORE_BS_HOUSE= "bs1001";
    public static final String SELECT_MORE_BS_PEOPLE= "bs1002";

    //人员采集跳转
    public static final int ADD_PEOPLE = 1012;
    public static final int ADD_PEOPLE_BACK= 1013;
    //工作量统计传值标识
    public static final String WORKLOADSTATISTICS= "workloadStatistics";
    private static DaoSession mDaoSession;
    public static final String TAG = "App";

    private static PeopleApplication mInstance;
    private SharedPreferences mSP;
    private LockPatternUtils mLockPatternUtils;
    private int countActivedActivity = -1;
    private boolean mBackgroundEver = false;

    public static PeopleApplication getInstance() {
        return mInstance;
    }

    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }



    /**
     * 配置数据库
     */
    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        //创建数据库kss.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "people.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }


    public static DaoSession getDaoInstant() {
        return mDaoSession;
    }

    /**
     * 获取是否开启手势，默认true开启，如果异常，则不开启手势
     *
     * @return
     */
    private boolean isAlpSwitchOn() {
        boolean result = false;
        try {
            result = mSP.getBoolean(Constant.ALP_SWITCH_ON, true);
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //配置数据库
        setupDatabase();
        countActivedActivity = 0;
        mInstance = this;
        mSP = getSharedPreferences(Constant.CONFIG_NAME, MODE_PRIVATE);
        mLockPatternUtils = new LockPatternUtils(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (countActivedActivity == 0 && mBackgroundEver == true) {
                    Log.v(TAG, "切到前台  lifecycle");
                    timeOutCheck(activity);
                }
                countActivedActivity++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                countActivedActivity--;
                if (countActivedActivity == 0) {
                    Log.v(TAG, "切到后台  lifecycle");
                    mBackgroundEver = true;

                    if (isAlpSwitchOn() == true) {
                        saveStartTime();
                    }
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public void timeOutCheck(Activity activity) {
        long endTime = System.currentTimeMillis();
        if (endTime - getStartTime() >= Constant.TIMEOUT_ALP * 1000) {
            Util.toast(this, "超时了,请重新验证");
            String alp = mSP.getString(Constant.ALP, null);
            if (TextUtils.isEmpty(alp) == false) {
                Intent intent = new Intent(this, UnlockGestureActivity.class);
                intent.putExtra("pattern", alp);
                intent.putExtra("login", false); //手势验证，不进行登录验证
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // 打开新的Activity
                activity.startActivityForResult(intent, Constant.REQ_COMPARE_PATTERN_TIMEOUT_CHECK);
            }
        }
    }

    public void saveStartTime() {
        mSP.edit().putLong(Constant.START_TIME, System.currentTimeMillis()).commit();
    }

    public long getStartTime() {
        long startTime = 0;
        try {
            startTime = mSP.getLong(Constant.START_TIME, 0);
        } catch (Exception e) {
            startTime = 0;
        }
        return startTime;
    }
}
