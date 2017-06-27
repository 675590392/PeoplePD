package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/10.
 */

public class PeopleAddShow {
    public String FIELDNAME;//	字段英文名称
    public String FIELDDATATYPE;//字段数据类型string/date/number
    public String FIELDDEFAULT;//是否市局字段
    public String FIELDDEFAULTVALUE;//	字段默认值
    public String FIELDMUST;//字段是否必填0:非必填 1:必填
    public String FIELDCNNAME;//字段中文名称
    public String FIELDNOTE;//字段解释
    public String FIELDTYPE;//字段输入方式01 输入框02 选择框03 是否04 日期选择
    public String FIELDSOURCE;//字段使用字典表表名
    public String FIELDVALUE;//字段对应的值
    public String AUTOFETCH;//是否从前面获取 1 自动获取 0 手工录入
    public String  ISSHOW;//是否展示  1 展示 0 不展示


    public String getISSHOW() {
        return ISSHOW;
    }

    public void setISSHOW(String ISSHOW) {
        this.ISSHOW = ISSHOW;
    }

    public String getAUTOFETCH() {
        return AUTOFETCH;
    }

    public void setAUTOFETCH(String AUTOFETCH) {
        this.AUTOFETCH = AUTOFETCH;
    }

    public String getFIELDVALUE() {
        return FIELDVALUE;
    }

    public void setFIELDVALUE(String FIELDVALUE) {
        this.FIELDVALUE = FIELDVALUE;
    }

    public String getFIELDTYPE() {
        return FIELDTYPE;
    }

    public void setFIELDTYPE(String FIELDTYPE) {
        this.FIELDTYPE = FIELDTYPE;
    }

    public String getFIELDCNNAME() {
        return FIELDCNNAME;
    }

    public void setFIELDCNNAME(String FIELDCNNAME) {
        this.FIELDCNNAME = FIELDCNNAME;
    }

    public String getFIELDDATATYPE() {
        return FIELDDATATYPE;
    }

    public void setFIELDDATATYPE(String FIELDDATATYPE) {
        this.FIELDDATATYPE = FIELDDATATYPE;
    }

    public String getFIELDDEFAULT() {
        return FIELDDEFAULT;
    }

    public void setFIELDDEFAULT(String FIELDDEFAULT) {
        this.FIELDDEFAULT = FIELDDEFAULT;
    }

    public String getFIELDDEFAULTVALUE() {
        return FIELDDEFAULTVALUE;
    }

    public void setFIELDDEFAULTVALUE(String FIELDDEFAULTVALUE) {
        this.FIELDDEFAULTVALUE = FIELDDEFAULTVALUE;
    }

    public String getFIELDMUST() {
        return FIELDMUST;
    }

    public void setFIELDMUST(String FIELDMUST) {
        this.FIELDMUST = FIELDMUST;
    }

    public String getFIELDNAME() {
        return FIELDNAME;
    }

    public void setFIELDNAME(String FIELDNAME) {
        this.FIELDNAME = FIELDNAME;
    }

    public String getFIELDNOTE() {
        return FIELDNOTE;
    }

    public void setFIELDNOTE(String FIELDNOTE) {
        this.FIELDNOTE = FIELDNOTE;
    }

    public String getFIELDSOURCE() {
        return FIELDSOURCE;
    }

    public void setFIELDSOURCE(String FIELDSOURCE) {
        this.FIELDSOURCE = FIELDSOURCE;
    }
}
