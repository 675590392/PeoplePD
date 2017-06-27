package com.yingde.gaydcj.entity;

import java.io.Serializable;

/**
 * Created by tanghao on 2017/4/6.
 */

public class User implements Serializable {

    /**
     * LOGIN_ID : user03
     * NAME : user03
     * ZHIWU : 协管员
     * MOBILE : 9234567890
     * CHIDCARD : 3.10107E+17
     * USERCODE : 2
     * POLICEID : 150005
     * JCWDM : 15137005
     * COUNTYID : 15
     * APPLATTICE : 19011012
     * TOKENID : 237D5D51FDD0415E8C9B8061CC5C7236
     * JZDM : 15137
     */

    private String LOGIN_ID;
    private String NAME;
    private String ZHIWU;
    private String MOBILE;
    private String CHIDCARD;
    private String USERCODE;
    private String POLICEID;
    private String JCWDM;
    private String COUNTYID;
    private String APPLATTICE;
    private String TOKENID;
    private String JZDM;

    public String getLOGIN_ID() {
        return LOGIN_ID;
    }

    public void setLOGIN_ID(String LOGIN_ID) {
        this.LOGIN_ID = LOGIN_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getZHIWU() {
        return ZHIWU;
    }

    public void setZHIWU(String ZHIWU) {
        this.ZHIWU = ZHIWU;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getCHIDCARD() {
        return CHIDCARD;
    }

    public void setCHIDCARD(String CHIDCARD) {
        this.CHIDCARD = CHIDCARD;
    }

    public String getUSERCODE() {
        return USERCODE;
    }

    public void setUSERCODE(String USERCODE) {
        this.USERCODE = USERCODE;
    }

    public String getPOLICEID() {
        return POLICEID;
    }

    public void setPOLICEID(String POLICEID) {
        this.POLICEID = POLICEID;
    }

    public String getJCWDM() {
        return JCWDM;
    }

    public void setJCWDM(String JCWDM) {
        this.JCWDM = JCWDM;
    }

    public String getCOUNTYID() {
        return COUNTYID;
    }

    public void setCOUNTYID(String COUNTYID) {
        this.COUNTYID = COUNTYID;
    }

    public String getAPPLATTICE() {
        return APPLATTICE;
    }

    public void setAPPLATTICE(String APPLATTICE) {
        this.APPLATTICE = APPLATTICE;
    }

    public String getTOKENID() {
        return TOKENID;
    }

    public void setTOKENID(String TOKENID) {
        this.TOKENID = TOKENID;
    }

    public String getJZDM() {
        return JZDM;
    }

    public void setJZDM(String JZDM) {
        this.JZDM = JZDM;
    }
}
