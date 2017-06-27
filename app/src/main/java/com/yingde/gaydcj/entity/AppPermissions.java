package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/5/8.
 * 获取APP操作模块（权限）
 */

public class AppPermissions {
    //模块代码
  private  String CODE;
   // 模块名称
    private  String NAME;
    //模块图标
    private  String IMAGE;
    //模块排序号码
    private  String SORTID;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getSORTID() {
        return SORTID;
    }

    public void setSORTID(String SORTID) {
        this.SORTID = SORTID;
    }
}
