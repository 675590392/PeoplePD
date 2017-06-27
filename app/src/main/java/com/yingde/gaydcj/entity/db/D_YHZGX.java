package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_YHZGX {

    /**
     * DM : 01
     * MC : 本人
     * PY : br
     */
    @Id
    private String DM;
    private String MC;
    private String PY;

    @Generated(hash = 1400904121)
    public D_YHZGX(String DM, String MC, String PY) {
        this.DM = DM;
        this.MC = MC;
        this.PY = PY;
    }

    @Generated(hash = 1477841094)
    public D_YHZGX() {
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
