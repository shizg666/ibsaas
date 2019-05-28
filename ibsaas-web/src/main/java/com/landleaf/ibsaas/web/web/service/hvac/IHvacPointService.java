package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacPointDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * @author Lokiy
 * @date 2019/5/27 15:56
 * @description:
 */
public interface IHvacPointService extends IBaseService<HvacPoint> {

    /**
     * 新增远程设备
     * @param hvacPointDTO
     * @return
     */
    HvacPoint addHvacPoint(HvacPointDTO hvacPointDTO);

    /**
     * 删除远程设备
     * @param id
     */
    void deleteById(String id);

    /**
     * 更新某个硬件设备
     * @param hvacPointDTO
     * @return
     */
    HvacPoint updateById(HvacPointDTO hvacPointDTO);

    /**
     * 获取某个远程设备
     * @param id
     * @return
     */
    HvacPoint getById(String id);

}
