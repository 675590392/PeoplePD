package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_ZZMM {
    /**
     * DM : 10
     * MC : 党员
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 1024677761)
    public D_ZZMM(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 784627616)
    public D_ZZMM() {
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
