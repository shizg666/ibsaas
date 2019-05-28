package com.landleaf.ibsaas.web.web.service.hvac;

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
}
