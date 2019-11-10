package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacFieldDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * @author Lokiy
 * @date 2019/5/27 15:56
 * @description:
 */
public interface IHvacFieldService extends IBaseService<HvacField> {

    /**
     * 新增远程设备
     * @param hvacFieldDTO
     * @return
     */
    HvacField addHvacField(HvacFieldDTO hvacFieldDTO);

    /**
     * 删除远程设备
     * @param id
     */
    void deleteById(String id);

    /**
     * 更新某个硬件设备
     * @param hvacFieldDTO
     * @return
     */
    HvacField updateById(HvacFieldDTO hvacFieldDTO);

    /**
     * 获取某个远程设备
     * @param id
     * @return
     */
    HvacField getById(String id);

}
