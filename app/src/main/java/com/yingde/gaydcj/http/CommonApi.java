package com.yingde.gaydcj.http;


import com.yingde.gaydcj.entity.Entity;
import com.yingde.gaydcj.entity.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by tanghao on 2017/1/26.
 * 请求的API接口
 */
public interface CommonApi {

    @FormUrlEncoded
    @POST("appInterface.asmx/login")
    Observable<Entity<List<User>>> login(@Field("deviceid") String deviceid,@Field("loginid") String loginid, @Field("loginpwd") String loginpwd);

    @FormUrlEncoded
    @POST("appInterface.asmx/DoInterface")
    Observable<Entity> request(@Field("tokenID") String tokenID, @Field("urlPath") String urlPath, @Field("jsonParas") String jsonParas);

}
