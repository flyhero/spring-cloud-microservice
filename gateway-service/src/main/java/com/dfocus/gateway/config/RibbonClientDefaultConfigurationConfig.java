package com.dfocus.gateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: qfwang
 * Date: 2017-10-20 下午3:53
 */
//RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
public class RibbonClientDefaultConfigurationConfig {
}
//@Configuration
class DefaultRibbonConfig {


    //@Bean
    public IRule ribbonRule() {
        return new BestAvailableRule();
    }

    //@Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

    //@Bean
    public ServerList<Server> ribbonServerList() {
        return new CustomRibbonConfig();
    }

    //@Bean
    public ServerListSubsetFilter serverListFilter() {
        ServerListSubsetFilter filter = new ServerListSubsetFilter();
        return filter;
    }

}