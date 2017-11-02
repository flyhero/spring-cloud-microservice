package com.dfocus.gateway.ribbon;

import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicStringProperty;
import org.apache.commons.configuration.Configuration;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: qfwang
 * Date: 2017-10-31 下午2:13
 */
public class CustomClientConfigImpl extends DefaultClientConfigImpl{

    public CustomClientConfigImpl() {
        super();
    }

    @Override
    public void loadProperties(String restClientName) {
        this.setClientName(restClientName);
        this.loadDefaultValues();
        String prop = "ribbon.listOfServers";
        String values= "http://localhost:8990/,http://localhost:8991/";
            try {
                this.setPropertyInternal(prop, values);
            } catch (Exception var7) {
                throw new RuntimeException(String.format("Property %s is invalid", values));
            }

    }
}
