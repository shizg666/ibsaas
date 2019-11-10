package com.landleaf.ibsaas.common.service.parking.impl;

import com.landleaf.ibsaas.common.dao.parking.ParkingRealCountInitDao;
import com.landleaf.ibsaas.common.domain.parking.ParkingRealCountInit;
import com.landleaf.ibsaas.common.service.parking.ICommonParkingRealCountInitService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonParkingRealCountInitService extends AbstractBaseService<ParkingRealCountInitDao, ParkingRealCountInit> implements ICommonParkingRealCountInitService<ParkingRealCountInit> {
    @Autowired
    private ParkingRealCountInitDao parkingRealCountInitDao;

}
