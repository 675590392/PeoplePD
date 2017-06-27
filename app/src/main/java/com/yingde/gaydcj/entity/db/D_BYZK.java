package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_BYZK {

    /**
     * DM : 0
     * MC : 未服兵役
     * PY : WFBY
     */
    @Id
    private String DM;
    private String MC;
    private String PY;

    @Generated(hash = 2036646214)
    public D_BYZK(String DM, String MC, String PY) {
        this.DM = DM;
        this.MC = MC;
        this.PY = PY;
    }

    @Generated(hash = 677786621)
    public D_BYZK() {
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
