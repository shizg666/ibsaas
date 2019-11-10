package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.dto.NewFanDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.NewFanVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/3 9:55
 * @description:
 */
public interface INewFanWebService {
    /**
     * 全览
     * @return
     */
    List<NewFanVO> overview();

    /**
     * 根据某个id获取信息
     * @param id
     * @return
     */
    NewFanVO getInfoById(String id);

    /**
     * 修改四效新风的某个值
     * @param newFanDTO
     */
    void update(NewFanDTO newFanDTO);
}
