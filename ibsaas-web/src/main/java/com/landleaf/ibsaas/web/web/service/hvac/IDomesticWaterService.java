package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.DomesticWaterDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.DomesticWaterVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:27
 * @description:
 */
public interface IDomesticWaterService {


    /**
     * 生活水总览
     * @return
     */
    List<DomesticWaterVO> overview();

    /**
     * 修改生活水
     * @param domesticWaterDTO
     */
    void update(DomesticWaterDTO domesticWaterDTO);
}
