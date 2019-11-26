package com.landleaf.ibsaas.web.web.service.light.impl;

import com.landleaf.ibsaas.common.dao.light.VacationSettingDao;
import com.landleaf.ibsaas.common.domain.light.VacationSetting;
import com.landleaf.ibsaas.common.utils.date.LocalAndDateUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.IVacationSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 假期记录表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
@Service
public class VacationSettingServiceImpl extends AbstractBaseService<VacationSettingDao, VacationSetting> implements IVacationSettingService {

    @Autowired
    private VacationSettingDao vacationSettingDao;

    @Override
    /**
     * 正常日期-0  节假日（包括周末（法定节假日）-1 补班日2
     */
    public Integer getSpecialFlag(LocalDateTime date) {
        String day = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        Date day = LocalAndDateUtil.localDateTime2Date(date);
        VacationSetting vacationSetting = vacationSettingDao.getSpecialFlag(day);
        if (vacationSetting == null){
            int dayOfWeek = date.getDayOfWeek().getValue();
            if (dayOfWeek == 7 || dayOfWeek == 6){
                return 1;
            }
            return 0;
        }
        return vacationSetting.getType();
    }
}
