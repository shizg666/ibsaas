package com.landleaf.ibsaas.common.dao.parking;

import com.landleaf.ibsaas.common.domain.parking.HeartBeatRecord;
import com.landleaf.ibsaas.common.domain.parking.ParkingRealCountInit;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartBeatRecordDao extends BaseDao<HeartBeatRecord> {
}
