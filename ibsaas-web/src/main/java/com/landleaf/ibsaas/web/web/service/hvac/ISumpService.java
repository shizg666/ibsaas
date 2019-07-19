package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.vo.SumpVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/19 14:27
 * @description:
 */
public interface ISumpService {


    /**
     * 集水坑总览
     * @return
     */
    List<SumpVO> overview();

}
