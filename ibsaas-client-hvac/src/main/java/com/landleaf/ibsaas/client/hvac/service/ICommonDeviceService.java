package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 15:40
 * @description:
 */
public interface ICommonDeviceService {

    /**
     * 统一获取固定设备当前所有节点的值
     * @param deviceInstanceNumber
     * @return
     */
    List<? extends BaseDevice> getCurrentData(Integer deviceInstanceNumber);

    /**
     * 获取单个节点当前信息
     * @param hvacNodeVO
     * @param <T>
     * @return
     */
    <T extends BaseDevice> T getCurrentInfo(HvacNodeVO hvacNodeVO);


    /**
     * 当前输入入redis库
     */
    void currentDataToRedis();

    /**
     * 写入设备数据
     * @param t
     * @param <T>
     */
    <T extends BaseDevice> void writeDevice(T t);
}
