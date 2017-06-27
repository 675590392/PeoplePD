package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_RKLB {


    /**
     * DM : 00
     * MC : 户籍人员
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 862468068)
    public D_RKLB(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 1862870433)
    public D_RKLB() {
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
