package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/27.
 */
@Entity
public class D_ZD {

    /**
     * TABLEID : 01
     * TABLENAME : null
     * GXSJ : 20170401
     */
    @Id
    private String TABLEID;
    private String TABLENAME;
    private String GXSJ;

    @Generated(hash = 369961100)
    public D_ZD(String TABLEID, String TABLENAME, String GXSJ) {
        this.TABLEID = TABLEID;
        this.TABLENAME = TABLENAME;
        this.GXSJ = GXSJ;
    }

    @Generated(hash = 1697607528)
    public D_ZD() {
    }

    public String getTABLEID() {
        return TABLEID;
    }

    public void setTABLEID(String TABLEID) {
        this.TABLEID = TABLEID;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getGXSJ() {
        return GXSJ;
    }

    public void setGXSJ(String GXSJ) {
        this.GXSJ = GXSJ;
    }
}
