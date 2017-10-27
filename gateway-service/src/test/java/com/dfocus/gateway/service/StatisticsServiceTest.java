package com.dfocus.gateway.service;

import com.dfocus.gateway.GatewayServiceApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author: qfwang
 * Date: 2017-10-27 下午3:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GatewayServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:api_gateway_test.properties")
public class StatisticsServiceTest {

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testLogApiInfo(){
        StatisticsService.ApiStatistics apiStatistics=statisticsService.newApiStatistics();
        apiStatistics.execTime=123;
        apiStatistics.apiName= "wang";
        Assert.assertNotNull(apiStatistics);
        System.out.println(apiStatistics);
    }

}
