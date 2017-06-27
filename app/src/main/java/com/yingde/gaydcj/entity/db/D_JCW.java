package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tanghao on 2017/4/11.
 * 居村委
 */

@Entity
public class D_JCW {

    /**
     * JCWDM : 15010031
     * JCWMC : 六里二居委
     * USEFUL_START : 2013-06-14T00:00:00
     * USEFUL_END : 9999-12-31T00:00:00
     * FLAG_USEFUL : 00
     * PCS6 : 150052
     * JZDM : 15010
     */

    @Id
    private String JCWDM;
    private String JCWMC;
    private String USEFUL_START;
    private String USEFUL_END;
    private String FLAG_USEFUL;
    private String PCS6;
    private String JZDM;

    @Generated(hash = 870828289)
    public D_JCW(String JCWDM, String JCWMC, String USEFUL_START, String USEFUL_END,
            String FLAG_USEFUL, String PCS6, String JZDM) {
        this.JCWDM = JCWDM;
        this.JCWMC = JCWMC;
        this.USEFUL_START = USEFUL_START;
        this.USEFUL_END = USEFUL_END;
        this.FLAG_USEFUL = FLAG_USEFUL;
        this.PCS6 = PCS6;
        this.JZDM = JZDM;
    }

    @Generated(hash = 899705665)
    public D_JCW() {
    }

    public String getJCWDM() {
        return JCWDM;
    }

    public void setJCWDM(String JCWDM) {
        this.JCWDM = JCWDM;
    }

    public String getJCWMC() {
        return JCWMC;
    }

    public void setJCWMC(String JCWMC) {
        this.JCWMC = JCWMC;
    }

    public String getUSEFUL_START() {
        return USEFUL_START;
    }

    public void setUSEFUL_START(String USEFUL_START) {
        this.USEFUL_START = USEFUL_START;
    }

    public String getUSEFUL_END() {
        return USEFUL_END;
    }

    public void setUSEFUL_END(String USEFUL_END) {
        this.USEFUL_END = USEFUL_END;
    }

    public String getFLAG_USEFUL() {
        return FLAG_USEFUL;
    }

    public void setFLAG_USEFUL(String FLAG_USEFUL) {
        this.FLAG_USEFUL = FLAG_USEFUL;
    }

    public String getPCS6() {
        return PCS6;
    }

    public void setPCS6(String PCS6) {
        this.PCS6 = PCS6;
    }

    public String getJZDM() {
        return JZDM;
    }

    public void setJZDM(String JZDM) {
        this.JZDM = JZDM;
    }
}
