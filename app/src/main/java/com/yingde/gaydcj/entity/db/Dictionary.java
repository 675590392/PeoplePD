package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by tanghao on 2017/4/11.
 */

@Entity
public class Dictionary {
    /**
     * TABLENAME : D_JCW
     * TABLECHNAME : JCW TABLE
     * VERSION : V1.0.1
     */

    @Id
    private String TABLENAME;
    private String TABLECHNAME;
    private String VERSION;

    @Generated(hash = 450384150)
    public Dictionary(String TABLENAME, String TABLECHNAME, String VERSION) {
        this.TABLENAME = TABLENAME;
        this.TABLECHNAME = TABLECHNAME;
        this.VERSION = VERSION;
    }

    @Generated(hash = 487998537)
    public Dictionary() {
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getTABLECHNAME() {
        return TABLECHNAME;
    }

    public void setTABLECHNAME(String TABLECHNAME) {
        this.TABLECHNAME = TABLECHNAME;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }
}
