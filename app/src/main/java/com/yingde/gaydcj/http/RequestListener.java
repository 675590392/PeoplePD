package com.yingde.gaydcj.http;

/**
 * Created by tanghao on 2017/2/21.
 */
public abstract class RequestListener<T> {


    public abstract void onSuccess(T t, String token);

    public void onFail() {
    }

}