package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_JZFWLX {


    /**
     * DM : 01
     * MC : 出租房屋
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 1559304739)
    public D_JZFWLX(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 1239347393)
    public D_JZFWLX() {
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
