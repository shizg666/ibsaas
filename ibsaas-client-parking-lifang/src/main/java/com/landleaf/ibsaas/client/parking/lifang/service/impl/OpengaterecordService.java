package com.landleaf.ibsaas.client.parking.lifang.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.parking.lifang.dao.OpengaterecordDao;
import com.landleaf.ibsaas.client.parking.lifang.dao.UserinfoDao;
import com.landleaf.ibsaas.client.parking.lifang.domain.Opengaterecord;
import com.landleaf.ibsaas.client.parking.lifang.service.IChannelService;
import com.landleaf.ibsaas.client.parking.lifang.service.IChargeruleService;
import com.landleaf.ibsaas.client.parking.lifang.service.IOpengaterecordService;
import com.landleaf.ibsaas.client.parking.lifang.service.IUserinfoService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
