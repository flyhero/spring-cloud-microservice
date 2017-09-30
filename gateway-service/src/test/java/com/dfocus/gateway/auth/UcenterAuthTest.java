package com.dfocus.gateway.auth;

import com.dfocus.gateway.GatewayServiceApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:api_gateway_test.properties")
public class UcenterAuthTest {

    @Autowired
    private UcenterAuth ucenterAuth;
    @Test
    public void testGetToken(){
        System.out.println("this is a test");
        Map<String,Object> map=new HashMap<>();
        map.put("username","wang");
        try {
            String token = ucenterAuth.getToken(map);
            Assert.assertNotEquals(token,null);
            System.out.println(ucenterAuth.getToken(map));
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    @Test
    public void testverifyTokenFail(){
        String token="eyJhbGciOiJIUzI1NiIsInVzZXJuYW1lIjoid2FuZyJ9.eyJle" +
                "HAiOjE1MDUxODc4MDgsImlhdCI6MTUwNTE4Nzc0OCwidXNlcm5hbWUiOiJ3YW" +
                "5nIn0.0mNMn9RwZItUMxPLM7DqP40JekDJT8qaiad0CbdfEho";
        String t="eyJhbGciOiJIUzI1NiIsInVzZXJuYW1lIjoid2FuZyJ9.eyJleHAiOjE1MDc" +
                "3ODA1NDEsImlhdCI6MTUwNTE4ODU0MSwidXNlcm5hbWUiOiJ3YW5nIn0.nPC" +
                "A-UBZ-U_ibzIyloa95MTTdFS-UsqufBaAE9IvW0Q";
        try {
            System.out.println(ucenterAuth.verify(token));
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Test
    public void testverifyTokenSuccess(){
        String token="eyJhbGciOiJIUzI1NiIsInVzZXJuYW1lIjoid2FuZyJ9.eyJleHAiOjE1MDc" +
                "3ODA1NDEsImlhdCI6MTUwNTE4ODU0MSwidXNlcm5hbWUiOiJ3YW5nIn0.nPC" +
                "A-UBZ-U_ibzIyloa95MTTdFS-UsqufBaAE9IvW0Q";
        try {
            System.out.println(ucenterAuth.verify(token));
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
