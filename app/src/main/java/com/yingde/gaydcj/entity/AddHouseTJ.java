package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/10.
 */

public class AddHouseTJ {
    private String   FIELDNAME;//	字段英文名称
    private String FIELDDATATYPE	;//字段数据类型string/date/number
    private String FIELDDEFAULT;//	是否市局字段
    private String FIELDVALUE	;//字段对应的值

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

    public String getFIELDNAME() {
        return FIELDNAME;
    }

    public void setFIELDNAME(String FIELDNAME) {
        this.FIELDNAME = FIELDNAME;
    }

    public String getFIELDVALUE() {
        return FIELDVALUE;
    }

    public void setFIELDVALUE(String FIELDVALUE) {
        this.FIELDVALUE = FIELDVALUE;
    }
}
