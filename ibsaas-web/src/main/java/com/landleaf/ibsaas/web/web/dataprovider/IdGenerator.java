package com.landleaf.ibsaas.web.web.dataprovider;

import com.landleaf.ibsaas.common.utils.id.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wyl
 * @version 1.0
 * @description
 * @date 2019年03月20日 19:51
 */
@Component
public class IdGenerator {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    public String nextId() {
        return String.valueOf(snowflakeIdWorker.nextId());
    }
}
