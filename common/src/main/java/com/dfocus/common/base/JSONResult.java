package com.dfocus.common.base;

/**
 * 返回json数据格式
 * User: qfwang
 * Date: 2017-09-14
 * Time: 上午09:50
 */
public class JSONResult {
    private int code;
    private String msg;
    private Object data;

    public JSONResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JSONResult ok() {
        return ok(null);
    }
    public static JSONResult ok(Object data) {
        return new JSONResult(200,"success",data);
    }
    public static JSONResult error(Object data) {
        return new JSONResult(500,"error",data);
    }

    public static JSONResult error(int code, String msg){
        return new JSONResult(code,msg,null);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

