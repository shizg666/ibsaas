package com.landleaf.ibsaas.client.parking.lifang.service.trackflow.handler;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.parking.lifang.domain.Usercrdtm;
import com.landleaf.ibsaas.client.parking.lifang.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IUsercrdtmService;
import com.landleaf.ibsaas.client.parking.lifang.service.trackflow.AbstractTrafficFlowHandler;
import com.landleaf.ibsaas.client.parking.lifang.service.trackflow.TrafficFlowAnnoation;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import com.landleaf.ibsaas.common.enums.parking.UsercrdtmInHistoryQueryTypeEnum;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link UsercrdtmInHistoryQueryTypeEnum}
 */
@TrafficFlowAnnoation(code = "hour")
@Component
public class TrafficFlowForHour extends AbstractTrafficFlowHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(TrafficFlowForHour.class);


    @Autowired
    private IUsercrdtmService usercrdtmService;

    @Override
    public List<UsercrdtmInHistoryQueryDTO> handle(String code, UsercrdtmInHistoryQueryDTO queryDTO) {
        List<UsercrdtmInHistoryQueryDTO> result = Lists.newArrayList();
        String startTime = queryDTO.getStartTime();
        String endTime = queryDTO.getEndTime();
        Integer type = queryDTO.getType();
        if (StringUtil.isBlank(startTime)) {
            startTime ="2018-01-01 23:59:59";
        }
        if (StringUtil.isBlank(endTime)) {
            endTime ="2019-01-01 23:59:59";
        }
        Date start = DateUtil.parseDate(startTime);
        Date end = DateUtil.parseDate(endTime);
        Example example = new Example(Usercrdtm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("crdtm", start);
        criteria.andLessThanOrEqualTo("crdtm", end);
        if(type!=null){
            criteria.andCondition("inOrOut=", type);
        }
        List<Usercrdtm> usercrdtms = usercrdtmService.selectByExample(example);

        /**
         * 1、比重置时间小的为重置时间时数量；
         * 2、比重置时间大的为重置后计算出来的数量。
         */
        //获取当日24小时时间
        List<Date> hourList = DateUtils.getHourList(startTime,endTime);
        //查询重置时间到当前时间的数据
        if (!CollectionUtils.isEmpty(usercrdtms)) {
            for (Date date : hourList) {
                UsercrdtmInHistoryQueryDTO temp = new UsercrdtmInHistoryQueryDTO();
                BeanUtils.copyProperties(queryDTO, temp);
                temp.setCurrent(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
                result.add(temp);
                List<Usercrdtm> inList = usercrdtms.stream().filter(record -> {
                    if (record.getCrdtm().before(date) && record.getInOrOut().intValue() == ChannelTypeEnum.IN.type) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                int inCount = inList.size();
                temp.setCount(inCount);
            }
        }else{
            for (Date date : hourList) {
                UsercrdtmInHistoryQueryDTO temp = new UsercrdtmInHistoryQueryDTO();
                BeanUtils.copyProperties(queryDTO, temp);
                temp.setCurrent(DateUtil.format(date, "HH:mm:ss"));
                temp.setCount(0);
                result.add(temp);
            }
        }
        return result;
    }





}
