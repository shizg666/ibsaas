package com.landleaf.ibsaas.client.parking.lifang.mq.service.impl;

import com.landleaf.ibsaas.client.parking.lifang.mq.dao.OpengaterecordDao;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.Opengaterecord;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IChannelService;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IChargeruleService;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IOpengaterecordService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpengaterecordService extends AbstractBaseService<OpengaterecordDao, Opengaterecord> implements IOpengaterecordService<Opengaterecord> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpengaterecordDao opengaterecordDao;
    @Autowired
    private IChargeruleService chargeruleService;
    @Autowired
    private IChannelService channelService;

}
