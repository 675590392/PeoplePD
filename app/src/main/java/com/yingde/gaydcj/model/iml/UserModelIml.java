package com.yingde.gaydcj.model.iml;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.entity.Entity;
import com.yingde.gaydcj.entity.User;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.http.RetrofitClient;
import com.yingde.gaydcj.model.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tanghao on 2017/4/6.
 */

public class UserModelIml extends BaseModelIml implements UserModel {
    public UserModelIml(Context mContext) {
        super(mContext);
    }

    @Override
    public void login(String deviceId, String username, String pwd, RequestListener requestListener) {
        if (checkNetwork())
            RetrofitClient.getCommonApi().login(deviceId, username, pwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SubscriberIml<Entity<List<User>>>(requestListener));
    }

    @Override
    public void getRegCode(String chidcard, String chname, String houseseq, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("chidcard", chidcard);
        hashMap.put("chname", chname);
        hashMap.put("houseseq", houseseq);

        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchRegCode.aspx", json, requestListener);
    }

    //上传照片
    @Override
    public void uploadPicture(String regcode, String photoseq, String imagephoto, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("regcode", regcode);
        hashMap.put("photoseq", photoseq);
        hashMap.put("imagephoto", imagephoto);

        String json = JSON.toJSONString(hashMap);
        sendRequest("uploadPhoto.aspx", json, requestListener);
    }

    //下载照片
    @Override
    public void downloadPicture(String regcode, String photoseq,RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("regcode", regcode);
        hashMap.put("photoseq", photoseq);

        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchPhotos.aspx", json, requestListener);
    }
    //app升级
    @Override
    public void downloadApp(String AppVersion, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("AppVersion", AppVersion);
        String json = JSON.toJSONString(hashMap);
        sendRequest("downloadApp.aspx", json, requestListener);
    }

    //二十六、个人工作量统计—--根据时间段返回每一天的采集数
    @Override
    public void simpleWorkRecordsByDate(String StartDate, String EndDate, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("StartDate", StartDate);
        hashMap.put("EndDate", EndDate);

        //{'StartDate':'20170406','EndDate':'20170410'}
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchWorkload.aspx", json, requestListener);
    }


    //{"COUNT": 1.0,"YYYYMMDD": "20170407"}
    //二十七、居村委工作量统计—--根据时间段返回每一天的采集数
    @Override
    public void workRecordsByDate(String StartDate, String EndDate, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("StartDate", StartDate);
        hashMap.put("EndDate", EndDate);

        //{'StartDate':'20170406','EndDate':'20170410'}
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchWorkload4JCW.aspx", json, requestListener);
    }
    //二十八、居村委平均工作量统计—--根据时间段返回每一天的平均采集数
    @Override
    public void workRecordsPerByDate(String StartDate, String EndDate, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("StartDate", StartDate);
        hashMap.put("EndDate", EndDate);

        //{'StartDate':'20170406','EndDate':'20170410'}
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchAverageWorkload4JCW.aspx", json, requestListener);
    }
    //二十九、获取APP操作模块（权限）
    @Override
    public void getAppPermissions(RequestListener requestListener) {

        sendRequest("fetchRights.aspx", "", requestListener);
    }
}
