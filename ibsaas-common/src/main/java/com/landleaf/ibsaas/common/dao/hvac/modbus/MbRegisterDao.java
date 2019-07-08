package com.landleaf.ibsaas.common.dao.hvac.modbus;

import com.landleaf.ibsaas.common.domain.hvac.assist.MbRegisterDetail;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbRegister;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/5 16:04
 * @description:
 */
public interface MbRegisterDao extends BaseDao<MbRegister> {

    /**
     * 查询对应id
     * @param maxId
     * @return
     */
    List<MbRegister> getMbRegisterLmt(@Param("maxId") Integer maxId);

    /**
     * 所有有效寄存器地址
     * @return
     */
    List<MbRegisterDetail> allMbRegisterDetails();

}
