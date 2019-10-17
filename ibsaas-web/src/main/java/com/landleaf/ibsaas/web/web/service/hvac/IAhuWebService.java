package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.AhuDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AhuVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/4 10:01
 * @description:
 */
public interface IAhuWebService {

    /**
     * AHU总览
     * @return
     */
    List<AhuVO> overview();

    /**
     * 修改AHU
     * @param ahuDTO
     */
    void update(AhuDTO ahuDTO);
}
