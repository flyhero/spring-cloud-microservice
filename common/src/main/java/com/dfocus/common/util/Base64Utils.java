package com.dfocus.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * User: qfwang
 * Date: 2017-09-29
 * Time: 上午10:33
 */
public class Base64Utils {

    /**
     * base64编码
     * @param text
     * @return
     */
    public static String encoder(String text){
        String encodedText =null;
        try {
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] textByte = text.getBytes("UTF-8");
            //编码
            encodedText = encoder.encodeToString(textByte);
        }catch (UnsupportedEncodingException e){
            System.out.println(e.toString());
        }

        System.out.println(encodedText);

        return encodedText;
    }

    /**
     * base64解码
     * @param text
     * @return
     */
    public static String decoder(String text) throws Exception{
        String decodedText = null;
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final byte[] textByte = text.getBytes("UTF-8");
            decodedText = new String(decoder.decode(textByte), "UTF-8");
        }catch (UnsupportedEncodingException e){
            System.out.println(e.toString());
        }

        return decodedText;
    }
}
