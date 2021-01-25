package com.landleaf.ibsaas.common.dao.energy;

import com.landleaf.ibsaas.common.domain.energy.EnergyDataShow;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyDataShowVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/12 14:44
 * @description:
 */
public interface EnergyDataShowDao extends BaseDao<EnergyDataShow> {


    List<EnergyDataShowVO> getListPage(@Param("startDate") Long startDate, @Param("endDate") Long endDate);


    @Select(" SELECT d.id,d.VALUE,d.time FROM t_energy_data_show d ORDER BY d.time desc limit 12")
    List<EnergyDataShowVO> getListLatest12();

    BigDecimal getCount(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
