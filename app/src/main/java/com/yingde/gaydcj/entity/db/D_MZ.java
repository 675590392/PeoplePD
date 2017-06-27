package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_MZ {


    /**
     * DM : 01
     * PY : H
     * MC : 汉
     */
    @Id
    private String DM;
    private String PY;
    private String MC;

    @Generated(hash = 1448911872)
    public D_MZ(String DM, String PY, String MC) {
        this.DM = DM;
        this.PY = PY;
        this.MC = MC;
    }

    @Generated(hash = 1349009731)
    public D_MZ() {
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getPY() {
        return PY;
    }

    public void setPY(String PY) {
        this.PY = PY;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }
}
