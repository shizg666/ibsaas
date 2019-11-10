package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.FanCoilVO;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/10 14:30
 * @description:
 */
public interface IFanCoilWebService {

    /**
     * 风机盘管总览
     * @return
     */
    List<FanCoilVO> overview();


    /**
     * 格式总览
     * @return
     */
    Map<String, Map<String, FanCoilVO>> totalOverView();

    /**
     * 查看单个风机盘管的值
     * @param id
     * @return
     */
    FanCoilVO getInfoById(String id);

    /**
     * 更新某个风机盘管的值
     * @param fanCoilDTO
     */
    void update(FanCoilDTO fanCoilDTO);
}
