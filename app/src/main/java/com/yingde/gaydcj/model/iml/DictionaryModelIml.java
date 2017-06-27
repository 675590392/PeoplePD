package com.yingde.gaydcj.model.iml;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.yingde.gaydcj.entity.Entity;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.http.RetrofitClient;
import com.yingde.gaydcj.model.DictionaryModel;

import java.util.HashMap;
import java.util.Map;

import rx.schedulers.Schedulers;

/**
 * Created by tanghao on 2017/4/11.
 */

public class DictionaryModelIml extends BaseModelIml implements DictionaryModel {
    public DictionaryModelIml(Context mContext) {
        super(mContext);
    }

    @Override
    public void getDictionaryList(RequestListener requestListener) {
        sendRequest("fetchDictionaries.aspx", "", requestListener);
    }

    @Override
    public void getDictionaryData(String TABLENAME, String VERSION, RequestListener requestListener) {

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("TABLENAME", TABLENAME);
        hashMap.put("VERSION", VERSION);

        String json = JSON.toJSONString(hashMap);
//        sendRequest("fetchDictionaryData.aspx", json, requestListener);


        if (checkNetwork()) {
            isShowDialog = false;
            RetrofitClient.getCommonApi().request(tokenId, "fetchDictionaryData.aspx", json)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseModelIml.SubscriberIml<Entity>(requestListener));
        }
    }
}
