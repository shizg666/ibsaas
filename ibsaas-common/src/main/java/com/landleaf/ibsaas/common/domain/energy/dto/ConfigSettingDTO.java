package com.landleaf.ibsaas.common.domain.energy.dto;

import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/12 15:14
 * @description:
 */
@Data
@ToString(callSuper = true)
public class ConfigSettingDTO extends ConfigSetting implements Serializable {
}
