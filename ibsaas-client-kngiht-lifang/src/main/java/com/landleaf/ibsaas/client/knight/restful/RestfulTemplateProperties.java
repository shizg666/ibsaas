package com.landleaf.ibsaas.client.knight.restful;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @Description: 客户端调用配置
* @version V1.0
*/
@ConfigurationProperties(prefix = "ibsaas.web.restful")
public class RestfulTemplateProperties {

    /**
     * 最大连接数
     */
    private int maxTotal = 1500;

    /**
     * 单个路由默认最大连接数
     */
    private int defaultMaxPerRoute = 1500;

    /**
     * 连接超时时间
     */
    private int connectTimeOut = 30 * 1000;

    /**
     * 从连接池中获取连接超时时间
     */
    private int requestTimeOut = 30 * 1000;

    /**
     * 读取超时时间
     */
    private int sockedTimeOut = 60 * 1000;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getRequestTimeOut() {
        return requestTimeOut;
    }

    public void setRequestTimeOut(int requestTimeOut) {
        this.requestTimeOut = requestTimeOut;
    }

    public int getSockedTimeOut() {
        return sockedTimeOut;
    }

    public void setSockedTimeOut(int sockedTimeOut) {
        this.sockedTimeOut = sockedTimeOut;
    }
}
