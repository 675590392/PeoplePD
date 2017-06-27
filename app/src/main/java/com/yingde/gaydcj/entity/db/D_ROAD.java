package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tanghao on 2017/4/11.
 * 路
 */

@Entity
public class D_ROAD {
    /**
     * DM : 301749
     * MC : 长征农场建筑公司生活区
     * PY : jzgs
     */

    @Id
    private String DM;
    private String MC;
    private String PY;

    @Generated(hash = 1917677732)
    public D_ROAD(String DM, String MC, String PY) {
        this.DM = DM;
        this.MC = MC;
        this.PY = PY;
    }

    @Generated(hash = 464721616)
    public D_ROAD() {
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
}
