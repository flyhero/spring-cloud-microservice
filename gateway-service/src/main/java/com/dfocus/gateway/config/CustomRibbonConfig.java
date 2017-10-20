package com.dfocus.gateway.config;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * Author: qfwang
 * Date: 2017-10-20 下午3:27
 */
public class CustomRibbonConfig extends AbstractServerList<Server> {

    private IClientConfig clientConfig;

    public CustomRibbonConfig() {
    }

    public List<Server> getInitialListOfServers() {
        return this.getUpdatedListOfServers();
    }

    public List<Server> getUpdatedListOfServers() {
        String listOfServers = "http://localhost:8990/,http://localhost:8991/";
        try {
            ClientFactory.createNamedClient("qrcode",new DefaultClientConfigImpl().set(CommonClientConfigKey.ListOfServers,listOfServers).getClass());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("================serverlist");
        //从数据库获取负载均衡的服务器地址

        return this.derive(listOfServers);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    private List<Server> derive(String value) {
        List<Server> list = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(value)) {
            String[] var3 = value.split(",");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                list.add(new Server(s.trim()));
            }
        }

        return list;
    }

}
