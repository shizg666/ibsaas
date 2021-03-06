package com.landleaf.ibsaas.client.hvac.config.id;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description ID 生成器配置类
 */
@ConfigurationProperties(prefix = "id.generate")
public class IdGeneratePropertiesEx {

    /**
     * 机器编号
     */
    private int workId;
    /**
     * 数据中心编号
     */
    private int datacenterId;

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public int getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(int datacenterId) {
        this.datacenterId = datacenterId;
    }
}
