package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_XB {

    /**
     * DM : 1
     * MC : 男性
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 1117011248)
    public D_XB(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 921187406)
    public D_XB() {
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
