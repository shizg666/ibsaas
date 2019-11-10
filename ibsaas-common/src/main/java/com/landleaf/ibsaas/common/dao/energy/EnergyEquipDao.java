package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipSearchDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipSearchVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 14:40
 * @description:
 */
public interface EnergyEquipDao extends BaseDao<EnergyEquip> {

    /**
     * 获取设备数量
     * @return
     */
    int count();

    /**
     * 设备名和设备编号的唯一性
     * @param equipName
     * @param equipNo
     * @return
     */
    EnergyEquip selectUnique(@Param("equipName") String equipName, @Param("equipNo") String equipNo);

    /**
     * 根据id获取设备详情
     * @param id
     * @return
     */
    EnergyEquipVO getEnergyEquipVO(String id);

    /**
     * 分页查询
     * @param energyEquipSearchDTO
     * @return
     */
    List<EnergyEquipSearchVO> getEnergyEquipSearchVO(EnergyEquipSearchDTO energyEquipSearchDTO);



    /**
     * 抄表数据分页查询
     * @param energyEquipSearchDTO
     * @return
     */
    List<EnergyEquipSearchVO> getDataEnergyEquipSearchVO(EnergyEquipSearchDTO energyEquipSearchDTO);

    /**
     * 根据节点查询设备
     * @param nodeId
     * @param id
     * @return
     */
    List<EnergyEquip> getEnergyEquipByNodeId(@Param("nodeId") String nodeId, @Param("id") String id);
}
