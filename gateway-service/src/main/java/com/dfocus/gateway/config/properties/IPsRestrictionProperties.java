package com.dfocus.gateway.config.properties;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Author: qfwang
 * Date: 2017-10-24 下午7:20
 */
@RefreshScope
@Component
@ConfigurationProperties(prefix = "zuul.ip")
public class IPsRestrictionProperties {
    private boolean enabled;
    private Map<String,IPList> ips = Maps.newHashMap();

    public IPsRestrictionProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public Optional<IPList> getIps(String key) {
        return Optional.ofNullable(ips.get(key));
    }

    public void setIps(Map<String, IPList> ips) {
        this.ips = ips;
    }

    public static class IPList{
        private String blacklist;
        private String whitelist;

        public String getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(String blacklist) {
            this.blacklist = blacklist;
        }

        public String getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(String whitelist) {
            this.whitelist = whitelist;
        }
    }
}
