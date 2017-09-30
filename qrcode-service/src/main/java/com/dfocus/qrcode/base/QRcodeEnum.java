package com.dfocus.qrcode.base;


/**
 * User: qfwang
 * Date: 2017-09-29
 * Time: 下午2:50
 */
public enum QRcodeEnum {
    OK(200,"ok"),
    ERROR(500,"error"),
    EXPIRED(201,"已经过期了"),
    INVALID_TOKEN(1002,"无效的token"),
    SCAN_CLIENT_ERROR(1003,"请使用已登录的APP扫码"),
    PL_UPDATE_QR(1004,"请刷新二维码");

    private int code;
    private String name;

    QRcodeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
