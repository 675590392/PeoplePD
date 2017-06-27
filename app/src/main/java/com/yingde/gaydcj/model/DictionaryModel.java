package com.yingde.gaydcj.model;

import com.yingde.gaydcj.http.RequestListener;

/**
 * Created by tanghao on 2017/4/11.
 */

public interface DictionaryModel {

    //获取字典表列表清单
    void getDictionaryList(RequestListener requestListener);

    //获取字典表数据
    void getDictionaryData(String TABLENAME, String VERSION, RequestListener requestListener);
}
