package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tanghao on 2017/4/11.
 * 门弄牌
 */
@Entity
public class D_MLPH {
    /**
     * MLPBM : 31252795000000000000000325523X
     * JLXMC : 万祥镇万隆村
     * MLPH : 万三810号
     * GIS_X : 31470.134963
     * GIS_Y : -30636.285447
     * ZZDM : 15143
     * JCWDM : 15143217
     * PCS6 : 150017
     * JLXDM : 252795
     * MLPHXX : 上海市浦东新区万祥镇万隆村万三810号
     * YJMLP : 000000
     * EJMLP : 000000
     * SJMLP : 000325
     */

    @Id
    private String MLPBM;
    private String JLXMC;
    private String MLPH;
    private double GIS_X;
    private double GIS_Y;
    private String ZZDM;
    private String JCWDM;
    private String PCS6;
    private String JLXDM;
    private String MLPHXX;
    private String YJMLP;
    private String EJMLP;
    private String SJMLP;

    @Generated(hash = 1578236887)
    public D_MLPH(String MLPBM, String JLXMC, String MLPH, double GIS_X,
            double GIS_Y, String ZZDM, String JCWDM, String PCS6, String JLXDM,
            String MLPHXX, String YJMLP, String EJMLP, String SJMLP) {
        this.MLPBM = MLPBM;
        this.JLXMC = JLXMC;
        this.MLPH = MLPH;
        this.GIS_X = GIS_X;
        this.GIS_Y = GIS_Y;
        this.ZZDM = ZZDM;
        this.JCWDM = JCWDM;
        this.PCS6 = PCS6;
        this.JLXDM = JLXDM;
        this.MLPHXX = MLPHXX;
        this.YJMLP = YJMLP;
        this.EJMLP = EJMLP;
        this.SJMLP = SJMLP;
    }

    @Generated(hash = 1013828998)
    public D_MLPH() {
    }

    public String getMLPBM() {
        return MLPBM;
    }

    public void setMLPBM(String MLPBM) {
        this.MLPBM = MLPBM;
    }

    public String getJLXMC() {
        return JLXMC;
    }

    public void setJLXMC(String JLXMC) {
        this.JLXMC = JLXMC;
    }

    public String getMLPH() {
        return MLPH;
    }

    public void setMLPH(String MLPH) {
        this.MLPH = MLPH;
    }

    public double getGIS_X() {
        return GIS_X;
    }

    public void setGIS_X(double GIS_X) {
        this.GIS_X = GIS_X;
    }

    public double getGIS_Y() {
        return GIS_Y;
    }

    public void setGIS_Y(double GIS_Y) {
        this.GIS_Y = GIS_Y;
    }

    public String getZZDM() {
        return ZZDM;
    }

    public void setZZDM(String ZZDM) {
        this.ZZDM = ZZDM;
    }

    public String getJCWDM() {
        return JCWDM;
    }

    public void setJCWDM(String JCWDM) {
        this.JCWDM = JCWDM;
    }

    public String getPCS6() {
        return PCS6;
    }

    public void setPCS6(String PCS6) {
        this.PCS6 = PCS6;
    }

    public String getJLXDM() {
        return JLXDM;
    }

    public void setJLXDM(String JLXDM) {
        this.JLXDM = JLXDM;
    }

    public String getMLPHXX() {
        return MLPHXX;
    }

    public void setMLPHXX(String MLPHXX) {
        this.MLPHXX = MLPHXX;
    }

    public String getYJMLP() {
        return YJMLP;
    }

    public void setYJMLP(String YJMLP) {
        this.YJMLP = YJMLP;
    }

    public String getEJMLP() {
        return EJMLP;
    }

    public void setEJMLP(String EJMLP) {
        this.EJMLP = EJMLP;
    }

    public String getSJMLP() {
        return SJMLP;
    }

    public void setSJMLP(String SJMLP) {
        this.SJMLP = SJMLP;
    }
}
