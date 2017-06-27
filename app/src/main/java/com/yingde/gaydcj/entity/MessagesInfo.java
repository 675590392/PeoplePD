package com.yingde.gaydcj.entity;

/**
 * Created by Administrator on 2017/4/11.
 */

public class MessagesInfo {
    public String CODE;//	通知ID
    public String TITLE;//	主题
    public String MessageType;//	01 通知通告02 工作提醒
    public String PUBLISHER;//	发布人姓名
    public String SAVEDATE;//	发布日期
    public String ISREADED;//	是否阅读0:未阅读1:已阅读
    public String READDATE;//	阅读日期

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getISREADED() {
        return ISREADED;
    }

    public void setISREADED(String ISREADED) {
        this.ISREADED = ISREADED;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getPUBLISHER() {
        return PUBLISHER;
    }

    public void setPUBLISHER(String PUBLISHER) {
        this.PUBLISHER = PUBLISHER;
    }

    public String getREADDATE() {
        return READDATE;
    }

    public void setREADDATE(String READDATE) {
        this.READDATE = READDATE;
    }

    public String getSAVEDATE() {
        return SAVEDATE;
    }

    public void setSAVEDATE(String SAVEDATE) {
        this.SAVEDATE = SAVEDATE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
