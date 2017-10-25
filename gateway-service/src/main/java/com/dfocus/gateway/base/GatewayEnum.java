package com.dfocus.gateway.base;

/**
 * Description:
 * User: qfwang
 * Date: 2017-09-14
 * Time: 上午10:46
 */
public enum GatewayEnum {

    ok(200,"ok"),
    MISS_TOKEN(401,"令牌丢失"),
    EXPIRED(201,"已经过期了"),
    INVALID_TOKEN(1002,"无效的token"),
    ACCESS_DENIED(506,"拒绝访问"),
    error(500,"error");

    protected int code;
    protected String msg;

    GatewayEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code)
    {
        for (GatewayEnum tipEnum : GatewayEnum.values()) {
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
