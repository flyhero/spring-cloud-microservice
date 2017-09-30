package com.dfocus.common.util;

import java.util.Date;

/**
 * User: qfwang
 * Date: 2017-09-29
 * Time: 下午2:23
 */
public class DateUtils {

    public static boolean isExpired(long createTime, long expTime){
        return  new Date(createTime+expTime).before(new Date(System.currentTimeMillis()));
    }
    public static boolean isExpired(long createTime){
        return isExpired(createTime,0);
    }
}
