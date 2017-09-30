package com.dfocus.qrcode.vo;

/**
 * User: qfwang
 * Date: 2017-09-29
 * Time: 下午4:14
 */
public class Info {
    private String clientId;
    private long createTime;
    private String type;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Info{" +
                "clientId='" + clientId + '\'' +
                ", createTime=" + createTime +
                ", type='" + type + '\'' +
                '}';
    }
}
