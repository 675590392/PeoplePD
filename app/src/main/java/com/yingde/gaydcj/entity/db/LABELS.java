package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/5.
 */
@Entity
public class LABELS {
    /**
     * DM : P001
     * NAME : 孤寡老人
     * JZDM : 15137
     * PCS6 : 150005
     * TYPE : Persons
     * USERS_ID : 0E31EE2390B34AFD86C8203A2367B22B
     * SAVEDATE : 2017-04-16T11:48:14
     */
    @Id
    private String DM;
    private String NAME;
    private String JZDM;
    private String PCS6;
    private String TYPE;
    private String USERS_ID;
    private String SAVEDATE;


    @Generated(hash = 675716054)
    public LABELS(String DM, String NAME, String JZDM, String PCS6, String TYPE,
            String USERS_ID, String SAVEDATE) {
        this.DM = DM;
        this.NAME = NAME;
        this.JZDM = JZDM;
        this.PCS6 = PCS6;
        this.TYPE = TYPE;
        this.USERS_ID = USERS_ID;
        this.SAVEDATE = SAVEDATE;
    }

    @Generated(hash = 930323164)
    public LABELS() {
    }


    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getJZDM() {
        return JZDM;
    }

    public void setJZDM(String JZDM) {
        this.JZDM = JZDM;
    }

    public String getPCS6() {
        return PCS6;
    }

    public void setPCS6(String PCS6) {
        this.PCS6 = PCS6;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getUSERS_ID() {
        return USERS_ID;
    }

    public void setUSERS_ID(String USERS_ID) {
        this.USERS_ID = USERS_ID;
    }

    public String getSAVEDATE() {
        return SAVEDATE;
    }

    public void setSAVEDATE(String SAVEDATE) {
        this.SAVEDATE = SAVEDATE;
    }
}
