package com.dfocus.gateway.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dfocus.common.util.HttpUtils;
import com.netflix.zuul.context.RequestContext;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author: qfwang
 * Date: 2017-10-27 下午3:10
 */
@Service
public class StatisticsService {

    private ProxyRequestHelper proxyRequestHelper = new ProxyRequestHelper();
    public String logApiInfo() {
        RequestContext ctx = RequestContext.getCurrentContext();
        long endTimeMillis = System.currentTimeMillis();
        long startTimeMillis = (long)ctx.get("startTimeMillis");
        long execTimeMillis = endTimeMillis - startTimeMillis;

        HttpServletRequest request = (HttpServletRequest)ctx.get("requestInfo");
        ClientHttpResponse response = (ClientHttpResponse)ctx.get("zuulResponse");

        ApiStatistics apiStatistics =new ApiStatistics();


        apiStatistics.setRequestTime(startTimeMillis);
        apiStatistics.setResponseTime(endTimeMillis);
        apiStatistics.setExecTime((int) execTimeMillis);
        apiStatistics.setIp(HttpUtils.getIPAddr(ctx.getRequest()));
        apiStatistics.setFrontendPath(request.getRequestURI());
        //apiStatistics.setBackendPath(route.getPath());
        apiStatistics.setRequestHeaders((HttpHeaders) proxyRequestHelper.buildZuulRequestHeaders(request));
        int status = 0;
        try {
            if(response != null){ //多实例，走负载均衡
                apiStatistics.setStatusCode(response.getRawStatusCode());
                apiStatistics.setResponseHeaders(response.getHeaders());
            }else {
                if(ctx.containsKey("sendErrorFilter.ran")){ //出现异常
                    status = (int)ctx.getRequest().getAttribute("javax.servlet.error.status_code");
                }else {
                    status =ctx.getResponse().getStatus();
                }
                apiStatistics.setResponseHeaders(new HttpHeaders());
                apiStatistics.setStatusCode(status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        apiStatistics.setResponseBody(ctx.getResponseBody());
        apiStatistics.setMethod(request.getMethod());
        apiStatistics.setQueryString(request.getQueryString());

        String apiInfo= JSON.toJSONString(apiStatistics);
        return apiInfo;
    }

    public static String ggg(){
        StatisticsService statisticsService=new StatisticsService();
        ApiStatistics apiStatistics =statisticsService.newApiStatistics();
/*        apiStatistics.setRequestAppKey("wang");
        apiStatistics.setRequestKey("wang");*/

        apiStatistics.setRequestTime(12321L);
        apiStatistics.setResponseTime(235343);
        String apiInfo= JSON.toJSONString(apiStatistics);
        return apiInfo;
    }

    public static void main(String[] args) {
        System.out.println(ggg());
    }
    public ApiStatistics newApiStatistics(){
        return new ApiStatistics();
    }

    class ApiStatistics{
        public ApiStatistics() {
        }

/*        @JSONField(name="request_appkey")
        String requestAppKey;

        @JSONField(name="request_key")
        String requestKey;*/

        @JSONField(name="ip")
        String ip;

        @JSONField(name="request_host")
        String requestHost;

        @JSONField(name="request_body")
        String requestBody;

        @JSONField(name="response_body")
        String responseBody;

        @JSONField(name="request_time")
        long requestTime;

        @JSONField(name="response_time")
        long responseTime;

        @JSONField(name="request_date")
        String requestDate;

        @JSONField(name="response_date")
        String responseDate;

        @JSONField(name="api_name")
        String apiName;

        @JSONField(name="frontend_path")
        String frontendPath;

        @JSONField(name="backend_path")
        String backendPath;

        @JSONField(name="business_type")
        String businessType;

        @JSONField(name="business_func")
        String businessFunc;

        @JSONField(name="api_version")
        String apiVersion;

        @JSONField(name="method_path")
        String methodPath;

        @JSONField(name="query_string")
        String queryString;

        @JSONField(name="method")
        String method;

        @JSONField(name="request_headers")
        HttpHeaders requestHeaders;

        @JSONField(name="response_headers")
        HttpHeaders responseHeaders;

        @JSONField(name="status_code")
        int statusCode;

        @JSONField(name="exec_time")
        int execTime;


        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getRequestHost() {
            return requestHost;
        }

        public void setRequestHost(String requestHost) {
            this.requestHost = requestHost;
        }

        public String getRequestBody() {
            return requestBody;
        }

        public void setRequestBody(String requestBody) {
            this.requestBody = requestBody;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }

        public long getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(long requestTime) {
            this.requestTime = requestTime;
        }

        public long getResponseTime() {
            return responseTime;
        }

        public void setResponseTime(long responseTime) {
            this.responseTime = responseTime;
        }

        public String getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        public String getResponseDate() {
            return responseDate;
        }

        public void setResponseDate(String responseDate) {
            this.responseDate = responseDate;
        }

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getFrontendPath() {
            return frontendPath;
        }

        public void setFrontendPath(String frontendPath) {
            this.frontendPath = frontendPath;
        }

        public String getBackendPath() {
            return backendPath;
        }

        public void setBackendPath(String backendPath) {
            this.backendPath = backendPath;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getBusinessFunc() {
            return businessFunc;
        }

        public void setBusinessFunc(String businessFunc) {
            this.businessFunc = businessFunc;
        }

        public String getApiVersion() {
            return apiVersion;
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }

        public String getMethodPath() {
            return methodPath;
        }

        public void setMethodPath(String methodPath) {
            this.methodPath = methodPath;
        }

        public String getQueryString() {
            return queryString;
        }

        public void setQueryString(String queryString) {
            this.queryString = queryString;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public HttpHeaders getRequestHeaders() {
            return requestHeaders;
        }

        public void setRequestHeaders(HttpHeaders requestHeaders) {
            this.requestHeaders = requestHeaders;
        }

        public HttpHeaders getResponseHeaders() {
            return responseHeaders;
        }

        public void setResponseHeaders(HttpHeaders responseHeaders) {
            this.responseHeaders = responseHeaders;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getExecTime() {
            return execTime;
        }

        public void setExecTime(int execTime) {
            this.execTime = execTime;
        }
    }
}
