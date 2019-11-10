package com.landleaf.ibsaas.client.parking.lifang.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.parking.lifang.dao.UsercrdtmDao;
import com.landleaf.ibsaas.client.parking.lifang.domain.Channel;
import com.landleaf.ibsaas.client.parking.lifang.domain.Chargerule;
import com.landleaf.ibsaas.client.parking.lifang.domain.Usercrdtm;
import com.landleaf.ibsaas.client.parking.lifang.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IChannelService;
import com.landleaf.ibsaas.client.parking.lifang.service.IChargeruleService;
import com.landleaf.ibsaas.client.parking.lifang.service.IUsercrdtmService;
import com.landleaf.ibsaas.client.parking.lifang.service.trackflow.TrafficFlowHandler;
import com.landleaf.ibsaas.client.parking.lifang.service.trackflow.TrafficFlowHandlerSelector;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UsercrdtmResponseDTO;
import com.landleaf.ibsaas.common.enums.parking.UsercrdtmInHistoryQueryTypeEnum;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsercrdtmService extends AbstractBaseService<UsercrdtmDao, Usercrdtm> implements IUsercrdtmService<Usercrdtm> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsercrdtmService.class);
    @Autowired
    private UsercrdtmDao usercrdtmDao;
    @Autowired
    private IChargeruleService chargeruleService;
    @Autowired
    private IChannelService channelService;

    @Override
    public PageInfo pageQueryList(UsercrdtmListQueryDTO queryDTO) {
        List<UsercrdtmResponseDTO> result = Lists.newArrayList();
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getLimit(), true);
        Example example = new Example(Usercrdtm.class);
        Example.Criteria criteria = example.createCriteria();

        //起始时间
        if (!StringUtils.isEmpty(queryDTO.getStartTime())) {
            Date startDate = DateUtil.parseDate(queryDTO.getStartTime());
            criteria.andGreaterThanOrEqualTo("crdtm", startDate);
        }
        //结束时间
        if (!StringUtils.isEmpty(queryDTO.getEndTime())) {
            Date endDate = DateUtil.parseDate(queryDTO.getEndTime());
            criteria.andLessThanOrEqualTo("crdtm", endDate);
        }
        //收费类型
        if (!StringUtils.isEmpty(queryDTO.getChargeTypeCode())) {
            criteria.andCondition("chargeRuleID=", queryDTO.getChargeTypeCode());
        }
        //车牌号码
        if (!StringUtils.isEmpty(queryDTO.getCarCode())) {
            criteria.andLike("carCode", "%" + queryDTO.getCarCode() + "%");
        }
        //进出通道
        if (!StringUtils.isEmpty(queryDTO.getChannelCode())) {
            criteria.andCondition("channelId=", queryDTO.getChannelCode());
        }
        //通道状态
        if (!StringUtil.isEmpty(queryDTO.getChannelType())) {
            if (StringUtil.isEquals(ChannelTypeEnum.IN.getName(), queryDTO.getChannelType())) {
                //进
                criteria.andCondition("inOrOut=", ChannelTypeEnum.IN.type);
            } else if (StringUtil.isEquals(ChannelTypeEnum.OUT.getName(), queryDTO.getChannelType())) {
                criteria.andCondition("inOrOut=", ChannelTypeEnum.OUT.type);
            }
        }
        example.setOrderByClause("Crdtm desc");
        List<Usercrdtm> usercrdtms = selectByExample(example);
        if (CollectionUtils.isEmpty(usercrdtms)) {
            usercrdtms = Lists.newArrayList();
        }
        PageInfo pageInfo = new PageInfo(usercrdtms);
        if (!CollectionUtils.isEmpty(usercrdtms)) {

            List<Chargerule> chargerules = chargeruleService.selectAll();
            Map<Integer, List<Chargerule>> chargeruleMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(chargerules)) {
                chargeruleMap = chargerules.stream().collect(Collectors.groupingBy(Chargerule::getChargeRuleId));
            }
            List<Channel> channels = channelService.selectAll();
            Map<Integer, List<Channel>> channelMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(channels)) {
                channelMap = channels.stream().collect(Collectors.groupingBy(Channel::getChannelId));
            }

            Map<Integer, List<Chargerule>> finalChargeruleMap = chargeruleMap;
            Map<Integer, List<Channel>> finalChannelMap = channelMap;

            result = usercrdtms.stream().map(usercrdtm -> {
                UsercrdtmResponseDTO usercrdtmResponseDTO = new UsercrdtmResponseDTO();
                usercrdtmResponseDTO.setCarCode(usercrdtm.getCarCode());
                usercrdtmResponseDTO.setChannelCode(String.valueOf(usercrdtm.getChannelId()));
                usercrdtmResponseDTO.setChannelType(ChannelTypeEnum.getInstByType(usercrdtm.getInOrOut()).name);
                usercrdtmResponseDTO.setChargeTypeCode(String.valueOf(usercrdtm.getChargeRuleID()));
                usercrdtmResponseDTO.setUserName(usercrdtm.getUserName());
                usercrdtmResponseDTO.setUniqueId(String.valueOf(usercrdtm.getRecordId()));
                try {
                    usercrdtmResponseDTO.setOccurTime(DateUtil.format(usercrdtm.getCrdtm()));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                List<Chargerule> tempRule = finalChargeruleMap.get(usercrdtm.getChargeRuleID());
                if (!CollectionUtils.isEmpty(tempRule)) {
                    usercrdtmResponseDTO.setChargeTypeName(tempRule.get(0).getChargeRuleName());
                }
                List<Channel> tempChannel = finalChannelMap.get(usercrdtm.getChannelId());
                if (!CollectionUtils.isEmpty(tempChannel)) {
                    usercrdtmResponseDTO.setChannelName(tempChannel.get(0).getChannelName());
                }
                return usercrdtmResponseDTO;
            }).collect(Collectors.toList());
            pageInfo.setList(result);
        }

        return pageInfo;
    }

    @Override
    public UsercrdtmRealCountQueryDTO realCount(UsercrdtmRealCountQueryDTO queryDTO) {
        UsercrdtmRealCountQueryDTO result = new UsercrdtmRealCountQueryDTO();
        Integer occupyCount = queryDTO.getOccupyCount();
        Integer remainCount = queryDTO.getRemainCount();
        Integer total = queryDTO.getTotal();
        if (occupyCount == null) {
            queryDTO.setOccupyCount(0);
        }
        if (remainCount == null) {
            queryDTO.setRemainCount(0);
        }
        if (total == null) {
            queryDTO.setTotal(0);
        }
        String resetTime = queryDTO.getResetTime();

        Date resetDate = DateUtil.parseDate(resetTime);
        Example example = new Example(Usercrdtm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("crdtm", resetDate);
        criteria.andCondition("inOrOut=", ChannelTypeEnum.IN.type);
        int inCount = selectCountByExample(example);
        Example example2 = new Example(Usercrdtm.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andGreaterThanOrEqualTo("crdtm", resetDate);
        criteria2.andCondition("inOrOut=", ChannelTypeEnum.OUT.type);
        int outCount = selectCountByExample(example2);
        BeanUtils.copyProperties(queryDTO, result);
        if (outCount > inCount) {
            outCount = inCount;
        }
        int resultOccupyCount = queryDTO.getOccupyCount() + inCount - outCount;
        if (resultOccupyCount >= total) {
            resultOccupyCount = total;
        }
        result.setOccupyCount(resultOccupyCount);
        result.setRemainCount(queryDTO.getTotal() - result.getOccupyCount());
        return result;
    }

    /**
     * 当日各时段车位占用情况
     *
     * @param queryDTO
     * @return
     */
    @Override
    public List<UsercrdtmRealCountQueryByHourDTO> realCountFHour(UsercrdtmRealCountQueryByHourDTO queryDTO) {
        List<UsercrdtmRealCountQueryByHourDTO> result = Lists.newArrayList();
        Integer occupyCount = queryDTO.getOccupyCount();
        Integer remainCount = queryDTO.getRemainCount();
        Integer total = queryDTO.getTotal();
        if (occupyCount == null) {
            queryDTO.setOccupyCount(0);
        }
        if (remainCount == null) {
            queryDTO.setRemainCount(0);
        }
        if (total == null) {
            queryDTO.setTotal(0);
        }
        String resetTime = queryDTO.getResetTime();
        Date resetDate = null;
        if (StringUtil.isBlank(resetTime)) {
            resetDate = DateUtil.parseDate("2018-01-01 23:59:59");
        } else {
            resetDate = DateUtil.parseDate(resetTime);
        }
        Example example = new Example(Usercrdtm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("crdtm", resetDate);
        List<Usercrdtm> usercrdtms = selectByExample(example);

        /**
         * 1、比重置时间小的为重置时间时数量；
         * 2、比重置时间大的为重置后计算出来的数量。
         */
        //获取当日24小时时间
        List<Date> todayHourList = getTodayHourList();
        //查询重置时间到当前时间的数据
        if (!CollectionUtils.isEmpty(usercrdtms)) {
            for (Date date : todayHourList) {
                UsercrdtmRealCountQueryByHourDTO temp = new UsercrdtmRealCountQueryByHourDTO();
                BeanUtils.copyProperties(queryDTO, temp);
                temp.setCurrentHour(DateUtil.format(date, "HH:mm:ss"));
                if (date.before(resetDate)||date.after(new Date())) {
                    continue;
                }
                result.add(temp);
                List<Usercrdtm> inList = usercrdtms.stream().filter(record -> {
                    if (record.getCrdtm().before(date) && record.getInOrOut().intValue() == ChannelTypeEnum.IN.type) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                List<Usercrdtm> outList = usercrdtms.stream().filter(record -> {
                    if (record.getCrdtm().before(date) && record.getInOrOut().intValue() == ChannelTypeEnum.OUT.type) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                int inCount = inList.size();
                int outCount = outList.size();
                if (outCount > inCount) {
                    outCount = inCount;
                }
                int resultOccupyCount = queryDTO.getOccupyCount() + inCount - outCount;
                if (resultOccupyCount <= 0) {
                    resultOccupyCount = 0;
                }
                if (resultOccupyCount >= total) {
                    resultOccupyCount = total;
                }
                temp.setOccupyCount(resultOccupyCount);
                temp.setRemainCount(queryDTO.getTotal() - temp.getOccupyCount());
            }
        } else {
            for (Date date : todayHourList) {
                if (date.after(new Date())) {
                    continue;
                }
                UsercrdtmRealCountQueryByHourDTO temp = new UsercrdtmRealCountQueryByHourDTO();
                BeanUtils.copyProperties(queryDTO, temp);
                int resultOccupyCount = queryDTO.getOccupyCount();
                if (resultOccupyCount <= 0) {
                    resultOccupyCount = 0;
                }
                temp.setOccupyCount(resultOccupyCount);
                temp.setRemainCount(queryDTO.getTotal() - temp.getOccupyCount());
                temp.setCurrentHour(DateUtil.format(date, "HH:mm:ss"));
                result.add(temp);
            }
        }
        return result;
    }

    @Autowired
    private TrafficFlowHandlerSelector trafficFlowHandlerSelector;

    /**
     * 车流量查询
     *
     * @param queryDTO
     * @return
     */
    @Override
    public List<UsercrdtmInHistoryQueryDTO> trafficFlow(UsercrdtmInHistoryQueryDTO queryDTO) {
        String code = UsercrdtmInHistoryQueryTypeEnum.getInstByType(queryDTO.getType()).code;
        TrafficFlowHandler handler = trafficFlowHandlerSelector.selectHandler(code);
        return handler.handle(code, queryDTO);
    }

    @Override
    public int selectCountBetween(String startTime, String endTime, Integer type) {
        Example example = new Example(Usercrdtm.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtil.isBlank(startTime)) {
            startTime = "2018-01-01 23:59:59";
        }
        if (StringUtil.isBlank(endTime)) {
            endTime = "2019-01-01 23:59:59";
        }
        Date start = DateUtil.parseDate(startTime);
        Date end = DateUtil.parseDate(endTime);
        criteria.andGreaterThanOrEqualTo("crdtm", start);
        criteria.andLessThanOrEqualTo("crdtm", end);
        if (type != null) {
            criteria.andCondition("inOrOut=", type);
        }
        return selectCountByExample(example);
    }

    public List<Date> getTodayHourList() {
        List<Date> todayHourList = Lists.newArrayList();

        for (int i = 0; i < 24; i++) {
            Calendar todayStart = Calendar.getInstance();
            todayStart.set(Calendar.HOUR_OF_DAY, i);
            todayStart.set(Calendar.MINUTE, 0);
            todayStart.set(Calendar.SECOND, 0);
            todayStart.set(Calendar.MILLISECOND, 0);
            todayHourList.add(todayStart.getTime());
        }
        return todayHourList;
    }


}
