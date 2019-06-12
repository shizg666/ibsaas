package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.ConfigSettingDTO;
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
}
