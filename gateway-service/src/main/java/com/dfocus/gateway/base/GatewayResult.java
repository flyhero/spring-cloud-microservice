package com.dfocus.gateway.base;

import com.dfocus.common.base.JSONResult;

/**
 * 网关返回信息
 * Author: qfwang
 * Date: 2017-10-11 下午7:00
 */
public class GatewayResult extends JSONResult{
    public GatewayResult(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
