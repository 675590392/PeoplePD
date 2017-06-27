package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/7.
 */

public class StreetRoadAlley {
    /**
     * JLXDM : 252771
     * JLXMC : 五、根据GIS查找街路巷信息--根据GIS（X,Y）信息在D_MLPH中确定附近的街巷路代码和名称
     */
    //街路巷代码
    private String JLXDM;
    //街路巷名称
    private String JLXMC;
    //经度
    private String GIS_X;
    //纬度
    private String GIS_Y;

    public String getJLXDM() {
        return JLXDM;
    }

    public void setJLXDM(String JLXDM) {
        this.JLXDM = JLXDM;
    }

    public String getJLXMC() {
        return JLXMC;
    }

    public void setJLXMC(String JLXMC) {
        this.JLXMC = JLXMC;
    }

    public String getGIS_X() {
        return GIS_X;
    }

    public void setGIS_X(String GIS_X) {
        this.GIS_X = GIS_X;
    }

    public String getGIS_Y() {
        return GIS_Y;
    }

    public void setGIS_Y(String GIS_Y) {
        this.GIS_Y = GIS_Y;
    }
}
