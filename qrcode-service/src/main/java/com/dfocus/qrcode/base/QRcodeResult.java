package com.dfocus.qrcode.base;

import com.dfocus.common.base.JSONResult;

/**
 * User: qfwang
 * Date: 2017-09-29
 * Time: 下午5:45
 */
public class QRcodeResult extends JSONResult {
    public QRcodeResult(int code, String msg, Object data) {
        super(code, msg, data);
    }
    public static JSONResult error(QRcodeEnum tipsEnum){
        return error(tipsEnum.getCode(),tipsEnum.getName());
    }
}
