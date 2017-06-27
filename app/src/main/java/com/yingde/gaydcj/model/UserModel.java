package com.yingde.gaydcj.model;

import com.yingde.gaydcj.http.RequestListener;

/**
 * Created by tanghao on 2017/4/6.
 */

public interface UserModel {
    //登录接口
    void login(String deviceId, String username, String pwd, RequestListener requestListener);

    //获取登记号
    void getRegCode(String chidcard, String chname, String houseseq, RequestListener requestListener);

    //照片上传
    void uploadPicture(String regcode, String photoseq, String imagephoto, RequestListener requestListener);

    //照片下载
    void downloadPicture(String regcode, String houseseq, RequestListener requestListener);

    //APP下载接口
    void downloadApp(String AppVersion, RequestListener requestListener);

    //二十六、个人工作量统计—--根据时间段返回每一天的采集数
    void simpleWorkRecordsByDate(String StartDate, String EndDate, RequestListener requestListener);

    //居村委工作量统计—--根据时间段返回每一天的采集数
    void workRecordsByDate(String StartDate, String EndDate, RequestListener requestListener);

    //居村委平均工作量统计—--根据时间段返回每一天的平均采集数
    void workRecordsPerByDate(String StartDate, String EndDate, RequestListener requestListener);
    //获取APP操作模块（权限）
    void getAppPermissions(RequestListener requestListener);
}
