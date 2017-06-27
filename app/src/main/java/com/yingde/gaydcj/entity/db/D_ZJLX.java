package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_ZJLX {

    /**
     * DM : 00
     * MC : 无证件
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 1088742447)
    public D_ZJLX(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 974553828)
    public D_ZJLX() {
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
}
