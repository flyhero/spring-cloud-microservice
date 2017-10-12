package com.dfocus.gateway.event;

import com.dfocus.gateway.GatewayServiceApplication;
import com.dfocus.gateway.route.CustomRouteLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Author: qfwang
 * Date: 2017-10-12 上午11:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:api_gateway_test.properties")
public class RefreshRouteServiceTest {

    @Autowired
    RefreshRouteService refreshRouteService;

    @Test
    public void testAddRoute() {
        CustomRouteLocator.ZuulRouteVO zuulRouteVO = new CustomRouteLocator.ZuulRouteVO();
        zuulRouteVO.setPath("/testapi/**");
        zuulRouteVO.setServiceId("test-service");
        zuulRouteVO.setUrl("http://localhost:8070/");
        zuulRouteVO.setEnabled(false);
        zuulRouteVO.setApiName("testapi");

        Assert.assertNotNull("不能为空", zuulRouteVO);
        int num = refreshRouteService.addRoute(zuulRouteVO);
        Assert.assertNotEquals(num, 0);

    }

    @Test
    public void testFindRouteList() {
        List<CustomRouteLocator.ZuulRouteVO> list = refreshRouteService.findRouteList();
        Assert.assertNotNull("can not be null", list);
        for (CustomRouteLocator.ZuulRouteVO zuulRouteVO : list) {
            System.out.println(zuulRouteVO.getServiceId());
        }
        System.out.println("===================================");
        List<CustomRouteLocator.ZuulRouteVO> list1 = refreshRouteService.findRouteList(false);
        Assert.assertNotNull("can not be null", list1);
        for (CustomRouteLocator.ZuulRouteVO zuulRouteVO : list1) {
            System.out.println(zuulRouteVO.getServiceId());
        }
    }
}
