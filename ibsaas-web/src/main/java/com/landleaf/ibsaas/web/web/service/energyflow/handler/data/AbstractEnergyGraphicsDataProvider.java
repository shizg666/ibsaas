package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AbstractEnergyGraphicsDataProvider implements IEnergyGraphicsDataProvider {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractEnergyGraphicsDataProvider.class);


    public EnergyReportQueryVO reportQueryVO;

    public Map<String, Object> data = Maps.newHashMap();

    public List<EnergyGraphicsEnum> chartTypes = Lists.newArrayList();

    public void buildParam(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,List<EnergyGraphicsEnum> chartTypes) {
        reportQueryVO = new EnergyReportQueryVO(dateType, startTime, endTime, equipType, queryType, queryValue);
        this.chartTypes.addAll(chartTypes);
        this.convertimeByDateType(reportQueryVO);
    }

    @Override
    public Map<String, Object> getEnergyFlowData(Integer queryType, Integer queryValue, Integer dateType, Integer equipType, String startTime, String endTime,List<EnergyGraphicsEnum> chartTypes) {
        return null;
    }

    /**
     * 根据维度类型转换起止时间
     *
     * @param requestBody
     */
    public void convertimeByDateType(EnergyReportQueryVO requestBody) {
        String startTime = requestBody.getStartTime();
        String endTime = requestBody.getEndTime();
        Integer dateType = requestBody.getDateType();
        //根据维度生成
        switch (dateType) {
            case 1:
                //时
                startTime = DateUtils.convert(DateUtils.getStartDateForHour(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForHour(DateUtils.convert(endTime)));
                break;
            case 2:
                //日
                startTime = DateUtils.convert(DateUtils.getStartDateForDay(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForDay(DateUtils.convert(endTime)));
                break;
            case 3:
                //月
                startTime = DateUtils.convert(DateUtils.getStartDateForMonth(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForMonth(DateUtils.convert(endTime)));
                break;
            case 4:
                //年
                startTime = DateUtils.convert(DateUtils.getStartDateForYear(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForYear(DateUtils.convert(endTime)));
                break;
        }
        requestBody.setStartTime(startTime);
        requestBody.setEndTime(endTime);
    }


    /**
     * 根据维度类型转换起止时间
     *
     * @param requestBody
     */
    public Date getStartDate(EnergyReportDTO requestBody) {
        Date result = null;
        String startTime = requestBody.getStartTime();
        Integer dateType = requestBody.getDateType();
        //根据维度生成
        switch (dateType) {
            case 1:
                result = DateUtils.getStartDateForHour(DateUtils.convert(startTime));
                break;
            case 2:
                result = DateUtils.getStartDateForDay(DateUtils.convert(startTime));
                break;
            case 3:
                result = DateUtils.getStartDateForMonth(DateUtils.convert(startTime));
                break;
            case 4:
                result = DateUtils.getStartDateForYear(DateUtils.convert(startTime));
                break;
            default:
                result = new Date();
        }
        return result;
    }

    /**
     * 根据维度类型转换起止时间
     *
     * @param requestBody
     */
    public Date getEndDate(EnergyReportDTO requestBody) {
        Date result = null;
        String endTime = requestBody.getEndTime();
        Integer dateType = requestBody.getDateType();
        //根据维度生成
        switch (dateType) {
            case 1:
                //时
                result = DateUtils.getEndDateForHour(DateUtils.convert(endTime));
                break;
            case 2:
                //日
                result = DateUtils.getEndDateForDay(DateUtils.convert(endTime));
                break;
            case 3:
                //月
                result = DateUtils.getEndDateForMonth(DateUtils.convert(endTime));
                break;
            case 4:
                //年
                result = DateUtils.getEndDateForYear(DateUtils.convert(endTime));
                break;
            default:
                result = new Date();
        }
        return result;
    }


}
