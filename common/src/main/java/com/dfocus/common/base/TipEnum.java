package com.dfocus.common.base;

/**
 * Description:
 * User: qfwang
 * Date: 2017-09-14
 * Time: 上午10:46
 */
public enum TipEnum {

    ok(200,"ok"),
    error(500,"error");

    protected int code;
    protected String msg;

    TipEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code)
    {
        for (TipEnum tipEnum : TipEnum.values()) {
            if (tipEnum.getCode()== code) {
                return tipEnum.msg;
            }
        }
        return null;
    }    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
