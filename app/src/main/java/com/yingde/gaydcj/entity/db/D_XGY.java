package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_XGY {

    /**
     * DM : 10540035
     * MC : 席宏霞
     */
    @Id
    private String DM;
    private String MC;

    @Generated(hash = 27026754)
    public D_XGY(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    @Generated(hash = 550642659)
    public D_XGY() {
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
