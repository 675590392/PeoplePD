package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_PROVINCE {


    /**
     * DM : 11
     * MC : 北京市
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 1480052472)
    public D_PROVINCE(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 1350455572)
    public D_PROVINCE() {
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
