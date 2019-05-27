package com.landleaf.ibsaas.web.web.util;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/5/27 17:29
 * @description: 入库帮助类
 */
@Component
public class DaoAdapter<T extends BasicEntity> {

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 完善新增操作信息
     * @param t
     */
    public void consummateAddOperation(T t){
        Date now = new Date();
        String userCode = UserContext.getCurrentUser().getUserCode();
        t.setId(idGenerator.nextId());
        t.setActive(IbsaasWebConstants.ACTIVE);
        t.setCreateTime(now);
        t.setCreateUserCode(userCode);
        t.setModifyTime(now);
        t.setModifyUserCode(userCode);
    }

    /**
     * 完善更新操作信息
     * @param t
     */
    public void consummateUpdateOperation(T t){
        Date now = new Date();
        String userCode = UserContext.getCurrentUser().getUserCode();
        t.setModifyTime(now);
        t.setModifyUserCode(userCode);
    }

    /**
     * 完善删除操作信息
     * @param t
     */
    public void consummateDeleteOperation(T t){
        Date now = new Date();
        String userCode = UserContext.getCurrentUser().getUserCode();
        t.setActive(IbsaasWebConstants.INACTIVE);
        t.setModifyTime(now);
        t.setModifyUserCode(userCode);
    }


    /**
     * 跑批更新id为long
     * @param t
     */
    public void runningBatch(T t){
        String userCode = "test";
        t.setId(idGenerator.nextId());
        t.setCreateUserCode(userCode);
        t.setModifyUserCode(userCode);
    }
}
