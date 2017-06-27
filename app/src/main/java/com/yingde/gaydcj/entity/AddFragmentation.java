package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/11.
 */

public class AddFragmentation {

    public String ZJHM;//	证件号码
    public String NAME;//被登记人姓名
    public String LOCATION;//	地址
    public String TITLE;//	碎片化主题
    public String MEMO;//碎片化内容
    public String FRAGTYPE;//	碎片化分类person/house/action

    public String getFRAGTYPE() {
        return FRAGTYPE;
    }

    public void setFRAGTYPE(String FRAGTYPE) {
        this.FRAGTYPE = FRAGTYPE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getMEMO() {
        return MEMO;
    }

    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }
}
