package com.landleaf.ibsaas.client.knight.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.knight.service.IStationService;
import com.landleaf.ibsaas.common.dao.knight.StationDao;
import com.landleaf.ibsaas.common.domain.knight.control.Station;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class StationService extends AbstractBaseService<StationDao, Station> implements IStationService<Station> {

    public static final Logger LOGGER = LoggerFactory.getLogger(StationService.class);

    @Override
    public List<Station> getMjDeviceByIdsDb(List<Integer> deviceSysIds) {
        List<Station> result = Lists.newArrayList();
        Example example = new Example(Station.class);
        Example.Criteria criteria = example.createCriteria();

        if (!CollectionUtils.isEmpty(deviceSysIds)) {
            criteria.andIn("device_sys_id", deviceSysIds);
        }
        example.setOrderByClause("device_sys_id asc");
        List<Station> stationList = selectByExample(example);
        if (CollectionUtils.isEmpty(stationList)) {
            stationList = Lists.newArrayList();
        }
        if (!CollectionUtils.isEmpty(stationList)) {
            result.addAll(stationList);
        }
        return result;
    }

    @Override
    public PageInfo<Station> getMjDeviceByPageDb(int curPage, int pageSize) {
        PageHelper.startPage(curPage, pageSize, true);
        Example example = new Example(Station.class);
        Example.Criteria criteria = example.createCriteria();
        List<Station> stationList = selectByExample(example);
        if (CollectionUtils.isEmpty(stationList)) {
            stationList = Lists.newArrayList();
        }
        return new PageInfo(stationList);
    }
}
