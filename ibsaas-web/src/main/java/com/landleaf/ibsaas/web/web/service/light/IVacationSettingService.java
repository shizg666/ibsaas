package com.landleaf.ibsaas.web.web.service.light;

import java.time.LocalDateTime;

/**
 * <p>
 * 假期记录表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
public interface IVacationSettingService<T> {

    Integer getSpecialFlag(LocalDateTime date);
}
