package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_GJ {

    /**
     * DM : 004
     * MC : 阿富汗
     * PY : AFH
     * WB : AFG
     */
    @Id
    private String DM;
    private String MC;
    private String PY;
    private String WB;

    @Generated(hash = 92307591)
    public D_GJ(String DM, String MC, String PY, String WB) {
        this.DM = DM;
        this.MC = MC;
        this.PY = PY;
        this.WB = WB;
    }

    @Generated(hash = 490560015)
    public D_GJ() {
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getPY() {
        return PY;
    }

    public void setPY(String PY) {
        this.PY = PY;
    }

    public String getWB() {
        return WB;
    }

    public void setWB(String WB) {
        this.WB = WB;
    }
}
