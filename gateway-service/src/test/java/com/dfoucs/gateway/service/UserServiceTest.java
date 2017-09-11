package com.dfoucs.gateway.service;

import com.dfoucs.gateway.GatewayServiceApplication;
import com.dfoucs.gateway.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:api_gateway_test.properties")
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userServiceimpl;
    @Test
    public void testGetAllUsers(){
        int num=userServiceimpl.getAllUsers();
        System.out.println("记录总计："+num);
    }

}
