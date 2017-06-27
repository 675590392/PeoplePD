package com.yingde.gaydcj.entity;

import com.yingde.gaydcj.view.SlideView;

/**
 * Created by Administrator on 2016/12/13.
 */

public class ComingSHPeopleAdd {
    String name;
    int type;
    public SlideView slideView;

    public SlideView getSlideView() {
        return slideView;
    }

    public void setSlideView(SlideView slideView) {
        this.slideView = slideView;
    }

    public ComingSHPeopleAdd(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
