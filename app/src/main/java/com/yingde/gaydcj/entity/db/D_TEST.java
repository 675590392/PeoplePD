package com.yingde.gaydcj.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/17.
 */
@Entity
public class D_TEST {
    @Id
    private long id;
    private String name;
    @Generated(hash = 2072585620)
    public D_TEST(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1986121772)
    public D_TEST() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
