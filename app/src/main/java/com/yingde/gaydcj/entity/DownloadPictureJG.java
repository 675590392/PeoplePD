package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/20.
 */

public class DownloadPictureJG {
    private String imagephoto;//照片Byte数组
    private String regcode;//登记号
    private String photoseq;//	照片序号

    public String getImagephoto() {
        return imagephoto;
    }

    public void setImagephoto(String imagephoto) {
        this.imagephoto = imagephoto;
    }

    public String getPhotoseq() {
        return photoseq;
    }

    public void setPhotoseq(String photoseq) {
        this.photoseq = photoseq;
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }
}
