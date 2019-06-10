package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.vo.HydraulicModuleVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/10 13:33
 * @description:
 */
public interface IHydraulicModuleWebService {

    /**
     * 水力模块总览
     * @return
     */
    List<HydraulicModuleVO> overview();

    /**
     * 查询单个信息
     * @param id
     * @return
     */
    HydraulicModuleVO getInfoById(String id);


    /**
     * 修改某个水力模块的
     * @param hydraulicModuleVO
     */
    void update(HydraulicModuleVO hydraulicModuleVO);
}
