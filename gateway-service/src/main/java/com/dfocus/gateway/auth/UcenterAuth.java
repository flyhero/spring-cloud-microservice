package com.dfocus.gateway.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UcenterAuth implements Auth{

    private static long expiredTime = 60 * 60 * 24 * 30; //一个月

    private static String key = "secret";
    @Override
    public String getToken(Map<String, Object> map) throws Exception{
        String username= (String) map.get("username");
        Map<String, Object> mapHeader = new HashMap<String, Object>();
        mapHeader.put("alg", "HS256");
        mapHeader.put("typ", "JWT");
        long iat=System.currentTimeMillis();
        long exp=iat+ expiredTime*1000l;
        String token = JWT.create()
                .withHeader(map)//header
                .withIssuedAt(new Date(iat))
                .withExpiresAt(new Date(exp))
                .withClaim("username", username)//payload
                .sign(Algorithm.HMAC256(key));//加密
        return token;
    }

    @Override
    public Map<String, Claim> verify(String token){
        System.out.println("================正在验证token==================");
        DecodedJWT jwt=null;
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                    .build();
            jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            //System.out.println("====用户名===="+claims.get("username").asString());
            return  jwt.getClaims();
        }catch (Exception e){
            System.out.println(e.toString());
            return null;
        }

    }
}
