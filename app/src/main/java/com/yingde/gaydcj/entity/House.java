package com.yingde.gaydcj.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/7.
 */

public class House  implements Serializable {
    public String FWBM;//房屋编码
    public String mlphxx;//门牌号信息
    public String sh;//室号

    public String getFWBM() {
        return FWBM;
    }

    public void setFWBM(String FWBM) {
        this.FWBM = FWBM;
    }

    public String getSh() {
        return sh;
    }

    public void setSh(String sh) {
        this.sh = sh;
    }

    public String getMlphxx() {
        return mlphxx;
    }

    public void setMlphxx(String mlphxx) {
        this.mlphxx = mlphxx;
    }
}
