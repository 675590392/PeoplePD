package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_XZQH {

    /**
     * DM : 513300
     * PY : SCSGZZZZZZ
     * MC : 四川省甘孜藏族自治州
     */
    @Id
    private String DM;
    private String PY;
    private String MC;

    @Generated(hash = 1982151335)
    public D_XZQH(String DM, String PY, String MC) {
        this.DM = DM;
        this.PY = PY;
        this.MC = MC;
    }

    @Generated(hash = 752111538)
    public D_XZQH() {
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
