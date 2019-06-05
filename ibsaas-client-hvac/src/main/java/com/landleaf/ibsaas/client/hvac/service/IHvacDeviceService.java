package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacDeviceDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:56
 * @description:
 */
public interface IHvacDeviceService extends IBaseService<HvacDevice> {

    /**
     * 新增远程设备
     * @param hvacDeviceDTO
     * @return
     */
    HvacDevice addHvacDevice(HvacDeviceDTO hvacDeviceDTO);

    /**
     * 删除远程设备
     * @param id
     */
    void deleteById(String id);

    /**
     * 更新某个硬件设备
     * @param hvacDeviceDTO
     * @return
     */
    HvacDevice updateById(HvacDeviceDTO hvacDeviceDTO);

    /**
     * 获取某个远程设备
     * @param id
     * @return
     */
    HvacDevice getById(String id);


    /**
     * 查询所有设备
     * @return
     */
    List<HvacDevice> all();


}
