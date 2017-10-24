package com.dfocus.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Author: qfwang
 * Date: 2017-10-24 下午7:37
 */
@Configuration
@ConditionalOnProperty(prefix = "zuul.ip",name = "enabled",havingValue = "true")
public class IPsConfig {

}
