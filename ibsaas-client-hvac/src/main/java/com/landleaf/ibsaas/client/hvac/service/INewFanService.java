package com.landleaf.ibsaas.client.hvac.service;

import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 16:15
 * @description:
 */
public interface INewFanService {

    /**
     * 四效新风机总览
     * @return
     */
    List<NewFanVO> overview();

    /**
     * 单个四效新风机
     * @param id
     * @return
     */
    NewFanVO getInfoById(String id);

    /**
     * 更新某项值
     * @param newFanDTO
     */
    void update(NewFanDTO newFanDTO);
}
