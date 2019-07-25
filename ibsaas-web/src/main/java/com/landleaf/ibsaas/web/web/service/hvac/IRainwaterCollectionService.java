package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.RainwaterCollectionDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.RainwaterCollectionVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:27
 * @description:
 */
public interface IRainwaterCollectionService {


    /**
     * 雨水收集总览
     * @return
     */
    List<RainwaterCollectionVO> overview();

    /**
     * 修改雨水收集
     * @param rainwaterCollectionDTO
     */
    void update(RainwaterCollectionDTO rainwaterCollectionDTO);
}
