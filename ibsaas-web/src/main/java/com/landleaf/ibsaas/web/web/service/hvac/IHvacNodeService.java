package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacNodeDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * @author Lokiy
 * @date 2019/5/27 15:56
 * @description:
 */
public interface IHvacNodeService extends IBaseService<HvacNode> {

    /**
     * 新增远程设备
     * @param hvacNodeDTO
     * @return
     */
    HvacNode addHvacNode(HvacNodeDTO hvacNodeDTO);

    /**
     * 删除远程设备
     * @param id
     */
    void deleteById(String id);

    /**
     * 更新某个硬件设备
     * @param hvacNodeDTO
     * @return
     */
    HvacNode updateById(HvacNodeDTO hvacNodeDTO);

    /**
     * 获取某个远程设备
     * @param id
     * @return
     */
    HvacNode getById(String id);

}
