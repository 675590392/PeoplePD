package com.yingde.gaydcj.model.iml;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yingde.gaydcj.entity.AddFragmentation;
import com.yingde.gaydcj.entity.PeopleAddShow;
import com.yingde.gaydcj.http.RequestListener;
import com.yingde.gaydcj.model.HouseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanghao on 2017/4/6.
 */

public class HouseModelIml extends BaseModelIml implements HouseModel {


    public HouseModelIml(Context mContext) {
        super(mContext);
    }

    //通过GIS获取门弄牌列表
    @Override
    public void getDoorByGIS(String Gis_x, String Gis_y, String Distance,String index, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("Gis_x", Gis_x);
        hashMap.put("Gis_y", Gis_y);
        hashMap.put("Distance", Distance);//距离（单位米）50,100,150
        hashMap.put("Pagenumber", index);
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchMLPHByGIS.aspx", json, requestListener);
    }

    //根据GIS查找街路巷信息
    @Override
    public void getStreetByGIS(String Gis_x, String Gis_y, String Distance, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("Gis_x", Gis_x);
        hashMap.put("Gis_y", Gis_y);
        hashMap.put("Distance", Distance);//距离（单位米）50,100,150

        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchJLXByGIS.aspx", json, requestListener);
    }

    //根据街路巷代码和门牌号（拍照识别）查找房屋列表
    @Override
    public void getHouseListByJLX(String JLXDM, String MLPH, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("JLXDM", JLXDM);//街路巷代码
        hashMap.put("MLPH", MLPH);//门牌号

        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchFWXXByMPH.aspx", json, requestListener);
    }

    //根据门弄牌编码查找房屋列表
    @Override
    public void getHouseListByMLP(String MLPBM, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("MLPBM", MLPBM);//街路巷代码

        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchFWXXByMLPBM.aspx", json, requestListener);
    }

    //根据房屋编码查找对应房屋中人员信息
    @Override
    public void getPeopleInfoListByFWBM(String FWBM, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("FWBM", FWBM);//房屋编码
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchPersonsByFWBM.aspx", json, requestListener);
    }

    //九、获取人员采集项目字段列表
    @Override
    public void getPeopleAddList(RequestListener requestListener) {
        sendRequest("fetchPersonFields.aspx", "", requestListener);
    }

    //十、获取房屋采集项目字段列表
    @Override
    public void getHouseAddList(RequestListener requestListener) {
        sendRequest("fetchHouseFields.aspx", "", requestListener);
    }

    //十一、新增人员接收接口  ?
    @Override
    public void getPeopleAdd(List<PeopleAddShow> addPerson, RequestListener requestListener) {
//        String json = JSON.toJSONString(addPerson);
        Gson gson = new Gson();
//        String json = gson.toJson(addPerson);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson includeNullsGson = gsonBuilder.serializeNulls().create();
        String json2 = includeNullsGson.toJson(addPerson);

        sendRequest("addPerson.aspx", json2, requestListener);
    }
    //十二、注销人员接收接口
    @Override
    public void getCancelPerson(String regcode, String chidcard, String chname, String fwbm, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("regcode", regcode);//登记号
        hashMap.put("chidcard", chidcard);//公民身份证号码
        hashMap.put("chname", chname);//姓名
        hashMap.put("fwbm", fwbm);//房屋编码

        String json = JSON.toJSONString(hashMap);
        sendRequest("cancelPerson.aspx", json, requestListener);
    }

    //十四、新增房屋接收接口
    @Override
    public void getAddHouse(List<PeopleAddShow> addHouse, RequestListener requestListener) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson includeNullsGson = gsonBuilder.serializeNulls().create();
        String json2 = includeNullsGson.toJson(addHouse);
        sendRequest("addHouse.aspx", json2, requestListener);
    }


    //十六、获取人员标识
    @Override
    public void getPersonLabel(RequestListener requestListener) {
        sendRequest("fetchPersonLabel.aspx", "", requestListener);
    }

    //十七、获取房屋标识
    @Override
    public void getHouseLabel(RequestListener requestListener) {
        sendRequest("fetchHouseLabel.aspx", "", requestListener);
    }

    //十八、碎片化信息新增接口
    @Override
    public void getAddFragmentation(AddFragmentation addFragmentation, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("ZJHM", addFragmentation.ZJHM);//证件号码
        hashMap.put("NAME", addFragmentation.NAME);//被登记人姓名
        hashMap.put("LOCATION", addFragmentation.LOCATION);//地址
        hashMap.put("TITLE", addFragmentation.TITLE);//碎片化主题
        hashMap.put("MEMO", addFragmentation.MEMO);//碎片化内容
        hashMap.put("FRAGTYPE", addFragmentation.FRAGTYPE);//碎片化分类person/house/action

        String json = JSON.toJSONString(hashMap);
        sendRequest("addFragmentation.aspx", json, requestListener);
    }

    //二十四、获取通知通告清单
    @Override
    public void getMessages(String Type, String Rows, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("Type", Type);//类型：Message/Notice
        hashMap.put("Rows", Rows);//获取条数:0标示所有
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchMessages.aspx", json, requestListener);
    }

    //二十五、获取通知通告详细信息
    @Override
    public void getMessageData(String CODE, RequestListener requestListener) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("CODE", CODE);//通知ID
        String json = JSON.toJSONString(hashMap);
        sendRequest("fetchMessageData.aspx", json, requestListener);
    }

}
