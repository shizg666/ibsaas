package com.landleaf.ibsaas.client.hvac.util;

import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.domain.BasicEntity;

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
    private IdGeneratorEx idGeneratorEx;

    /**
     * 完善新增操作信息
     * @param t
     */
    public void consummateAddOperation(T t){
        Date now = new Date();
//        String userCode = UserContext.getCurrentUser().getUserCode();
        String userCode = "test";
        t.setId(idGeneratorEx.nextId());
        t.setActive(IbsaasConstant.ACTIVE);
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
//        String userCode = UserContext.getCurrentUser().getUserCode();
        String userCode = "test";
        t.setModifyTime(now);
        t.setModifyUserCode(userCode);
    }

    /**
     * 完善删除操作信息
     * @param t
     */
    public void consummateDeleteOperation(T t){
        Date now = new Date();
//        String userCode = UserContext.getCurrentUser().getUserCode();
        String userCode = "test";
        t.setActive(IbsaasConstant.IN_ACTIVE);
        t.setModifyTime(now);
        t.setModifyUserCode(userCode);
    }


    /**
     * 跑批更新id为long
     * @param t
     */
    public void runningBatch(T t){
        String userCode = "test";
        t.setId(idGeneratorEx.nextId());
        t.setCreateUserCode(userCode);
        t.setModifyUserCode(userCode);
    }
}
