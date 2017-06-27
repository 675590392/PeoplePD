package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tanghao on 2017/4/11.
 * 街镇
 */
@Entity
public class D_JZ {
    /**
     * JZDM : 15141
     * JZMC : 宣桥镇
     * USEFUL_START : 2013-06-14T00:00:00
     * USEFUL_END : 9999-12-31T00:00:00
     * FLAG_USEFUL : 00
     * QYDM : 15
     */

    @Id
    private String JZDM;
    private String JZMC;
    private String USEFUL_START;
    private String USEFUL_END;
    private String FLAG_USEFUL;
    private String QYDM;

    @Generated(hash = 495090002)
    public D_JZ(String JZDM, String JZMC, String USEFUL_START, String USEFUL_END,
            String FLAG_USEFUL, String QYDM) {
        this.JZDM = JZDM;
        this.JZMC = JZMC;
        this.USEFUL_START = USEFUL_START;
        this.USEFUL_END = USEFUL_END;
        this.FLAG_USEFUL = FLAG_USEFUL;
        this.QYDM = QYDM;
    }

    @Generated(hash = 471570177)
    public D_JZ() {
    }

    public String getJZDM() {
        return JZDM;
    }

    public void setJZDM(String JZDM) {
        this.JZDM = JZDM;
    }

    public String getJZMC() {
        return JZMC;
    }

    public void setJZMC(String JZMC) {
        this.JZMC = JZMC;
    }

    public String getUSEFUL_START() {
        return USEFUL_START;
    }

    public void setUSEFUL_START(String USEFUL_START) {
        this.USEFUL_START = USEFUL_START;
    }

    public String getUSEFUL_END() {
        return USEFUL_END;
    }

    public void setUSEFUL_END(String USEFUL_END) {
        this.USEFUL_END = USEFUL_END;
    }

    public String getFLAG_USEFUL() {
        return FLAG_USEFUL;
    }

    public void setFLAG_USEFUL(String FLAG_USEFUL) {
        this.FLAG_USEFUL = FLAG_USEFUL;
    }

    public String getQYDM() {
        return QYDM;
    }

    public void setQYDM(String QYDM) {
        this.QYDM = QYDM;
    }
}
