package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.ExhaustBlowerDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.ExhaustBlowerVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:27
 * @description:
 */
public interface IExhaustBlowerService {


    /**
     * 排风机总览
     * @return
     */
    List<ExhaustBlowerVO> overview();

    /**
     * 修改排风机
     * @param exhaustBlowerDTO
     */
    void update(ExhaustBlowerDTO exhaustBlowerDTO);
}
