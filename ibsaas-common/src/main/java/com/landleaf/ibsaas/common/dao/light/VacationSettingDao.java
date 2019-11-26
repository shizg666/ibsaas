package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.VacationSetting;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 * 假期记录表 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
public interface VacationSettingDao extends BaseDao<VacationSetting> {

    VacationSetting getSpecialFlag(@Param("date") String date);
}
