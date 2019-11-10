package com.landleaf.ibsaas.common.privoder;

import com.landleaf.ibsaas.common.domain.Base;
import com.landleaf.ibsaas.common.utils.id.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertDbPrivoder {
    @Autowired(required = false)
    SnowflakeIdWorker snowflakeIdWorker;

    public Long nextId() {
        return snowflakeIdWorker.nextId();
    }

    public void saveBuild(Base base){
        long now = System.currentTimeMillis();
        base.setId(nextId());
        base.setCreatetime(now);
        base.setUpdatetime(now);
    }
}
