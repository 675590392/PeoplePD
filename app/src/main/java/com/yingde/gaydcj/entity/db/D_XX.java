package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_XX {

    /**
     * DM : 0
     * MC : 不明
     * PY : bm
     */
    @Id
    private String DM;
    private String MC;
    private String PY;

    @Generated(hash = 338568024)
    public D_XX(String DM, String MC, String PY) {
        this.DM = DM;
        this.MC = MC;
        this.PY = PY;
    }

    @Generated(hash = 1889090601)
    public D_XX() {
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
