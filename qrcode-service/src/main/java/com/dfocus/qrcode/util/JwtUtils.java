package com.dfocus.qrcode.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static long expiredTime = 60 * 60 * 24 * 30; //一个月

    private static String key = "secret";

    public static String createToken() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        long iat=System.currentTimeMillis();
        long exp=iat+expiredTime;
        String token = JWT.create()
                .withHeader(map)//header
                .withIssuedAt(new Date(iat))
                .withExpiresAt(new Date(exp))
                .withClaim("username", "wang")//payload
                .sign(Algorithm.HMAC256(key));//加密
        System.out.println(new Date(iat));
        System.out.println(new Date(exp));
        return token;
    }
    public static Map<String, Claim> verifyToken(String token){
        Map<String, Claim> claims =null;
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            claims = jwt.getClaims();
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return claims;
    }
}
