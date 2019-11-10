package com.landleaf.ibsaas.web.web.service.energy;

import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.ConfigSettingDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/12 14:48
 * @description:
 */
public interface IConfigSettingService extends IBaseService<ConfigSetting> {
    /**
     * 新增配置
     * @param configSettingDTO
     * @return
     */
    ConfigSetting add(ConfigSettingDTO configSettingDTO);

    /**
     * 根据id更新配置
     * @param configSettingDTO
     * @return
     */
    ConfigSetting updateById(ConfigSettingDTO configSettingDTO);

    /**
     * 根据type获取配置列表
     * @param type
     * @return
     */
    List<ConfigSettingVO> typeList(String type);

    /**
     * 根据type和code获取具体的配置
     * @param type
     * @param code
     * @return
     */
    ConfigSettingVO typeOne(String type, String code);

    /**
     * 根据类型获取下拉菜单
     * @param type
     * @return
     */
    List<ChoiceButton> getChoiceButtons(String type);

    /**
     *  返回分类选择框
     * @param type
     * @return
     */
    Map<String, List<ChoiceButton>> getEquipClassificationChoiceButton(String type);

    List<ConfigSettingVO> selectList();
}
