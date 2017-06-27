package com.yingde.gaydcj.model;

import com.yingde.gaydcj.entity.AddFragmentation;
import com.yingde.gaydcj.entity.PeopleAddShow;
import com.yingde.gaydcj.http.RequestListener;

import java.util.List;

/**
 * Created by tanghao on 2017/4/6.
 */

public interface HouseModel {

    //通过GIS获取门弄牌列表
    void getDoorByGIS(String Gis_x, String Gis_y, String Distance,String index, RequestListener requestListener);

    //根据GIS查找街路巷信息
    void getStreetByGIS(String Gis_x, String Gis_y, String Distance, RequestListener requestListener);

    //根据街路巷代码和门牌号（拍照识别）查找房屋列表
    void getHouseListByJLX(String JLXDM, String MLPH, RequestListener requestListener);

    //根据门弄牌编码查找房屋列表
    void getHouseListByMLP(String MLPBM, RequestListener requestListener);

    //根据房屋编码查找对应房屋中人员信息
    void getPeopleInfoListByFWBM(String MLPBM, RequestListener requestListener);

    //九、获取人员采集项目字段列表
    void getPeopleAddList(RequestListener requestListener);

    //十、获取房屋采集项目字段列表
    void getHouseAddList(RequestListener requestListener);

    //十一、新增人员接收接口
    void getPeopleAdd(List<PeopleAddShow> addPerson, RequestListener requestListener);

    //十二、注销人员接收接口
    void getCancelPerson(String regcode,String chidcard,String chname,String fwbm,RequestListener requestListener);

    //十四、新增房屋接收接口
    void getAddHouse(List<PeopleAddShow> addHouseTJ, RequestListener requestListener);

    //十六、获取人员标识
    void getPersonLabel(RequestListener requestListener);

    //十七、获取房屋标识
    void getHouseLabel(RequestListener requestListener);

    //十八、碎片化信息新增接口
    void getAddFragmentation(AddFragmentation addFragmentation, RequestListener requestListener);

    //二十四、获取通知通告清单
    void getMessages(String Type,String Rows, RequestListener requestListener);

    //二十五、获取通知通告详细信息
    void getMessageData(String CODE,RequestListener requestListener);

}
