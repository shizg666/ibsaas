package com.landleaf.ibsaas.web.web.service.energy;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.energy.EnergyDataShow;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyDataShowVO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.EnergyDataShowDTO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.EnergyDataShowQryDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;


/**
 * @author Lokiy
 * @date 2019/6/14 17:37
 * @description:
 */
public interface IEnergyDataShowService extends IBaseService<EnergyDataShow> {


    void addData(EnergyDataShowDTO request);

    void updateById(EnergyDataShowDTO request);

    PageInfo<EnergyDataShowVO> getList(EnergyDataShowQryDTO request);

    void refreshEnergyDataShow();

    void removeById(Long id);
}
