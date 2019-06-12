package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.landleaf.ibsaas.common.dao.energy.ConfigSettingDao;
import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.dto.ConfigSettingDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import com.landleaf.ibsaas.web.web.util.WebDaoAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/6/12 14:49
 * @description:
 */
@Service
@Slf4j
public class ConfigSettingService extends AbstractBaseService<ConfigSettingDao, ConfigSetting> implements IConfigSettingService {

    @Autowired
    private ConfigSettingDao configSettingDao;

    @Autowired
    private WebDaoAdapter<ConfigSetting> webDaoAdapter;

    @Override
    public ConfigSetting add(ConfigSettingDTO configSettingDTO) {
        if(!checkUnique(configSettingDTO)){
            throw new BusinessException("已存在同样type和code的配置");
        }
        ConfigSetting configSetting = new ConfigSetting();
        BeanUtils.copyProperties(configSettingDTO, configSetting);
        webDaoAdapter.consummateAddOperation(configSetting);
        save(configSetting);
        return configSetting;
    }

    @Override
    public ConfigSetting updateById(ConfigSettingDTO configSettingDTO) {
        if(!checkUnique(configSettingDTO)){
            throw new BusinessException("已存在同样type和code的配置");
        }
        ConfigSetting configSetting = new ConfigSetting();
        BeanUtils.copyProperties(configSettingDTO, configSetting);
        webDaoAdapter.consummateUpdateOperation(configSetting);
        updateByPrimaryKeySelective(configSetting);
        return configSetting;
    }

    @Override
    public List<ConfigSettingVO> typeList(String type) {
        return configSettingDao.getByType(type);
    }

    @Override
    public ConfigSettingVO typeOne(String type, String code) {
        return configSettingDao.getByTypeAndCode(type, code);
    }

    @Override
    public List<ChoiceButton> getChoiceButtons(String type) {
        List<ConfigSettingVO> configSettingVOList = typeList(type);
        return configSettingVOList.stream()
                .map(cs -> new ChoiceButton(cs.getSettingCode(), cs.getSettingValue()))
                .collect(Collectors.toList());
    }



    /**
     * 根据id,type,code 检测唯一性
     * @param configSettingDTO
     * @return
     */
    private boolean checkUnique(ConfigSettingDTO configSettingDTO){
        ConfigSetting configSetting = configSettingDao.selectUnique(configSettingDTO);
        return configSetting == null;
    }
}
