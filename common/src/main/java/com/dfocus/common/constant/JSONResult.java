package com.dfocus.common.constant;

/**
 * Description:
 * User: qfwang
 * Date: 2017-09-14
 * Time: 上午09:50
 */
public class JSONResult {
    protected int code;
    protected String msg;
    protected Object data;

    public JSONResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JSONResult ok(TipEnum tipEnum,Object data){
        return new JSONResult(tipEnum.getCode(),tipEnum.getMsg(),data);
    }
    public static JSONResult ok(Object data){
        return  ok(TipEnum.ok,data);
    }
    public static JSONResult ok(TipEnum tipEnum){
        return  ok(tipEnum,null);
    }
    public static JSONResult ok(){
        return ok(null);
    }

    public static JSONResult error(TipEnum tipEnum,Object data){
        return new JSONResult(tipEnum.getCode(),tipEnum.getMsg(),data);
    }
    public static JSONResult error(Object data){
        return  ok(TipEnum.ok,data);
    }
    public static JSONResult error(TipEnum tipEnum){
        return  ok(tipEnum,null);
    }
    public static JSONResult error(){
        return ok(null);
    }
}
