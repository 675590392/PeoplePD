package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tanghao on 2017/4/11.
 * 派出所
 */

@Entity
public class D_PCS {
    /**
     * PCS6 : 150011
     * PCS12 : 310115380000
     * PCSMC : 惠南派出所
     * USEFUL_START : 2013-05-15T00:00:00
     * USEFUL_END : 9999-12-31T00:00:00
     * FLAG_USEFUL : 00
     * JZDM : 15131
     */

    @Id
    private String PCS6;
    private String PCS12;
    private String PCSMC;
    private String USEFUL_START;
    private String USEFUL_END;
    private String FLAG_USEFUL;
    private String JZDM;

    @Generated(hash = 287787675)
    public D_PCS(String PCS6, String PCS12, String PCSMC, String USEFUL_START,
            String USEFUL_END, String FLAG_USEFUL, String JZDM) {
        this.PCS6 = PCS6;
        this.PCS12 = PCS12;
        this.PCSMC = PCSMC;
        this.USEFUL_START = USEFUL_START;
        this.USEFUL_END = USEFUL_END;
        this.FLAG_USEFUL = FLAG_USEFUL;
        this.JZDM = JZDM;
    }

    @Generated(hash = 1743289886)
    public D_PCS() {
    }

    public String getPCS6() {
        return PCS6;
    }

    public void setPCS6(String PCS6) {
        this.PCS6 = PCS6;
    }

    public String getPCS12() {
        return PCS12;
    }

    public void setPCS12(String PCS12) {
        this.PCS12 = PCS12;
    }

    public String getPCSMC() {
        return PCSMC;
    }

    public void setPCSMC(String PCSMC) {
        this.PCSMC = PCSMC;
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

    public String getJZDM() {
        return JZDM;
    }

    public void setJZDM(String JZDM) {
        this.JZDM = JZDM;
    }
}
