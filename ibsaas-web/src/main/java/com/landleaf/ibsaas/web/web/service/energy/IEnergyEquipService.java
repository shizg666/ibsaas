package com.landleaf.ibsaas.web.web.service.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.energy.vo.NodeChoiceVO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * @author Lokiy
 * @date 2019/6/12 14:51
 * @description:
 */
public interface IEnergyEquipService extends IBaseService<EnergyEquip> {
    /**
     * 新增某个能耗设备
     * @param energyEquipDTO
     * @return
     */
    EnergyEquipVO addEnergyEquip(EnergyEquipDTO energyEquipDTO);

    /**
     * 根据id获取设备信息
     * @param id
     * @return
     */
    EnergyEquipVO getEnergyEquipById(String id);

    /**
     * 根据id更新设备信息
     * @param energyEquipDTO
     * @return
     */
    EnergyEquipVO updateEnergyEquipById(EnergyEquipDTO energyEquipDTO);

    /**
     * 获取所有电表水表节点
     * @return
     */
    NodeChoiceVO nodes();
}
