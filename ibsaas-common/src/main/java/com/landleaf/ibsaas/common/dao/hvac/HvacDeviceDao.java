package com.landleaf.ibsaas.common.dao.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:42
 * @description:
 */
public interface HvacDeviceDao extends BaseDao<HvacDevice> {

    /**
     * 查询所有未删除的设备
     * @param type
     * @return
     */
    List<HvacDevice> all(@Param("type") Integer type);

    /**
     * 获取网络控制器的所有路由
     * @return
     */
    List<HvacDevice> groupByNetwork();

    /**
     * 根据设备号获取设备信息
     * @param deviceInstanceNumber
     * @return
     */
    HvacDevice getByDeviceInstanceNumber(@Param("deviceInstanceNumber") Integer deviceInstanceNumber);


    /**
     * 查询所有id不对的
     * @param maxId
     * @return
     */
    List<HvacDevice> getHvacDeviceLmt(@Param("maxId") Integer maxId);
}
