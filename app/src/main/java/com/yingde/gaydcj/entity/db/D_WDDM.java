package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_WDDM {

    /**
     * DM : 15001000
     * MC : 浦东康桥镇（公元三村）点
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 1286000700)
    public D_WDDM(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 1173002352)
    public D_WDDM() {
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
