package com.yingde.gaydcj.entity;

import com.yingde.gaydcj.view.SlideView;

/**
 * Created by Administrator on 2017/4/7.
 */

public class PeopleInfo {
    private String regcode;//登记号
    private String chidcard;//身份证
    private String chname;//姓名
    private String imagephoto;//	照片
    private String photoseq;//	照片序号
    private String Sex;//	性别
    private String Mz;//	民族
    private String Birthday;//出生日期
    private String Address;//	住址
    public SlideView slideView;

    public SlideView getSlideView() {
        return slideView;
    }

    public void setSlideView(SlideView slideView) {
        this.slideView = slideView;
    }

    public String getChidcard() {
        return chidcard;
    }

    public void setChidcard(String chidcard) {
        this.chidcard = chidcard;
    }

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getImagephoto() {
        return imagephoto;
    }

    public void setImagephoto(String imagephoto) {
        this.imagephoto = imagephoto;
    }

    public String getMz() {
        return Mz;
    }

    public void setMz(String mz) {
        Mz = mz;
    }

    public String getPhotoseq() {
        return photoseq;
    }

    public void setPhotoseq(String photoseq) {
        this.photoseq = photoseq;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}
