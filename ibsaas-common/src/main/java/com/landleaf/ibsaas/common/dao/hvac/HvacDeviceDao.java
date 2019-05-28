package com.landleaf.ibsaas.common.dao.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;


import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:42
 * @description:
 */
public interface HvacDeviceDao extends BaseDao<HvacDevice> {

    /**
     * 查询所有未删除的设备
     * @return
     */
    List<HvacDevice> all();


    /**
     * 根据设备号获取设备信息
     * @param deviceInstanceNumber
     * @return
     */
    HvacDevice getByDeviceInstanceNumber(Integer deviceInstanceNumber);
}
