package com.yingde.gaydcj.entity;

/**
 * Created by tanghao on 2017/3/2.
 */

public class Entity<T> {

    /**
     * Token : 84A2E3D86BE94227B2245A526BB00887
     * State : 100
     * Msg : 登录成功.
     * Data :
     */

    private String Token;
    private String State;
    private String Msg;
    private T Data;

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
