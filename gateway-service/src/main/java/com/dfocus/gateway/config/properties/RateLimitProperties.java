/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dfocus.gateway.config.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * @author Marcos Barbero
 */
@RefreshScope
@Component
@ConfigurationProperties(RateLimitProperties.PREFIX)
public class RateLimitProperties {

    public static final String PREFIX = "zuul.ratelimit";

    private Policy defaultPolicy;

    @NotNull
    private Map<String, Policy> policies = Maps.newHashMap();

    private boolean behindProxy;

    private boolean enabled;

    @NotNull
    @Value("${spring.application.name:rate-limit-application}")
    private String keyPrefix;

    public RateLimitProperties() {
    }

    @NotNull
    private Repository repository = Repository.IN_MEMORY;

    public enum Repository {
        REDIS, CONSUL, JPA, IN_MEMORY
    }

    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, defaultPolicy));
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public Policy getDefaultPolicy() {
        return defaultPolicy;
    }

    public void setDefaultPolicy(Policy defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }

    public Map<String, Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(Map<String, Policy> policies) {
        this.policies = policies;
    }

    public boolean isBehindProxy() {
        return behindProxy;
    }

    public void setBehindProxy(boolean behindProxy) {
        this.behindProxy = behindProxy;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public static class Policy {

        @NotNull
        private Long refreshInterval = MINUTES.toSeconds(1L);

        private Long limit;

        private Long quota;

        @NotNull
        private List<Type> type = Lists.newArrayList();

        public enum Type {
            ORIGIN, USER, URL
        }

        public Long getRefreshInterval() {
            return refreshInterval;
        }

        public void setRefreshInterval(Long refreshInterval) {
            this.refreshInterval = refreshInterval;
        }

        public Long getLimit() {
            return limit;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        public Long getQuota() {
            return quota;
        }

        public void setQuota(Long quota) {
            this.quota = quota;
        }

        public List<Type> getType() {
            return type;
        }

        public void setType(List<Type> type) {
            this.type = type;
        }
    }
}