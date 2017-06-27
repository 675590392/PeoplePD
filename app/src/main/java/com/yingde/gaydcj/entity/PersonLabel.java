package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/11.
 */

public class PersonLabel {
    public String DM;//标签代码
    public String NAME;//标签名称
    public String JZDM;//所属街镇代码
    public String PCS6;//所属派出所
    public String TYPE;//Person

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getJZDM() {
        return JZDM;
    }

    public void setJZDM(String JZDM) {
        this.JZDM = JZDM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPCS6() {
        return PCS6;
    }

    public void setPCS6(String PCS6) {
        this.PCS6 = PCS6;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
