package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.ConfigSettingDTO;
import com.landleaf.ibsaas.common.domain.energy.report.IntervalData;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 14:38
 * @description:
 */
public interface ConfigSettingDao extends BaseDao<ConfigSetting> {
    /**
     * 根据id,type,code 检测唯一性
     * @param configSettingDTO
     * @return
     */
    ConfigSetting selectUnique(ConfigSettingDTO configSettingDTO);

    /**
     * 根据类型获取
     * @param type
     * @return
     */
    List<ConfigSettingVO> getByType(String type);

    /**
     * 根据类型和code获取
     * @param type
     * @param code
     * @return
     */
    ConfigSettingVO getByTypeAndCode(@Param("type") String type, @Param("code") String code);

    /**
     * 查询前几  跑批用
     * @param maxId
     * @return
     */
    List<ConfigSetting> getCongfigSettingLmt(@Param("maxId") Integer maxId);

    /**
     * 根据年获取能耗标准
     * @param settingType
     * @param equipType
     * @param year
     * @return
     */
    String getStandardConsumption(@Param("settingType") String settingType,
                                  @Param("equipType") String equipType,
                                  @Param("year") Integer year);

    List<ConfigSetting> selectList();

    /**
     * 根据时间获取国标
     * @param chineseStandardEnergyConsumption
     * @param equipType
     * @return
     */
    List<IntervalData> getIntervalStandardConsumption(@Param("chineseStandardEnergyConsumption") String chineseStandardEnergyConsumption,
                                                      @Param("equipType") String equipType);
}
